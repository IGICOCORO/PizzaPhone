package bi.gbstallman.pizzaphone.Fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

import bi.gbstallman.pizzaphone.Adapter.AdapterPizzaList;
import bi.gbstallman.pizzaphone.Host;
import bi.gbstallman.pizzaphone.Model.Pizza;
import bi.gbstallman.pizzaphone.R;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class PizzaListFragment extends Fragment {
    RecyclerView pizzalist;
    String rappel;
    private ArrayList<Pizza> pizzas;
    AdapterPizzaList adapter;
    private TextView txt_prix_total, txt_qtt_total;
    public Button btn_order;
    public MutableLiveData<Double> total = new MutableLiveData<>();
    public MutableLiveData<Integer> quantity = new MutableLiveData<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pizza_list, container, false);
        pizzalist = view.findViewById(R.id.pizzalist);
        txt_prix_total = view.findViewById(R.id.txt_prix_total);
        txt_qtt_total = view.findViewById(R.id.txt_qtt_total);
        btn_order = view.findViewById(R.id.btn_order);

        rappel = getActivity().getIntent().getStringExtra("cookie");

        pizzalist.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false));
        pizzalist.addItemDecoration(new DividerItemDecoration(getContext(),1));

        pizzas = new ArrayList<>();
        adapter = new AdapterPizzaList(this,pizzas);
        pizzalist.setAdapter(adapter);

        extractpizzas();

        btn_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                order();
            }
        });

        total.observe(getActivity(), new Observer<Double>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onChanged(Double price) {
                txt_prix_total.setText(price.toString());
            }
        });
        quantity.observe(getActivity(), new Observer<Integer>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onChanged(Integer qtt) {
                txt_qtt_total.setText(qtt.toString());
            }
        });
        return view;
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
                String json = Objects.requireNonNull(response.body()).string();
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
                    getActivity().runOnUiThread((Runnable) () -> adapter.notifyDataSetChanged());
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    public void updateQuantityPizza() {
        int quantity = 0;
        double total = 0.0;
        for(Pizza pizza:pizzas){
            if(pizza.quantity == 0) continue;
            quantity += pizza.quantity;
            total += pizza.quantity*pizza.prix;
        }
        this.quantity.setValue(quantity);
        this.total.setValue(total);
    }

    public void order() {
        if(quantity.getValue() == null) return;
        String order = "";
        for(Pizza pizza:pizzas){
            if(pizza.quantity == 0) continue;
            String string_pizza = "";
            for (int i = 0; i<pizza.quantity; i++){
                order+=pizza.nom+",";
            }
            order += string_pizza;
        }
        order = order.substring(0, order.length()-1);
        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(Host.URL + "/order/"+order).newBuilder();
        String url = urlBuilder.build().toString();
        final RequestBody body = RequestBody.create("", null);
        Request request = new Request.Builder().url(url).addHeader("cookie", rappel).post(body).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, final IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String json = Objects.requireNonNull(response.body()).string();
                Log.i("===Pizza==", json);
                Host.toast(getActivity() ,"la commande a été envoyée avec succès",Toast.LENGTH_LONG);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        for(Pizza pizza:pizzas){
                            if(pizza.quantity == 0) continue;
                        }
                        total.setValue(0.);
                        quantity.setValue(0);
                    }
                });
            }
        });
    }
}