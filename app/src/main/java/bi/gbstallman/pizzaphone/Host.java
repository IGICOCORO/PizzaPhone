package bi.gbstallman.pizzaphone;

import android.app.Activity;
import android.widget.Toast;

public class Host {
    public static final String URL = "https://daviddurand.info/D228/pizza";

    public static void toast(final Activity activity, final String message, int longueur){
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
            }
        });
    }
}
