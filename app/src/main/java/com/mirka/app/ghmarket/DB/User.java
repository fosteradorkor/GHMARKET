package com.mirka.app.ghmarket.DB;

import com.parse.ParseUser;

/**
 * Created by Foster on 3/20/2018.
 */

public class User extends ParseUser {


    private static final String KEY_PROFILE_IMAGE = "profile_image";
    private static final String KEY_USER_NAME = "user_name";
    private static final String KEY_NOTIFICATION_OFFERS = "notification_offers";
    private static final String KEY_NOTIFICATION_ALERTS = "notification_alerts";
    private static final String KEY_NOTIFICATION_FAVORITES = "notification_favorites";

    //    name
    public String getName() {
        return getString(KEY_USER_NAME);
    }

    public void setName(String value) {
        put(KEY_USER_NAME, value);
    }

    //     profile image
    public void setProfileImage(String value) {
        put(KEY_PROFILE_IMAGE, value);
    }

    public String getProfileImage() {
     return   getString(KEY_PROFILE_IMAGE);
    }

    //    email
    public String getEmail() {
        return super.getEmail();
    }

    public void setEmail(String value) {
        super.setEmail(value);
        super.setUsername(value);
    }

    //  notification settings
    public boolean getNotificationOffers(){
        return getBoolean(KEY_NOTIFICATION_OFFERS);
    }

    public void setNotificationOffers(boolean value){put(KEY_NOTIFICATION_OFFERS, value);}

     public boolean getNotificationAlerts(){
        return getBoolean(KEY_NOTIFICATION_ALERTS);
    }

    public void setNotificationAlerts(boolean value){put(KEY_NOTIFICATION_ALERTS, value);}

     public boolean getNotificationFavorites(){
        return getBoolean(KEY_NOTIFICATION_FAVORITES);
    }

    public void setNotificationFavorites(boolean value){put(KEY_NOTIFICATION_FAVORITES, value);}



    public static User getCurrentUser(){
        return ((User) ParseUser.getCurrentUser());
    }


}
