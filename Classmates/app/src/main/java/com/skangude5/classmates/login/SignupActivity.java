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
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.skangude5.classmates.R;
import com.skangude5.classmates.main.MainActivity;
import com.skangude5.classmates.staticData.EmailValidator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignupActivity extends AppCompatActivity {
    private ImageView signup_page_back_button;
    private Button signup_page_signup_button;
    private TextView sign_up_page_login_button;
    private EditText sign_up_email;
    private EditText sign_up_password;
    private TextInputLayout signup_inputLayoutEmail;
    private TextInputLayout signup_inputLayoutPassword;
    private TextInputLayout signup_inputLayoutConfirmPassword;
    private EditText sign_up_confirm_password;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_signup);

        signup_page_back_button = findViewById(R.id.signup_page_back_button);
        signup_page_signup_button = findViewById(R.id.signup_page_signup_button);
        sign_up_page_login_button = findViewById(R.id.sign_up_page_login_button);
        sign_up_email = findViewById(R.id.signup_email);
        sign_up_password = findViewById(R.id.signup_password);
        sign_up_confirm_password = findViewById(R.id.signup_confirm_password);
        signup_inputLayoutEmail = findViewById(R.id.signup_inputLayoutEmail);
        signup_inputLayoutPassword = findViewById(R.id.signup_inputLayoutPassword);
        signup_inputLayoutConfirmPassword = findViewById(R.id.signup_inputLayoutConfirmPassword);
        signup_page_back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignupActivity.this.finish();
            }
        });

        signup_page_signup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createUser();
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

        mAuth = FirebaseAuth.getInstance();
    }

    private void createUser(){
        String email = sign_up_email.getText().toString();
        String password = sign_up_password.getText().toString();
        String confirm_password = sign_up_confirm_password.getText().toString();
        signup_inputLayoutEmail.setError(null);
        signup_inputLayoutPassword.setError(null);
        signup_inputLayoutConfirmPassword.setError(null);
        if(TextUtils.isEmpty(email)){
            signup_inputLayoutEmail.setError("Email cannot be empty.");
            Toast.makeText(SignupActivity.this, "Email cannot be empty.", Toast.LENGTH_SHORT).show();
        } else if(!EmailValidator.validate(email)){
            signup_inputLayoutEmail.setError("Please enter valid email.");
            Toast.makeText(SignupActivity.this, "Please enter valid email.", Toast.LENGTH_SHORT).show();
        } else if(TextUtils.isEmpty(password)){
            signup_inputLayoutPassword.setError("Password is empty.");
            Toast.makeText(SignupActivity.this, "Password is empty.", Toast.LENGTH_SHORT).show();
        } else if(password.length()<8){
            signup_inputLayoutPassword.setError("Password should contain at least 8 character's");
            Toast.makeText(SignupActivity.this, "Password should contain at least 8 character's", Toast.LENGTH_SHORT).show();
        } else if(TextUtils.isEmpty(confirm_password)){
            signup_inputLayoutConfirmPassword.setError("Confirm Password is empty.");
            Toast.makeText(SignupActivity.this, "Confirm Password is empty.", Toast.LENGTH_SHORT).show();
        } else if(!password.equals(confirm_password)){
            signup_inputLayoutConfirmPassword.setError("Confirm Password & Password does not match.");
            Toast.makeText(SignupActivity.this, "Confirm Password & Password does not match.", Toast.LENGTH_SHORT).show();
        } else {
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(SignupActivity.this, "user registered successfully.", Toast.LENGTH_SHORT).show();
                        Intent mainIntent = new Intent(SignupActivity.this, LoginActivity.class);
                        SignupActivity.this.startActivity(mainIntent);
                        SignupActivity.this.finish();
                    } else {
                        Toast.makeText(SignupActivity.this, "registration error."+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}