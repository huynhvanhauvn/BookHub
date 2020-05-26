package com.hhub.bookhub.Activities.WelcomeActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.hhub.bookhub.Activities.LoginActivity.LoginActivity;
import com.hhub.bookhub.Activities.MainActivity.MainActivity;
import com.hhub.bookhub.R;

public class WelcomeActivity extends AppCompatActivity {

    public static final String SHARED_DATA = "MyData";
    public static final String USER_NAME = "username";
    public static final String PASSWORD = "password";
    public static final String USER_ID = "userid";
    public static final String IS_LOGGED = "isLogged";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        getSupportActionBar().hide();

        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(SHARED_DATA, Context.MODE_PRIVATE);
        boolean isLogged = sharedPref.getBoolean(IS_LOGGED,false);
        if(isLogged) {
            Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else if (!isLogged) {
            Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
