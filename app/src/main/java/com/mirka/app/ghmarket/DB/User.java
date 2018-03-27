package com.mirka.app.ghmarket.DB;

import com.parse.ParseUser;

/**
 * Created by Foster on 3/20/2018.
 */

public class User extends ParseUser {


    private static final String KEY_PROFILE_IMAGE = "profile_image";
    private static final String KEY_USER_NAME = "user_name";

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


    public static User getCurrentUser(){
        return ((User) ParseUser.getCurrentUser());
    }


}
