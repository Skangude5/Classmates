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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.skangude5.classmates.R;
import com.skangude5.classmates.SplashScreen;
import com.skangude5.classmates.main.MainActivity;
import com.skangude5.classmates.staticData.EmailValidator;

public class LoginActivity extends AppCompatActivity {
    private ImageView back_button;
    private Button login_button;
    private Button forgot_password_button;
    private TextView sign_up_button;
    private EditText login_email;
    private EditText login_password;
    private  TextInputLayout login_inputLayoutEmail;
    private TextInputLayout login_inputLayoutPassword;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_login_activity);

        //initialise back_button with view
        back_button = findViewById(R.id.login_page_back_button);
        login_button = findViewById(R.id.login_page_login_button);
        forgot_password_button = findViewById(R.id.login_page_forgot_password_button);
        sign_up_button = findViewById(R.id.login_page_sign_up_button);
        login_email = findViewById(R.id.login_email);
        login_password = findViewById(R.id.login_password);
        login_inputLayoutEmail = findViewById(R.id.login_inputLayoutEmail);
        login_inputLayoutPassword = findViewById(R.id.login_inputLayoutPassword);
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
                userLogin();
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


        mAuth = FirebaseAuth.getInstance();
    }

    private void userLogin(){
        String email = login_email.getText().toString();
        String password = login_password.getText().toString();

        login_inputLayoutEmail.setError(null);
        login_inputLayoutPassword.setError(null);
        if(TextUtils.isEmpty(email)){
            login_inputLayoutEmail.setError("Email cannot be empty.");
            Toast.makeText(LoginActivity.this, "Email cannot be empty.", Toast.LENGTH_SHORT).show();
        } else if(!EmailValidator.validate(email)){
            login_inputLayoutEmail.setError("Please enter valid email.");
            Toast.makeText(LoginActivity.this, "Please enter valid email.", Toast.LENGTH_SHORT).show();
        } else if(TextUtils.isEmpty(password)){
            login_inputLayoutPassword.setError("Password is empty.");
            Toast.makeText(LoginActivity.this, "Password is empty.", Toast.LENGTH_SHORT).show();
        } else if(password.length()<8){
            login_inputLayoutPassword.setError("Password should contain at least 8 character's");
            Toast.makeText(LoginActivity.this, "Password should contain at least 8 character's", Toast.LENGTH_SHORT).show();
        } else {
            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(LoginActivity.this, "user registered successfully.", Toast.LENGTH_SHORT).show();
                        Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
                        LoginActivity.this.startActivity(mainIntent);
                        LoginActivity.this.finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "login error."+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}