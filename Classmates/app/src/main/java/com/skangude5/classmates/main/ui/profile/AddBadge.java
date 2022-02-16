package com.skangude5.classmates.main.ui.profile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.skangude5.classmates.R;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class AddBadge extends AppCompatActivity {
    AutoCompleteTextView add_badge_badge_type;
    AutoCompleteTextView add_badge_icon;
    AutoCompleteTextView add_badge_level;
    EditText add_badge_description_editText;
    Button add_badge_button;

    private FirebaseAuth mAuth;
    private FirebaseFirestore fStore;
    private String userId;
    private ProgressBar add_badge_page_progressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_add_badge);

        add_badge_badge_type = findViewById(R.id.add_badge_badge_type);
        add_badge_icon = findViewById(R.id.add_badge_icon);
        add_badge_level = findViewById(R.id.add_badge_level);
        add_badge_description_editText = findViewById(R.id.add_badge_description_editText);
        add_badge_button = findViewById(R.id.add_badge_button);
        add_badge_page_progressbar = findViewById(R.id.add_badge_page_progressbar);
        String[] badge_type = {"Skill","Job","Award"};
        String[] badge_icon = {"python","android","google","javascript"};
        String[] badge_level = {"Newbie","Intermediate","Expert"};
        ArrayAdapter<String> badgeTypeAdapter = new ArrayAdapter<String>(AddBadge.this,R.layout.dropdown_textview,badge_type);
        ArrayAdapter<String> badgeIconAdapter = new ArrayAdapter<String>(AddBadge.this,R.layout.dropdown_textview,badge_icon);
        ArrayAdapter<String> badgeLevelAdapter = new ArrayAdapter<String>(AddBadge.this,R.layout.dropdown_textview,badge_level);
        add_badge_badge_type.setAdapter(badgeTypeAdapter);
        add_badge_icon.setAdapter(badgeIconAdapter);
        add_badge_level.setAdapter(badgeLevelAdapter);


        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userId = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
        add_badge_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appendBadge();
            }
        });
    }

    private void appendBadge() {
        add_badge_button.setVisibility(View.INVISIBLE);
        add_badge_page_progressbar.setVisibility(View.VISIBLE);
        String badge_type = add_badge_badge_type.getText().toString();
        String badge_icon = add_badge_icon.getText().toString();
        String badge_level = add_badge_level.getText().toString();
        String badge_description = add_badge_description_editText.getText().toString();

        CollectionReference collectionReference = fStore.collection("users").document(userId).collection("profile");
        Map<String,Object> user = new HashMap<>();
        user.put("badge_type",badge_type);
        user.put("badge_icon",badge_icon);
        user.put("badge_level",badge_level);
        user.put("badge_description",badge_description);
        collectionReference.add(user).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                add_badge_button.setVisibility(View.VISIBLE);
                add_badge_page_progressbar.setVisibility(View.INVISIBLE);
                Toast.makeText(AddBadge.this, "Batch is added.", Toast.LENGTH_SHORT).show();
                AddBadge.this.finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                add_badge_button.setVisibility(View.VISIBLE);
                add_badge_page_progressbar.setVisibility(View.INVISIBLE);
                Toast.makeText(AddBadge.this, "Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}