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
import com.mirka.app.ghmarket.databinding.FragmentSignInBinding;
import com.mirka.app.ghmarket.misc.CustomAlert;
import com.mirka.app.ghmarket.misc.Util;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignInFragment extends Fragment implements View.OnClickListener {

    public static final String TAG = SignUpFragment.class.getName();
    final List<String> permissions = Arrays.asList("public_profile", "email");


    FragmentSignInBinding layout;
    CustomAlert alert;

    public SignInFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);
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

        layout.btnSignUp.setOnClickListener(this);
        layout.btnSignIn.setOnClickListener(this);
        layout.btnFbSignIn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_sign_up:
//                add signup frag
                getFragmentManager().beginTransaction().addToBackStack(SignInFragment.TAG)
                        .replace(R.id.container, new SignUpFragment(), SignUpFragment.TAG).commit();
                break;

            case R.id.btn_sign_in:
                //email login
                login();
                break;

            case R.id.btn_fb_sign_in:
                facebookSignup();
                break;


        }
    }

    void login() {
        if (inputs_validated()) {
            alert = new CustomAlert(getContext());
            alert.setTitle(getString(R.string.title_please_wait));
            alert.setType(CustomAlert.PROGRESS);
            alert.show();

            ParseUser.logInInBackground(layout.etEmail.getText().toString(),
                    layout.etPassword.getText().toString(),
                    new LogInCallback() {
                        @Override
                        public void done(ParseUser user, ParseException e) {
                            if (e != null) {
                                //error logging in
                                alert.setTitle(getString(R.string.signin_error));
                                alert.setContent(e.getMessage());
                                alert.setButtonText(getString(R.string.try_again));
                                alert.setType(CustomAlert.MESSAGE);

                            } else {
                                //successful login
                                alert.dismiss();

                                //switching style
                                getFragmentManager().beginTransaction().replace(R.id.container, new TypeFragment()).commit();

                            }
                        }
                    });
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

    private boolean inputs_validated() {
        String email = layout.etEmail.getText().toString().trim();
        if (email.length() == 0 || !Util.isValidEmail(email)) {
            layout.etEmail.setError(getString(R.string.invalid_email));
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
                    picture = String.format("https://graph.facebook.com/%s/picture?type=large", object.getString("id"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                saveUser(name, email, picture);
            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "name,email");
        request.setParameters(parameters);
        request.executeAsync();
    }

    void saveUser(String name, String email, String picture) {
        User user = User.getCurrentUser();
        user.setName(name);
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
