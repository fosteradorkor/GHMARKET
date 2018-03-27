package com.mirka.app.ghmarket.activities.store.fragments;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.mirka.app.ghmarket.DB.Product;
import com.mirka.app.ghmarket.R;
import com.mirka.app.ghmarket.activities.store.adapters.HomeGroupRecyclerAdapter;
import com.mirka.app.ghmarket.databinding.FragmentSearchBinding;
import com.parse.FindCallback;
import com.parse.ParseException;

import java.util.List;

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
        layout.recycler.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        layout.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                if (s.length() < 3) return false; // just a check

                performSearch(s);

                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }

    /**
     * perform search and if results is 0 or there is an error show message
     *
     * @param s
     */
    private void performSearch(String s) {
        Product.search(s, new FindCallback<Product>() {
            @Override
            public void done(List<Product> products, ParseException e) {
                if (e == null) {
                    if (products.size() > 0) {
                        layout.recycler.setAdapter(new HomeGroupRecyclerAdapter(products, getActivity(), true));
                    } else
                        Toast.makeText(getActivity(), "No results found", Toast.LENGTH_LONG).show();

                } else {
                    if (e.getCode() != 120)
                        Toast.makeText(getActivity(), "An error occoured", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
