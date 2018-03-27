package com.mirka.app.ghmarket.activities.store;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.mirka.app.ghmarket.R;
import com.mirka.app.ghmarket.activities.store.fragments.ExplorerFragment;
import com.mirka.app.ghmarket.activities.store.fragments.FavoritesFragment;
import com.mirka.app.ghmarket.activities.store.fragments.HomeFragment;
import com.mirka.app.ghmarket.activities.store.fragments.SearchFragment;
import com.mirka.app.ghmarket.databinding.ActivityStoreBinding;
import com.mirka.app.ghmarket.misc.FragmentAdapter;

import it.sephiroth.android.library.bottomnavigation.BottomNavigation;

public class StoreActivity extends AppCompatActivity {

    public static String STYLE = "style";
    public static String TYPE = "type";
    ActivityStoreBinding layout;
    FragmentAdapter fragmentAdapter = new FragmentAdapter(getSupportFragmentManager());
    private String selected_style, selected_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layout = DataBindingUtil.setContentView(this, R.layout.activity_store);

        fragmentAdapter.addFragment(new HomeFragment(), HomeFragment.TAG);
        fragmentAdapter.addFragment(new SearchFragment(), SearchFragment.TAG);
        fragmentAdapter.addFragment(new ExplorerFragment(), ExplorerFragment.TAG);
        fragmentAdapter.addFragment(new FavoritesFragment(), FavoritesFragment.TAG);

        switchFragment(fragmentAdapter.getItem(0), (String) fragmentAdapter.getPageTitle(0));


    }


    @Override
    protected void onStart() {
        super.onStart();
        layout.navigation.setOnMenuItemClickListener(new BottomNavigation.OnMenuItemSelectionListener() {
            @Override
            public void onMenuItemSelect(int id, int position, boolean b) {
                switchFragment(fragmentAdapter.getItem(position), (String) fragmentAdapter.getPageTitle(position));
            }

            @Override
            public void onMenuItemReselect(int i, int i1, boolean b) {

            }
        });
    }

    void switchFragment(Fragment fragment, String tag) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, fragment, tag)
                .commit();
    }

    @Override
    public void onBackPressed() {
        HomeFragment myFragment = (HomeFragment) getSupportFragmentManager().findFragmentByTag(HomeFragment.TAG);

        if (!((myFragment != null && myFragment.isVisible())))
            findViewById(layout.navigation.getMenuItemId(0)).performClick();
        else {
            super.onBackPressed();
        }

    }
}
