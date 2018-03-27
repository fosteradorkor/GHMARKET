package com.mirka.app.ghmarket.activities.store.fragments;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.mirka.app.ghmarket.DB.Product;
import com.mirka.app.ghmarket.R;
import com.mirka.app.ghmarket.databinding.FragmentSearchBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {


    public static final String TAG = "tag_search";
    FragmentSearchBinding layout;

    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.fragment_search, container, false);
        layout = DataBindingUtil.bind(v);
        // Inflate the layout for this fragment
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        setup();
    }

    private void setup() {
        layout.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                if (s.length() < 3)return  false; // just a check

                performSearch(s);

                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }

    private void performSearch(String s) {
        Product.getQuery().where
    }
}
