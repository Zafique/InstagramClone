package com.zaocial.instagramclone;

import android.app.Application;

import com.parse.Parse;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("R9uwX2XvYNznRflCM6TSeUiTce2N9PRq7TIewL6S")
                // if defined
                .clientKey("VsEEIgE2UWn1Mr0lUTcfIIjtyin2CBOJfZBwbVSA")
                .server("https://parseapi.back4app.com/")
                .build()
        );

    }
}
