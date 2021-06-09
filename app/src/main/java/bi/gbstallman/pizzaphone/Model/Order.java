package bi.gbstallman.pizzaphone.Model;


import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class Order {
    public String id_commande, total, time;
    public String[] references;

    public Order(String id_commande, String total, Long time, String references) {
        this.id_commande = id_commande;
        this.total = total;
        this.time = getTime(time);
        this.references = getReferences(references);
    }

// fonction pour avoir le temps , la commande a été passée
    private String getTime(Long integer) {
        SimpleDateFormat time = new SimpleDateFormat("dd MMM yyyy HH:mm");
        return time.format(integer*1000);
    }


    private String[] getReferences(String references) {
        String pure_str = references.replace("\"", "").replace("[", "").replace("]", "").replace("\\", "");
        return pure_str.split(",");
    }

    public int getQuantite(String recette){
        int quantity = 0;
        for(String s: references){
            if (s.equals(recette)) quantity++;
        }
        return quantity;
    }

    @Override
    public String toString() {
        ArrayList<String> affiché = new ArrayList<>();
        String text = "";
        for(String s: references){
            if(affiché.contains(s)) continue;
            text += ""+getQuantite(s)+" "+s+", ";
            affiché.add(s);
        }
        return text.substring(0, text.length()-2);
    }
}


