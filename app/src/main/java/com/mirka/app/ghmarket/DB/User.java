package com.mirka.app.ghmarket.DB;

import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Foster on 3/20/2018.
 */

public class User extends ParseUser {


    private static final String KEY_PROFILE_IMAGE = "profile_image";
    private static final String KEY_USER_NAME = "user_name";
    private static final String KEY_NOTIFICATION_OFFERS = "notification_offers";
    private static final String KEY_NOTIFICATION_ALERTS = "notification_alerts";
    private static final String KEY_NOTIFICATION_FAVORITES = "notification_favorites";
    private static final String KEY_FAVORITES = "favorites";
    private static final String KEY_AUTH_DATA = "authData";


    public static User getCurrentUser() {
        return ((User) ParseUser.getCurrentUser());
    }

    //    name
    public String getName() {
        return getString(KEY_USER_NAME);
    }

    public void setName(String value) {
        put(KEY_USER_NAME, value);
    }

    public String getProfileImage() {
        return getString(KEY_PROFILE_IMAGE);
    }

    //     profile image
    public void setProfileImage(String value) {
        put(KEY_PROFILE_IMAGE, value);
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
    public boolean getNotificationOffers() {
        return getBoolean(KEY_NOTIFICATION_OFFERS);
    }

    public void setNotificationOffers(boolean value) {
        put(KEY_NOTIFICATION_OFFERS, value);
    }

    public boolean getNotificationAlerts() {
        return getBoolean(KEY_NOTIFICATION_ALERTS);
    }

    public void setNotificationAlerts(boolean value) {
        put(KEY_NOTIFICATION_ALERTS, value);
    }

    public boolean getNotificationFavorites() {
        return getBoolean(KEY_NOTIFICATION_FAVORITES);
    }

    public void setNotificationFavorites(boolean value) {
        put(KEY_NOTIFICATION_FAVORITES, value);
    }

    public List<Product> getFavorites() {
        List<Product> favorites = getList(KEY_FAVORITES);
        if (favorites == null)
            return new ArrayList<>();
        return favorites;
    }

    public void addToFavorites(Product product) {
        List<Product> favorites = getFavorites();

        favorites.add(product);

        put(KEY_FAVORITES, favorites);
    }

    public String getAuthId() {
        Map<String, Map<String, String>> o = getMap(KEY_AUTH_DATA);
        if (o == null)
            return null;

       return o.get("facebook").get("id");

    }

    public void removeFromFavorites(Product product) {
        List<Product> favorites = getFavorites();
        for (int i = 0; i < favorites.size(); i++) {
            if (favorites.get(i).getObjectId().equals(product.getObjectId())) {
                favorites.remove(i);
                put(KEY_FAVORITES, favorites);
                saveInBackground();
                return;
            }
        }
    }
}
