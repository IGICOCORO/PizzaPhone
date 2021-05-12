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
    ArrayList<Pizza> pizzas;

    public Adapter(Context context, List<Pizza> pizzas){
        this.inflater = LayoutInflater.from(context);
        this.pizzas = (ArrayList<Pizza>) pizzas;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pizza, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name_pizza.setText(pizzas.get(position).getNom());
        holder.description_pizza.setText(pizzas.get(position).getIngredients());
        holder.prix_pizza.setText(pizzas.get(position).getPrix());
        Picasso.get().load(pizzas.get(position).getImage()).into(holder.image_pizza);

    }

    @Override
    public int getItemCount() {
        return pizzas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name_pizza,description_pizza;
        TextView prix_pizza;
        ImageView image_pizza;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name_pizza = itemView.findViewById(R.id.name_pizza);
            description_pizza  =  itemView.findViewById(R.id.description_pizza);
            prix_pizza = itemView.findViewById(R.id.prix_pizza);
            image_pizza = itemView.findViewById(R.id.image_pizza);
        }
    }
}
