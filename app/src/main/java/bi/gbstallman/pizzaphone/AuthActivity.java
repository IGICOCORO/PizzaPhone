package bi.gbstallman.pizzaphone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

import bi.gbstallman.pizzaphone.Dialogs.DialogAuth;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class AuthActivity extends AppCompatActivity {
    EditText username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
    }
// pizzaliste est une fonction qui sert d'extraire et de lister la carte des pizzas disponible
    public void pizzaliste(View view) {
        openDialog();
        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(Host.URL + "/login/"+username).newBuilder();
        String url = urlBuilder.build().toString();
        RequestBody body = RequestBody.create("", null);
        Request request = new Request.Builder().url(url).post(body).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                AuthActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(AuthActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, @NotNull Response response) throws IOException {
                String json = Objects.requireNonNull(response.body()).string();
                String rappel = "";
                for(String cookie: Objects.requireNonNull(response.headers().get("Set-Cookie")).split(";")) {
                    if (cookie.contains("PHPSESSID")) {
                        rappel = cookie;
                        try {
                            JSONObject j = new JSONObject(json);
                            if (j.getString("return").equals("connected")) {
                                Intent intent = new Intent(AuthActivity.this, MainActivity.class);
                                intent.putExtra("cookie", rappel);
                                startActivity(intent);
                                AuthActivity.this.finish();
                            } else {
                                Host.toast(AuthActivity.this, "verifier votre identifiant", Toast.LENGTH_LONG);
                            }
                        } catch (final Exception e) {
                            Host.toast(AuthActivity.this, e.getMessage(), Toast.LENGTH_LONG);
                        } finally {
                            break;
                        }
                    }
                }
            }
        });
    }

    private void openDialog() {
        DialogAuth dialog = new DialogAuth();
        dialog.show(getSupportFragmentManager(),"dialog connexion");
    }
}