package com.mirka.app.ghmarket.activities.store;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.mirka.app.ghmarket.App;
import com.mirka.app.ghmarket.DB.Order;
import com.mirka.app.ghmarket.R;
import com.mirka.app.ghmarket.activities.store.fragments.CategoryFragment;
import com.mirka.app.ghmarket.activities.store.fragments.ExploreFragment;
import com.mirka.app.ghmarket.activities.store.fragments.FavoritesFragment;
import com.mirka.app.ghmarket.activities.store.fragments.HomeFragment;
import com.mirka.app.ghmarket.activities.store.fragments.SearchFragment;
import com.mirka.app.ghmarket.databinding.ActivityStoreBinding;
import com.mirka.app.ghmarket.misc.FragmentAdapter;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;

import java.util.List;

import it.sephiroth.android.library.bottomnavigation.BottomNavigation;

public class StoreActivity extends AppCompatActivity {

    public static String STYLE = "style";
    public static String TYPE = "type";


    private String selected_style, selected_type;


    ActivityStoreBinding layout;
    FragmentAdapter fragmentAdapter = new FragmentAdapter(getSupportFragmentManager());


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layout = DataBindingUtil.setContentView(this, R.layout.activity_store);

        fragmentAdapter.addFragment(new HomeFragment(), HomeFragment.TAG);
        fragmentAdapter.addFragment(new SearchFragment(), SearchFragment.TAG);
        fragmentAdapter.addFragment(new CategoryFragment(), CategoryFragment.TAG);
        fragmentAdapter.addFragment(new FavoritesFragment(), FavoritesFragment.TAG);

        switchFragment(fragmentAdapter.getItem(0));


    }


    @Override
    protected void onStart() {
        super.onStart();
        layout.navigation.setOnMenuItemClickListener(new BottomNavigation.OnMenuItemSelectionListener() {
            @Override
            public void onMenuItemSelect(int id, int position, boolean b) {
                    switchFragment(fragmentAdapter.getItem(position));
            }

            @Override
            public void onMenuItemReselect(int i, int i1, boolean b) {

            }
        });
    }

    void switchFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }


}
