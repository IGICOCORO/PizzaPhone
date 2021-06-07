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

import bi.gbstallman.pizzaphone.Host;
import bi.gbstallman.pizzaphone.Model.Pizza;
import bi.gbstallman.pizzaphone.R;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    PizzalistActivity activity;
    LayoutInflater inflater;
     private final ArrayList<Pizza> pizzas;

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

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Pizza pizza = pizzas.get(position);
        holder.ingredient_pizza.setText(pizza.ingredients);
        holder.name_pizza.setText(pizza.nom);
        holder.prix_pizza.setText(pizza.prix.toString());
        holder.txt_qtt.setText(""+pizza.quantite);
        holder.btn_prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pizza.quantite>0) {
                    pizza.quantite--;
                    activity.updateQuantity();
                    holder.txt_qtt.setText(""+pizza.quantite);
                }
            }
        });
        holder.btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pizza.quantite++;
                activity.updateQuantity();
                holder.txt_qtt.setText(""+pizza.quantite);
            }
        });
        Picasso.get().load(Host.URL +"/"+(pizza.image)+".jpg").into(holder.image_pizza);

    }

    @Override
    public int getItemCount() {
        return pizzas.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name_pizza,txt_qtt,ingredient_pizza;
        TextView prix_pizza;
        Button btn_next, btn_prev;
        ImageView image_pizza;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name_pizza = itemView.findViewById(R.id.name_pizza);
            ingredient_pizza  =  itemView.findViewById(R.id.ingredient_pizza);
            prix_pizza = itemView.findViewById(R.id.prix_pizza);
            image_pizza = itemView.findViewById(R.id.image_pizza);
            txt_qtt = itemView.findViewById(R.id.txt_qtt);
            btn_next = itemView.findViewById(R.id.btn_next);
            btn_prev = itemView.findViewById(R.id.btn_prev);
        }
    }
}
