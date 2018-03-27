package com.mirka.app.ghmarket.activities.store.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mirka.app.ghmarket.DB.User;
import com.mirka.app.ghmarket.R;


public class FavoritesFragment extends Fragment {


    public static final String TAG = "tag_favorites";

    public FavoritesFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorites, container, false);
    }


    @Override
    public void onResume() {
        super.onResume();
        ExplorerFragment fragment = new ExplorerFragment();
        fragment.setProducts(User.getCurrentUser().getFavorites(), "FAVORITES");

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }
}
