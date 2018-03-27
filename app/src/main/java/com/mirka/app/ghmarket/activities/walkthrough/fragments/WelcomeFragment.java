package com.mirka.app.ghmarket.activities.walkthrough.fragments;


import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mirka.app.ghmarket.DB.User;
import com.mirka.app.ghmarket.R;
import com.mirka.app.ghmarket.databinding.FragmentWalkthroughWelcomeBinding;

/**
 * A simple {@link WelcomeFragment} subclass.
 */
public class WelcomeFragment extends Fragment {

    FragmentWalkthroughWelcomeBinding layout;

    public WelcomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_walkthrough_welcome, container, false);
        layout = DataBindingUtil.bind(v);
        return v;
    }


    @Override
    public void onStart() {
        super.onStart();
        setup();
    }


    private void setup() {
        layout.btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (User.getCurrentUser() == null)
                    getFragmentManager().beginTransaction().replace(R.id.container, new SignInFragment()).commit();
                else
                    getFragmentManager().beginTransaction().replace(R.id.container, new TypeFragment()).commit();

            }
        });

        layout.btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.container, new WelcomeFragment())
                        .commitNow();

                getFragmentManager().beginTransaction()
                        .replace(R.id.container, new SignUpFragment(), SignUpFragment.TAG)
                        .addToBackStack(SignInFragment.TAG)
                        .commit();

            }
        });
    }
}
