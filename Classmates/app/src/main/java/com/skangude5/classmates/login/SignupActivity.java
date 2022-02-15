package com.skangude5.classmates.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.skangude5.classmates.R;
import com.skangude5.classmates.main.MainActivity;

public class SignupActivity extends AppCompatActivity {
    private ImageView signup_page_back_button;
    private Button signup_page_signup_button;
    private TextView sign_up_page_login_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_signup);

        signup_page_back_button = findViewById(R.id.signup_page_back_button);
        signup_page_signup_button = findViewById(R.id.signup_page_signup_button);
        sign_up_page_login_button = findViewById(R.id.sign_up_page_login_button);

        signup_page_back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignupActivity.this.finish();
            }
        });

        signup_page_signup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signup();
            }
        });

        sign_up_page_login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent(SignupActivity.this, LoginActivity.class);
                SignupActivity.this.startActivity(mainIntent);
                SignupActivity.this.finish();
            }
        });
    }

    private void signup() {
        Intent mainIntent = new Intent(SignupActivity.this, MainActivity.class);
        SignupActivity.this.startActivity(mainIntent);
        SignupActivity.this.finish();
    }
}