package bi.gbstallman.pizzaphone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;


import org.jetbrains.annotations.NotNull;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

;
import bi.gbstallman.pizzaphone.Adapter.Adapter;
import bi.gbstallman.pizzaphone.Model.Pizza;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PizzalistActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    String rappel;
    ArrayList<Pizza> pizzas;
    bi.gbstallman.pizzaphone.Adapter.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pizzalist);

        recyclerView = findViewById(R.id.pizzalist);
        pizzas = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(),DividerItemDecoration.VERTICAL));

        adapter = new Adapter(getApplicationContext(),pizzas);
        recyclerView.setAdapter(adapter);
        extractpizzas();
    }
    private void extractpizzas() {
        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(Host.URL).newBuilder();
        String url = urlBuilder.build().toString();


        Request request = new Request.Builder()
                .url(url)
                .addHeader("cookie",rappel)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.i("=== Error Parsing ===", "onFailure: ");
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String json = response.body().string();
                try {
                    JSONObject json_items = new JSONObject(json);
                    JSONObject json_item = new JSONObject(json);
                    Iterator<String> keys = json_items.keys();
                    Pizza pizza;
                    while (keys.hasNext()){
                        String key = keys.next();
                        json_item = json_items.getJSONObject(key);
                        pizza = new Pizza(
                                json_item.getString("prix"),
                                json_item.getString("ingredients"),
                                json_item.getString("image"),
                                key

                        );
                        Log.i("===Pizza===", pizza.toString());
                        pizzas.add(pizza);
                    }
                    PizzalistActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter.notifyDataSetChanged();
                        }
                    });
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
}