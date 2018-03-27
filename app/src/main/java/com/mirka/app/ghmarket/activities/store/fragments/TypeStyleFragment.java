package com.mirka.app.ghmarket.activities.store.fragments;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mirka.app.ghmarket.DB.Product;
import com.mirka.app.ghmarket.R;
import com.mirka.app.ghmarket.activities.store.adapters.HomeGroupRecyclerAdapter;
import com.mirka.app.ghmarket.databinding.FragmentTypeStyleBinding;
import com.mirka.app.ghmarket.misc.Util;
import com.parse.FindCallback;
import com.parse.ParseException;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TypeStyleFragment extends Fragment {

    FragmentTypeStyleBinding layout;

    public TypeStyleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_type_style, container, false);
        layout = DataBindingUtil.bind(v);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        Util.setUpToolbar(layout.toolbar);
    }

    @Override
    public void onStart() {
        super.onStart();
        setup();
    }

    private void setup() {
        String type = getArguments().getString(Product.KEY_PRODUCT_TYPE, null);
        String style = getArguments().getString(Product.KEY_PRODUCT_STYLE, null);

        //title
        layout.toolbar.setTitle(type.toUpperCase());
        Util.setUpToolbar(layout.toolbar);
        layout.toolbar.setOnMenuItemClickListener(Util.toolBarNavItemActions(getActivity()));


        //nav
        layout.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        Product.getProducts(style, type, new FindCallback<Product>() {
            @Override
            public void done(List<Product> objects, ParseException e) {
                if (e == null) {
//                    successfull query
                    layout.recycler.setLayoutManager(new GridLayoutManager(getContext(), 3));
                    layout.recycler.setAdapter(new HomeGroupRecyclerAdapter(objects, getContext()));

                }
            }
        });
    }

}
