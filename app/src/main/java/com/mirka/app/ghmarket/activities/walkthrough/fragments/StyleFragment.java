package com.mirka.app.ghmarket.activities.walkthrough.fragments;


import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mirka.app.ghmarket.R;
import com.mirka.app.ghmarket.activities.store.StoreActivity;
import com.mirka.app.ghmarket.activities.walkthrough.WalkthroughActivity;
import com.mirka.app.ghmarket.databinding.FragmentStyleBinding;
import com.mirka.app.ghmarket.misc.Util;

/**
 * A simple {@link Fragment} subclass.
 */
public class StyleFragment extends Fragment implements View.OnClickListener {

    FragmentStyleBinding layout;

    public StyleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_style, container, false);
        layout = DataBindingUtil.bind(v);
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        setup();
    }

    private void setup() {
        layout.btnCasual.setOnClickListener(this);
        layout.btnClassical.setOnClickListener(this);
        layout.btnSport.setOnClickListener(this);
        layout.close.setOnClickListener(this);

        Util.centerToolbarTitle(layout.toolbar);
    }

    @Override
    public void onClick(View view) {



        //open home
        Intent i = new Intent(getActivity(),StoreActivity.class);
        Bundle b = new Bundle();

        i.putExtras(b);
        startActivity(i);

    }
}
