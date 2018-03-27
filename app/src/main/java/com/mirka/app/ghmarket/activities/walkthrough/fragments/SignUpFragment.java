package com.mirka.app.ghmarket.activities.walkthrough.fragments;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.mirka.app.ghmarket.DB.User;
import com.mirka.app.ghmarket.R;
import com.mirka.app.ghmarket.databinding.FragmentSignUpBinding;
import com.mirka.app.ghmarket.misc.CustomAlert;
import com.mirka.app.ghmarket.misc.Util;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpFragment extends Fragment implements View.OnClickListener {

    public static final String TAG = SignUpFragment.class.getName();
    final List<String> permissions = Arrays.asList("public_profile", "email");
    FragmentSignUpBinding layout;
    CustomAlert alert;

    public SignUpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        layout = DataBindingUtil.bind(view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        setup();
    }

    private void setup() {
//        setup carousel
        layout.carousel.setViewListener(Util.getViewListener(getActivity()));
        layout.carousel.setPageCount(Util.MAIN_IMAGE.length);
        layout.btnSignIn.setOnClickListener(this);
        layout.btnSignUp.setOnClickListener(this);
        layout.btnFacebookSignin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn_sign_in:
//                back to sign in
                getFragmentManager().popBackStack();
                break;

            case R.id.btn_sign_up:
                emailSignup();
                break;
            case R.id.btn_facebook_signin:
                facebookSignup();
                break;
        }

    }

    private void facebookSignup() {
        ParseFacebookUtils.logInWithReadPermissionsInBackground(getActivity(), permissions, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {

                alert = new CustomAlert(getContext());
                alert.setTitle("Creating account");
                alert.setType(CustomAlert.PROGRESS);


                if (user == null) {

//                    Log.d("MyApp", "Uh oh. The user cancelled the Facebook login.");
//                    unable to signin
                } else if (user.isNew()) {
                    //new user
                    getUserDetailFromFB();
                } else {
                    //existing user
                    getUserDetailFromFB();
                }
            }
        });
    }

    private void emailSignup() {
        String email = layout.etEmail.getText().toString().trim();
        String name = layout.etUsername.getText().toString().trim();
        String password = layout.etPassword.getText().toString().trim();

        if (validatedImputs()) {

            User u = new User();
            u.setEmail(email);
            u.setPassword(password);
            u.setName(name);

            alert = new CustomAlert(getContext());
            alert.setTitle("Creating account");
            alert.setType(CustomAlert.PROGRESS);
            alert.show();

            u.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {    //success
                        alert.dismiss();

                        //switching style
                        getFragmentManager().beginTransaction().replace(R.id.container, new TypeFragment()).commit();

                    } else {          //error
                        alert.setContent(e.getMessage());
                        alert.setType(CustomAlert.MESSAGE);
                    }
                }
            });


        }
    }

    private boolean validatedImputs() {
        String email = layout.etEmail.getText().toString().trim();
        String name = layout.etUsername.getText().toString().trim();
//        String password = layout.etPassword.getText().toString().trim();

        if (!Util.isValidEmail(email)) {
            layout.etEmail.setError(getString(R.string.invalid_email));
            return false;
        }
        if (name.length() == 0) {
            layout.etUsername.setError(getString(R.string.name_empty));
            return false;
        }

        return true;
    }

    void getUserDetailFromFB() {
        alert.setType(CustomAlert.PROGRESS);
        alert.show();
        GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                String name = null, picture = null, email = null;
                try {
                    name = object.getString("name");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    email = object.getString("email");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    picture = String.format("https://graph.facebook.com/%s/picture?type=large", object.getString("id")) ;
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                saveNewUser(name, email, picture);
            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "name,email");
        request.setParameters(parameters);
        request.executeAsync();
    }

    void saveNewUser(String name, String email, String picture) {
        User user = User.getCurrentUser();
        user.setUsername(name);
        user.setEmail(email);
        user.setProfileImage(picture);
        user.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {

                alert.dismiss();
                //continue
                getFragmentManager().beginTransaction().replace(R.id.container, new TypeFragment()).commit();


            }
        });
    }
}
