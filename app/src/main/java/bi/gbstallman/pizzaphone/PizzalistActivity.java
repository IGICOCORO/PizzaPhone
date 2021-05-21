    package bi.gbstallman.pizzaphone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
;
import bi.gbstallman.pizzaphone.Adapter.Adapter;
import bi.gbstallman.pizzaphone.Model.Pizza;

public class PizzalistActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<Pizza> pizzas;
    private static  String JSON_Url = "http://daviddurand.info/D228/pizza";
    bi.gbstallman.pizzaphone.Adapter.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pizzalist);

        recyclerView = findViewById(R.id.pizzalist);
        pizzas = new ArrayList<>();
        extractpizzas();
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        adapter = new Adapter(getApplicationContext(),pizzas);
        recyclerView.setAdapter(adapter);
    }
    private void extractpizzas() {
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, JSON_Url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject pizzaObject = response.getJSONObject(i);

                        Pizza pizza = new Pizza();
                        pizza.setNom(pizzaObject.getString("nom"));
                        pizza.setPrix(pizzaObject.getInt("prix"));
                        pizza.setIngredients(pizzaObject.getString("ingredients"));
                        pizza.setImage(pizzaObject.getString("image"));
                        pizzas.add(pizza);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("===ERROR===", "onErrorResponse: "+error.getMessage());
            }
        });
        queue.add(jsonArrayRequest);
    }

}