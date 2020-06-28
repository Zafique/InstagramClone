package com.zaocial.instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.shashank.sony.fancytoastlib.FancyToast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edtLoginEmail, edtLoginPassword;
    private Button btnLoign, btnSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setTitle("Login");

        edtLoginEmail = findViewById(R.id.edtLoginEmail);
        edtLoginPassword = findViewById(R.id.edtLoginPassword);
        // by enter key action will be done
        edtLoginPassword.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    onClick(btnLoign);
                }
                return false;
            }
        });

        btnLoign = findViewById(R.id.btnLoginButton);
        btnSignup = findViewById(R.id.btnLoginSignUpButton);

        btnSignup.setOnClickListener(this);
        btnLoign.setOnClickListener(this);

        if (ParseUser.getCurrentUser() != null) {
           ParseUser.getCurrentUser().logOut();
            //transitionToSocialMediaActivity();
        }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btnLoginButton:

                if (edtLoginEmail.getText().toString().equals("")
                        || edtLoginPassword.getText().toString().equals("")) {

                    FancyToast.makeText(LoginActivity.this,
                            "Email and Password is Required!",
                            FancyToast.LENGTH_LONG,
                            FancyToast.INFO, true).show();
                } else {

                    ParseUser.logInInBackground(edtLoginEmail.getText().toString(),
                            edtLoginPassword.getText().toString(), new LogInCallback() {
                                @Override
                                public void done(ParseUser user, ParseException e) {
                                    if (user != null && e == null) {
                                        FancyToast.makeText(LoginActivity.this,
                                                user.getUsername() + " is Successfully Login",
                                                FancyToast.LENGTH_LONG,
                                                FancyToast.SUCCESS, true).show();
                                        transitionToSocialMediaActivity();
                                    } else {
                                        FancyToast.makeText(LoginActivity.this, e.getMessage(),
                                                FancyToast.LENGTH_LONG,
                                                FancyToast.ERROR, true).show();
                                    }
                                }
                            });
                }
                break;

            case R.id.btnSignUp:
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
                break;

        }

    }

    public void rootLayoutTabbed(View view) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void transitionToSocialMediaActivity() {
        Intent intent = new Intent(LoginActivity.this, SocialMediaActivity.class);
        startActivity(intent);
    }
}