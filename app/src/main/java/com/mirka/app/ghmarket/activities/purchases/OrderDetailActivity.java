package com.mirka.app.ghmarket.activities.purchases;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.mirka.app.ghmarket.R;
import com.mirka.app.ghmarket.databinding.ActivityOrderDetailBinding;

public class OrderDetailActivity extends AppCompatActivity {

    ActivityOrderDetailBinding layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layout  = DataBindingUtil.setContentView(this, R.layout.activity_order_detail);
    }
}
