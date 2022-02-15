package com.skangude5.classmates.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.skangude5.classmates.R;
import com.skangude5.classmates.SplashScreen;
import com.skangude5.classmates.main.MainActivity;

public class LoginActivity extends AppCompatActivity {
    private ImageView back_button;
    private Button login_button;
    private Button forgot_password_button;
    private TextView sign_up_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_login_activity);

        //initialise back_button with view
        back_button = findViewById(R.id.login_page_back_button);
        login_button = findViewById(R.id.login_page_login_button);
        forgot_password_button = findViewById(R.id.login_page_forgot_password_button);
        sign_up_button = findViewById(R.id.login_page_sign_up_button);

        // set click listener callback
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginActivity.this.onBackPressed();
            }
        });

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

        forgot_password_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                LoginActivity.this.startActivity(mainIntent);
                LoginActivity.this.finish();
            }
        });

        sign_up_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent(LoginActivity.this, SignupActivity.class);
                LoginActivity.this.startActivity(mainIntent);
                LoginActivity.this.finish();
            }
        });
    }

    private void login() {
        Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
        LoginActivity.this.startActivity(mainIntent);
        LoginActivity.this.finish();
    }
}