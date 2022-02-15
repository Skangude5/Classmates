package com.skangude5.classmates.login;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;

import com.skangude5.classmates.R;
import com.skangude5.classmates.SplashScreen;

public class StartUpPage extends AppCompatActivity {
    private Button login_button;
    private Button signup_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_start_up_page);

        //initialise login_button with view
        login_button = findViewById(R.id.start_up_page_login_button);
        signup_button = findViewById(R.id.start_up_page_signup_button);

        // set onclick listener call back
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginIntent = new Intent(StartUpPage.this, LoginActivity.class);
                openLoginScreen(view,loginIntent,"login_page_transition");
            }
        });

        signup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginIntent = new Intent(StartUpPage.this, SignupActivity.class);
                openLoginScreen(view,loginIntent,"signup_page_transition");
            }
        });
    }

    private void openLoginScreen(View view, Intent intent, String transition_name){
        Pair[] pairs = new Pair[1];
        pairs[0] = new Pair<View,String>(view,transition_name);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(StartUpPage.this,pairs);
            startActivity(intent,activityOptions.toBundle());
        } else {
            startActivity(intent);
        }
    }
}