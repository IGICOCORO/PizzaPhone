package bi.gbstallman.pizzaphone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;


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
    private TextView txt_prix_total, txt_qtt_total;
    public MutableLiveData<Double> total = new MutableLiveData<>();
    public MutableLiveData<Integer> quantity = new MutableLiveData<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pizzalist);

        txt_prix_total = findViewById(R.id.txt_prix_total);
        txt_qtt_total = findViewById(R.id.txt_qtt_total);

        rappel = getIntent().getStringExtra("rappel");

        recyclerView = findViewById(R.id.pizzalist);
        pizzas = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(),DividerItemDecoration.VERTICAL));

        adapter = new Adapter(getApplicationContext(),pizzas);
        recyclerView.setAdapter(adapter);
        extractpizzas();

        total.observe(this, new Observer<Double>() {
            @Override
            public void onChanged(Double value) {
                txt_prix_total.setText(value.toString());
            }
        });
        quantity.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                txt_qtt_total.setText(integer.toString());
            }
        });
    }
    private void extractpizzas() {
        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(Host.URL).newBuilder();
        String url = urlBuilder.build().toString();


        Request request = new Request.Builder()
                .url(url)
            //    .addHeader("cookie",rappel)
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
                                json_item.getDouble("prix"),
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
    public void updateQuantity(){
        int qtt = 0;
        double total = 0;
        for(Pizza pizza:pizzas){
            if(pizza.quantite == 0) continue;
            qtt += pizza.quantite;
            total += pizza.quantite*pizza.prix;
        }
        this.quantity.setValue(qtt);
        this.total.setValue(total);
    }
}