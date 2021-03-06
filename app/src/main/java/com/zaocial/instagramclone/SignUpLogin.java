package com.zaocial.instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

public class SignUpLogin extends AppCompatActivity {

    private EditText edtUserNameSignUp, edtPasswordSignUp, edtUserNameLogin, edtPasswordLogin;
    private Button btnSignUp, btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_login);

        edtUserNameSignUp = findViewById(R.id.edtUserNameSignUp);
        edtPasswordSignUp = findViewById(R.id.edtPasswordSignUp);
        edtUserNameLogin = findViewById(R.id.edtUserNameLogin);
        edtPasswordLogin = findViewById(R.id.edtPasswrordLogin);
        btnSignUp = findViewById(R.id.btnSignUp);
        btnLogin = findViewById(R.id.btnLogin);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ParseUser appUser = new ParseUser();
                appUser.setUsername(edtUserNameSignUp.getText().toString());
                appUser.setPassword(edtPasswordSignUp.getText().toString());

                appUser.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {

                        if (e == null){
                            FancyToast.makeText(SignUpLogin.this,
                                    appUser.get("username") + " is Successfully Sign Up",
                                    FancyToast.LENGTH_LONG,
                                    FancyToast.SUCCESS,true).show();

                            Intent intent = new Intent(SignUpLogin.this, WellCome.class);
                            startActivity(intent);
                        }else {
                            FancyToast.makeText(SignUpLogin.this, e.getMessage(),
                                    FancyToast.LENGTH_LONG,
                                    FancyToast.ERROR, true).show();
                        }

                    }
                });

            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ParseUser.logInInBackground(edtUserNameLogin.getText().toString(), edtPasswordLogin.getText().toString(), new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        if (user != null && e == null){
                            FancyToast.makeText(SignUpLogin.this,
                                    user.get("username") + " is Successfully Login",
                                    FancyToast.LENGTH_LONG,
                                    FancyToast.SUCCESS,true).show();

                            Intent intent = new Intent(SignUpLogin.this, WellCome.class);
                            startActivity(intent);
                        }else {
                            FancyToast.makeText(SignUpLogin.this, e.getMessage(),
                                    FancyToast.LENGTH_LONG,
                                    FancyToast.ERROR, true).show();
                        }
                    }
                });
            }
        });
    }
}