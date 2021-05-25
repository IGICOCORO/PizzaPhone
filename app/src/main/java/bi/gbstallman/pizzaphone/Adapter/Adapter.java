package bi.gbstallman.pizzaphone.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import bi.gbstallman.pizzaphone.Model.Pizza;
import bi.gbstallman.pizzaphone.R;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    LayoutInflater inflater;
     private ArrayList<Pizza> pizzas;
    private static  String JSON_Url = "http://daviddurand.info/D228/pizza";

    public Adapter(Context context, ArrayList<Pizza> pizzas){
        this.inflater = LayoutInflater.from(context);
        this.pizzas =  pizzas;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item_pizza, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Pizza pizza = pizzas.get(position);
        holder.description_pizza.setText(pizza.ingredients);
        holder.name_pizza.setText(pizza.nom);
        holder.prix_pizza.setText(pizza.prix);
        Picasso.get().load(JSON_Url+"/"+(pizza.image)+".jpg").into(holder.image_pizza);

    }

    @Override
    public int getItemCount() {
        return pizzas.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name_pizza;
        TextView description_pizza;
        TextView prix_pizza;
        ImageView image_pizza;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name_pizza = itemView.findViewById(R.id.name_pizza);
            description_pizza  =  itemView.findViewById(R.id.ingredient_pizza);
            prix_pizza = itemView.findViewById(R.id.prix_pizza);
            image_pizza = itemView.findViewById(R.id.image_pizza);
        }
    }
}
