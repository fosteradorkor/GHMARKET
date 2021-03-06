package com.mirka.app.ghmarket.activities.account;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;

import com.mirka.app.ghmarket.DB.User;
import com.mirka.app.ghmarket.R;
import com.mirka.app.ghmarket.databinding.ActivitySettingsBinding;

public class NotificationsActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    ActivitySettingsBinding layout;
    User u = User.getCurrentUser();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

         layout = DataBindingUtil.setContentView(this, R.layout.activity_settings);
    }


    @Override
    protected void onStart() {
        super.onStart();

        layout.setUser(u);


        layout.switcherFavorites.setOnCheckedChangeListener(this);
        layout.switcherOffers.setOnCheckedChangeListener(this);
        layout.switcherSales.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

        switch (compoundButton.getId()){
            case R.id.switcher_favorites:
                u.setNotificationFavorites(b);
                break;
            case R.id.switcher_offers:
                u.setNotificationOffers(b);
                break;
            case R.id.switcher_sales:
                u.setNotificationAlerts(b);
                break;
        }

        u.saveInBackground();
    }
}
