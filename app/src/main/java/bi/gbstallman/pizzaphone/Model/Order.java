package bi.gbstallman.pizzaphone.Model;

import android.text.format.Time;

public class Order {
    private Order order;
    private Pizza pizza;
    private Time time;
    private Double Total;

    public Order(Order  order, Pizza pizza,Time time,Double Total) {
        this.order = order;
        this.pizza = pizza;
        this.time = time;
        this.Total = Total;
    }

    @Override
    public String toString() {
        return "Order{" +
                "order=" + order +
                ", pizza=" + pizza +
                ", time=" + time +
                ", Total=" + Total +
                '}';
    }
}


