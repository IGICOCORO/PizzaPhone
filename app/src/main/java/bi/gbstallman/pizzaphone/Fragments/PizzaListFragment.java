package bi.gbstallman.pizzaphone.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import bi.gbstallman.pizzaphone.Adapter.Adapter;
import bi.gbstallman.pizzaphone.Host;
import bi.gbstallman.pizzaphone.Model.Pizza;
import bi.gbstallman.pizzaphone.R;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class PizzaListFragment extends Fragment {
    RecyclerView pizzalist;
    String rappel;
    private ArrayList<Pizza> pizzas;
    bi.gbstallman.pizzaphone.Adapter.Adapter adapter;
    private TextView txt_prix_total, txt_qtt_total;
    public MutableLiveData<Double> total = new MutableLiveData<>();
    public MutableLiveData<Integer> quantity = new MutableLiveData<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pizza_list, container, false);
        pizzalist = view.findViewById(R.id.pizzalist);
        pizzalist.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false));
        pizzalist.addItemDecoration(new DividerItemDecoration(getContext(),1));

        pizzas = new ArrayList<>();
        adapter = new Adapter(getContext(),pizzas);
        pizzalist.setAdapter(adapter);

        extractpizzas();
        return view;

    }


    private void extractpizzas() {
        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(Host.URL).newBuilder();
        String url = urlBuilder.build().toString();


        Request request = new Request.Builder()
                .url(url)
                //.addHeader("cookie",rappel)
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
                    getActivity().runOnUiThread((Runnable) () -> adapter.notifyDataSetChanged());
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

}