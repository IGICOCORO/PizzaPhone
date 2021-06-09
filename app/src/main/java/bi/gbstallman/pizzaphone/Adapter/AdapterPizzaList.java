package bi.gbstallman.pizzaphone.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import bi.gbstallman.pizzaphone.Fragments.PizzaListFragment;
import bi.gbstallman.pizzaphone.Host;
import bi.gbstallman.pizzaphone.Model.Pizza;
import bi.gbstallman.pizzaphone.R;

public class AdapterPizzaList extends RecyclerView.Adapter<AdapterPizzaList.ViewHolder> {
    Button btn_order;
    PizzaListFragment activity;
     private final ArrayList<Pizza> pizzas;

    public AdapterPizzaList(PizzaListFragment activity, ArrayList<Pizza> pizzas){
        this.pizzas =  pizzas;
        this.activity = activity;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item_pizza, parent, false);
        return new ViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Pizza pizza = pizzas.get(position);
        holder.ingredient_pizza.setText(pizza.ingredients);
        holder.name_pizza.setText(pizza.nom);
        holder.prix_pizza.setText(pizza.prix.toString());
        holder.txt_quantity.setText(""+pizza.quantity);
        holder.btn_prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pizza.quantity>0) {
                    pizza.quantity--;
                    activity.updateQuantityPizza();
                    holder.txt_quantity.setText(""+pizza.quantity);
                }
            }
        });
        holder.btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pizza.quantity++;
                activity.updateQuantityPizza();
                holder.txt_quantity.setText(""+pizza.quantity);
            }
        });
        Picasso.get().load(Host.URL +"/"+(pizza.image)+".jpg").into(holder.image_pizza);

    }

    @Override
    public int getItemCount() {
        return pizzas.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name_pizza,txt_quantity,ingredient_pizza;
        TextView prix_pizza;
        Button btn_next, btn_prev;
        ImageView image_pizza;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name_pizza = itemView.findViewById(R.id.name_pizza);
            ingredient_pizza  =  itemView.findViewById(R.id.ingredient_pizza);
            prix_pizza = itemView.findViewById(R.id.prix_pizza);
            image_pizza = itemView.findViewById(R.id.image_pizza);
            txt_quantity = itemView.findViewById(R.id.txt_quantity);
            btn_next = itemView.findViewById(R.id.btn_next);
            btn_prev = itemView.findViewById(R.id.btn_prev);
        }
    }
}
