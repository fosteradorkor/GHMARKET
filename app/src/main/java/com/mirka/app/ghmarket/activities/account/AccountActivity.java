package com.mirka.app.ghmarket.activities.account;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.mirka.app.ghmarket.DB.User;
import com.mirka.app.ghmarket.R;
import com.mirka.app.ghmarket.databinding.ActivityAccountBinding;
import com.mirka.app.ghmarket.misc.CustomAlert;
import com.parse.ParseException;
import com.parse.SaveCallback;

public class AccountActivity extends AppCompatActivity {

    ActivityAccountBinding layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layout = DataBindingUtil.setContentView(this, R.layout.activity_account);
    }

    @Override
    protected void onStart() {
        super.onStart();

        setup();
    }

    private void setup() {
        final User currentUser = User.getCurrentUser();
        layout.setUser(currentUser);
        layout.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        layout.saveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validated()) {
                    //inputs validated
                    final CustomAlert alert = new CustomAlert(AccountActivity.this);

                    //setting new password
                    String pword = layout.etPassword.getText().toString();
                    if (!pword.isEmpty())
                        currentUser.setPassword(pword);
                    layout.etPassword.setText(null);

                    //setting new data
                    currentUser.setEmail(layout.etEmail.getText().toString());
                    currentUser.setName(layout.etUsername.getText().toString());

                    //alert.show
                    alert.setType(CustomAlert.PROGRESS);
                    alert.show();

                    currentUser.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e != null) {
//                                show error message

                                alert.setType(CustomAlert.MESSAGE);
                                alert.setContent(e.getMessage());

                                alert.setOnDismissed(new CustomAlert.OnDismiss() {
                                    @Override
                                    public void dismissed() {
                                        layout.setUser(User.getCurrentUser());
                                    }
                                });
                            }else{
                                Toast.makeText(AccountActivity.this, "Changes saved successfully", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }

    private boolean validated() {
        boolean empty_email = layout.etEmail.getText().toString().isEmpty();
        boolean empty_name = layout.etUsername.getText().toString().isEmpty();

        return !empty_email && !empty_name;
    }
}
