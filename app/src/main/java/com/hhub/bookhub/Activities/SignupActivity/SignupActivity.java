package com.hhub.bookhub.Activities.SignupActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.hhub.bookhub.Activities.LoginActivity.LoginActivity;
import com.hhub.bookhub.Activities.WelcomeActivity.WelcomeActivity;
import com.hhub.bookhub.R;

public class SignupActivity extends AppCompatActivity implements SignupView {

    private EditText edtUsername;
    private EditText edtPassword;
    private SignupPresenter presenter;
    private CardView btnSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        edtUsername = (EditText) findViewById(R.id.signup_edt_username);
        edtPassword = (EditText) findViewById(R.id.signup_edt_password);
        btnSignup = (CardView) findViewById(R.id.signup_btn_signup);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edtUsername != null && !edtUsername.getText().toString().equals("")
                        && edtPassword != null && !edtPassword.getText().toString().equals("")) {
                    presenter.signup(edtUsername.getText().toString(), edtPassword.getText().toString());
                }
            }
        });
    }

    @Override
    public void signup(String username, String password) {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(WelcomeActivity.SHARED_DATA,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(WelcomeActivity.USER_NAME,username);
        editor.putString(WelcomeActivity.PASSWORD,password);
        editor.commit();
        Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
        super.onBackPressed();
    }
}
