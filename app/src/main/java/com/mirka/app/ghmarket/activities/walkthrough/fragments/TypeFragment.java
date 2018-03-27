package com.mirka.app.ghmarket.activities.walkthrough.fragments;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mirka.app.ghmarket.R;
import com.mirka.app.ghmarket.activities.walkthrough.WalkthroughActivity;
import com.mirka.app.ghmarket.databinding.FragmentTypeBinding;
import com.mirka.app.ghmarket.misc.Util;

/**
 * A simple {@link Fragment} subclass.
 */
public class TypeFragment extends Fragment implements View.OnClickListener {

    FragmentTypeBinding layout;

    public TypeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_type, container, false);
        layout = DataBindingUtil.bind(v);
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        setup();
    }

    private void setup() {
        layout.btnMen.setOnClickListener(this);
        layout.close.setOnClickListener(this);
        layout.btnWomen.setOnClickListener(this);


        Util.centerToolbarTitle(layout.toolbar);
    }

    @Override
    public void onClick(View view) {



        //switching to style fragment
        getFragmentManager().beginTransaction()
                .replace(R.id.container, new StyleFragment())
                .addToBackStack(null)
                .commit();
    }
}
