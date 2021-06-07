package bi.gbstallman.pizzaphone;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import bi.gbstallman.pizzaphone.Fragments.OrderListFragment;
import bi.gbstallman.pizzaphone.Fragments.PizzaListFragment;

public class PageAdapter extends FragmentStatePagerAdapter {

    private Context context;
    Fragment[] fragments = new Fragment[]{
            new OrderListFragment(), new PizzaListFragment()
    };

    public PageAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments[position];
    }

    @Override
    public int getCount() {
        return fragments.length;
    }
}
