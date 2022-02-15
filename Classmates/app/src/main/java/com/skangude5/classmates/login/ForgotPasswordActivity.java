package com.skangude5.classmates.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.skangude5.classmates.R;

public class ForgotPasswordActivity extends AppCompatActivity {
    private Button login_page_login_button;
    private ImageView forgot_password_page_back_button;
    private TextView forgot_password_page_login_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_forgot_password);

        login_page_login_button = findViewById(R.id.login_page_login_button);
        forgot_password_page_login_button = findViewById(R.id.forgot_password_page_login_button);
        forgot_password_page_back_button = findViewById(R.id.forgot_password_page_back_button);

        login_page_login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ForgotPasswordActivity.this, "OTP sent successfully", Toast.LENGTH_SHORT).show();
            }
        });

        forgot_password_page_login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
                ForgotPasswordActivity.this.startActivity(mainIntent);
                ForgotPasswordActivity.this.finish();
            }
        });

        forgot_password_page_back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ForgotPasswordActivity.this.finish();
            }
        });
    }
}