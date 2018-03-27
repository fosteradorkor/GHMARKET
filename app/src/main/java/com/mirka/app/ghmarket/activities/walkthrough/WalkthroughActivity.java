package com.mirka.app.ghmarket.activities.walkthrough;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.mirka.app.ghmarket.DB.User;
import com.mirka.app.ghmarket.R;
import com.mirka.app.ghmarket.activities.walkthrough.fragments.WelcomeFragment;
import com.parse.ParseFacebookUtils;
//import com.mirka.app.ghmarket.databinding.ActivityWalkthroughBinding;

public class WalkthroughActivity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walkthrough);
    }

    @Override
    protected void onStart() {
        super.onStart();
        setup();

    }

    private void setup() {
//        initial fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new WelcomeFragment()).commit();
    }



    private boolean isLoggedIn() {
        return User.getCurrentUser() != null;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ParseFacebookUtils.onActivityResult(requestCode, resultCode, data);

    }
}
