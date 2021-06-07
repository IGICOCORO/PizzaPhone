package bi.gbstallman.pizzaphone.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import bi.gbstallman.pizzaphone.Fragments.OrderListFragment;
import bi.gbstallman.pizzaphone.Model.Order;
import bi.gbstallman.pizzaphone.R;


public class AdapterOrder extends RecyclerView.Adapter<AdapterOrder.ViewHolder> {
    OrderListFragment activity;
    private ArrayList<Order> orders;
    private String references;

    public AdapterOrder(OrderListFragment activity, ArrayList<Order> orders) {
        this.orders = orders;
        this.activity = activity;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item_order, parent, false);
        return new AdapterOrder.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Order order = orders.get(position);
        holder.id_commandes.setText(order.id_commande);
        holder.total_commande.setText(order.total);
        holder.references.setText(order.toString());
        holder.date_commande.setText(order.time);

    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public void setData(ArrayList<Order> orders) {
        this.orders = orders;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView id_commandes;
        public TextView references;
        public  TextView total_commande;
        public TextView date_commande;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            id_commandes = itemView.findViewById(R.id.id_commande);
            references  =  itemView.findViewById(R.id.references);
            total_commande = itemView.findViewById(R.id.total_commande);
            date_commande = itemView.findViewById(R.id.date_commande);
        }
    }
}
