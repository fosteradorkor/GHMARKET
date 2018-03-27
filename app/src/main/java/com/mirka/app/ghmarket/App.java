package com.mirka.app.ghmarket;

import android.app.Application;

import com.mirka.app.ghmarket.DB.Category;
import com.mirka.app.ghmarket.DB.Order;
import com.mirka.app.ghmarket.DB.Product;
import com.mirka.app.ghmarket.DB.User;
import com.parse.Parse;
import com.parse.ParseFacebookUtils;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;


/**
 * Created by Foster on 3/20/2018.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        parseSetup();

    }


    /**
     *
     */
    private void parseSetup() {
        //setting up parse
//        ParseObject.registerSubclass(User.class);


        ParseUser.registerSubclass(User.class);
        ParseObject.registerSubclass(Product.class);
        ParseObject.registerSubclass(Category.class);
        ParseObject.registerSubclass(Order.class);




        if (BuildConfig.DEBUG) {
//            local tessting

            Parse.initialize(new Parse.Configuration.Builder(this)
                    .applicationId(getString(R.string.parse_app_id))
                    .server(BuildConfig.LOCAL_SERVER)
//                    .enableLocalDataStore()
                    .build());
        } else {
//            live server
            Parse.initialize(new Parse.Configuration.Builder(this)
                    .applicationId(getString(R.string.PRODUCTION_APP_ID))
                    .server(getString(R.string.PRODUCTION_APP_SERVER))
//                    .enableLocalDataStore()
                    .build());
        }

        ParseFacebookUtils.initialize(this);



    }
}
