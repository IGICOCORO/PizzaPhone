package bi.gbstallman.pizzaphone.Model;

import android.icu.text.SimpleDateFormat;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;


public class Order {
    public String id_commande, total, time;
    public String[] references;
    private int integer;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public Order(String id_commande, String total, String time, String references) {
        this.id_commande = id_commande;
        this.total = total;
        this.time = getTime(time);
        this.references = getReferences(references);
    }

    private String getTime(String time) {
        SimpleDateFormat sdf = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            sdf = new SimpleDateFormat("dd MMM yyyy HH:mm");
        }
        return sdf.format(integer*1000);
    }


    private String[] getReferences(String references) {
        String pure_str = references.replace("\"", "").replace("[", "").replace("]", "").replace("\\", "");
        return pure_str.split(",");
    }

    public int getQuantite(String recette){
        int qtt = 0;
        for(String s: references){
            if (s.equals(recette)) qtt++;
        }
        return qtt;
    }

    @Override
    public String toString() {
        ArrayList<String> displayed = new ArrayList<>();
        String txt = "";
        for(String s: references){
            if(displayed.contains(s)) continue;
            txt += ""+getQuantite(s)+" "+s+", ";
            displayed.add(s);
        }
        return txt.substring(0, txt.length()-2);
    }
}


