package com.mirka.app.ghmarket.activities.checkout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mirka.app.ghmarket.R;
import com.mirka.app.ghmarket.activities.checkout.fragments.BagFragment;

public class CheckoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new BagFragment()).commit();
    }



}
