package com.skangude5.classmates.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.skangude5.classmates.R;
import com.skangude5.classmates.staticData.EmailValidator;

public class ForgotPasswordActivity extends AppCompatActivity {
    private Button forgot_password_reset_button;
    private ImageView forgot_password_page_back_button;
    private TextView forgot_password_page_login_button;
    private TextInputLayout forgot_password_textInputLayoutEmail;
    private EditText forgot_password_email;
    private ProgressBar forgot_page_progressbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_forgot_password);

        forgot_password_reset_button = findViewById(R.id.forgot_password_reset_button);
        forgot_password_page_login_button = findViewById(R.id.forgot_password_page_login_button);
        forgot_password_page_back_button = findViewById(R.id.forgot_password_page_back_button);
        forgot_password_textInputLayoutEmail = findViewById(R.id.forgot_password_textInputLayoutEmail);
        forgot_password_email = findViewById(R.id.forgot_password_email);
        forgot_page_progressbar = findViewById(R.id.forgot_page_progressbar);

        forgot_password_reset_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendPasswordResetLink();
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

    private void sendPasswordResetLink(){
        String email = forgot_password_email.getText().toString();
        forgot_password_textInputLayoutEmail.setError(null);
        if(TextUtils.isEmpty(email)){
            forgot_password_textInputLayoutEmail.setError("Email cannot be empty.");
            Toast.makeText(ForgotPasswordActivity.this, "Email cannot be empty.", Toast.LENGTH_SHORT).show();
        } else if(!EmailValidator.validate(email)){
            forgot_password_textInputLayoutEmail.setError("Please enter valid email.");
            Toast.makeText(ForgotPasswordActivity.this, "Please enter valid email.", Toast.LENGTH_SHORT).show();
        } else {
            forgot_password_reset_button.setVisibility(View.INVISIBLE);
            forgot_page_progressbar.setVisibility(View.VISIBLE);
            FirebaseAuth.getInstance().sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        forgot_password_reset_button.setVisibility(View.VISIBLE);
                        forgot_page_progressbar.setVisibility(View.INVISIBLE);
                        Toast.makeText(ForgotPasswordActivity.this, "Password resent link has been sent successfully.", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(ForgotPasswordActivity.this,LoginActivity.class));
                        finish();
                    } else {
                        forgot_password_reset_button.setVisibility(View.VISIBLE);
                        forgot_page_progressbar.setVisibility(View.INVISIBLE);
                        Toast.makeText(ForgotPasswordActivity.this, "Error: "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }
}