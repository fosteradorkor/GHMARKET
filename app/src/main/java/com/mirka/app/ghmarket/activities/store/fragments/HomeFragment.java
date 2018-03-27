package com.mirka.app.ghmarket.activities.store.fragments;


import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mirka.app.ghmarket.DB.Category;
import com.mirka.app.ghmarket.DB.Product;
import com.mirka.app.ghmarket.R;
import com.mirka.app.ghmarket.activities.store.StoreViewModel;
import com.mirka.app.ghmarket.activities.store.adapters.HomeRecyclerAdapter;
import com.mirka.app.ghmarket.databinding.FragmentHomeBinding;
import com.mirka.app.ghmarket.misc.LinearSpacingItemDecoration;
import com.mirka.app.ghmarket.misc.Util;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {


    public static final String TAG = "tag_home";
    FragmentHomeBinding layout;
    HomeRecyclerAdapter adapter;
    StoreViewModel model;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        layout = DataBindingUtil.bind(v);
        model = ViewModelProviders.of(this).get(StoreViewModel.class);
//        adapter = new HomeRecyclerAdapter(getContext(), this);
        setup();

        return v;
    }


    @Override
    public void onResume() {
        super.onResume();
        Util.setUpToolbar(layout.toolbar);
    }

    private void setup() {


        Category.getAll(new Category.OnComplete() {
            @Override
            public void complete(final List<Category> categories, Exception e) {

                if (categories != null) {
                    adapter = new HomeRecyclerAdapter(getContext(), categories);
                    layout.recycler.setAdapter(adapter);
                }
            }

        });


        layout.recycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        layout.recycler.addItemDecoration(new LinearSpacingItemDecoration(HomeRecyclerAdapter.VERTICAL_SPACING, LinearSpacingItemDecoration.VERTICAL, false));

        //context menu
        Util.setUpToolbar(layout.toolbar);
        layout.toolbar.setOnMenuItemClickListener(Util.toolBarNavItemActions(getActivity()));

        //buttons
        layout.btnBaby.setOnClickListener(this);
        layout.btnKids.setOnClickListener(this);
        layout.btnMen.setOnClickListener(this);
        layout.btnWomen.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        String type = null;
        switch (view.getId()) {
            case R.id.btn_women:
                type = "women";
                break;
            case R.id.btn_men:
                type = "men";
                break;
            case R.id.btn_kids:
                type = "kids";
                break;
            case R.id.btn_baby:
                type = "baby";
                break;

        }
        TypeStyleFragment typeStyleFragment = new TypeStyleFragment();
        Bundle b = new Bundle();
        b.putString(Product.KEY_PRODUCT_TYPE, type);
        typeStyleFragment.setArguments(b);

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.container, typeStyleFragment)
                .addToBackStack(null)
                .commit();
    }
}
