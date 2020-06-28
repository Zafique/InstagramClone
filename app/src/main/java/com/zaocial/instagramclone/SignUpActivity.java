package com.zaocial.instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edtSignUpEmail, edtSignUpUserName, edtSignUpPassword;
    private Button btnSignUp, btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        setTitle("Sign Up");

        edtSignUpEmail = findViewById(R.id.edtSignUpEmail);
        edtSignUpUserName = findViewById(R.id.edtSignUpUserName);
        edtSignUpPassword = findViewById(R.id.edtSignUpPassword);

        // by enter key action will be done
        edtSignUpPassword.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    onClick(btnSignUp);
                }
                return false;
            }
        });
        btnSignUp = findViewById(R.id.btnSignUpButton);
        btnLogin = findViewById(R.id.btnSignUpLoginButton);

        btnSignUp.setOnClickListener(this);
        btnLogin.setOnClickListener(this);

        if (ParseUser.getCurrentUser() != null) {
            ParseUser.getCurrentUser().logOut();
            //transitionToSocialMediaActivity();
        }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btnSignUpButton:

                if (edtSignUpEmail.getText().toString().equals("")
                        || edtSignUpUserName.getText().toString().equals("")
                        || edtSignUpPassword.getText().toString().equals("")) {

                    FancyToast.makeText(SignUpActivity.this,
                            "Email, Username and Password is Required!",
                            FancyToast.LENGTH_LONG,
                            FancyToast.INFO, true).show();
                } else {

                    final ParseUser appUser = new ParseUser();
                    appUser.setEmail(edtSignUpEmail.getText().toString());
                    appUser.setUsername(edtSignUpUserName.getText().toString());
                    appUser.setPassword(edtSignUpPassword.getText().toString());

                    final ProgressDialog progressDialog = new ProgressDialog(this);
                    progressDialog.setMessage("Sign Up " + edtSignUpUserName.getText().toString());
                    progressDialog.show();


                    appUser.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                FancyToast.makeText(SignUpActivity.this,
                                        appUser.get("username") + " is Successfully Sign Up",
                                        FancyToast.LENGTH_LONG,
                                        FancyToast.SUCCESS, true).show();

                                transitionToSocialMediaActivity();

                            } else {
                                FancyToast.makeText(SignUpActivity.this, e.getMessage(),
                                        FancyToast.LENGTH_LONG,
                                        FancyToast.ERROR, true).show();
                            }

                            progressDialog.dismiss();
                        }
                    });
                }

                break;
            case R.id.btnSignUpLoginButton:
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
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
        Intent intent = new Intent(SignUpActivity.this, SocialMediaActivity.class);
        startActivity(intent);
    }
}