package bi.gbstallman.pizzaphone.Fragments;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import bi.gbstallman.pizzaphone.Adapter.AdapterOrder;
import bi.gbstallman.pizzaphone.Host;
import bi.gbstallman.pizzaphone.Model.Order;
import bi.gbstallman.pizzaphone.R;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class OrderListFragment extends Fragment {
    RecyclerView orderslist;
    private ArrayList<Order> orders;
    private AdapterOrder adapter;
    String rappel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_list, container, false);
        orderslist = view.findViewById(R.id.orderslist);
        orderslist.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false));
        orderslist.addItemDecoration(new DividerItemDecoration(getContext(),1));

        rappel = getActivity().getIntent().getStringExtra("cookie");
        orders = new ArrayList<>();
        adapter = new AdapterOrder(this,orders);
        orderslist.setAdapter(adapter);

// appel d'une fonction
        extractorders();
        return view;
    }
// extractorders : fonction chargée d'envoyer la requete sur le serveur et extraire les données mes commandes
    private void extractorders() {
        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(Host.URL+"/mescommandes").newBuilder();
        String url = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(url)
                .addHeader("cookie",rappel)
                .build();
        client.newCall(request).enqueue(new Callback() {
            // si il y a erreur
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.i("=== Error Parsing ===", "onFailure: ");
            }
            @RequiresApi(api = Build.VERSION_CODES.N)
            // si il n'y a pas d'erreur
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String json = Objects.requireNonNull(response.body()).string();
                try {
                    Log.i("===Order===", json);
                    JSONArray json_items = new JSONArray(json);
                    JSONObject json_item;
                    Order order;
                    for (int i=0; i<json_items.length(); i++){
                        json_item = json_items.getJSONObject(i);
                        order = new Order(
                                json_item.getString("idCommande"),
                                json_item.getString("Total"),
                                json_item.getLong("Time"),
                                json_item.getString("References")
                        );
                        Log.i("===Order===", order.toString());
                        orders.add(order);
                    }
                    getActivity().runOnUiThread((Runnable) () -> adapter.notifyDataSetChanged());
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
}