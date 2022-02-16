package com.skangude5.classmates.main.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.skangude5.classmates.R;
import com.skangude5.classmates.SplashScreen;
import com.skangude5.classmates.adapters.CustomRecyclerViewAdapterForBadgeItem;
import com.skangude5.classmates.databinding.FragmentProfileBinding;
import com.skangude5.classmates.model.Badge;
import com.skangude5.classmates.staticData.Icons;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ProfileFragment extends Fragment {

    private ProfileViewModel profileViewModel;
    private FragmentProfileBinding binding;
    private RecyclerView recyclerView;

    private EditText profile_full_name_editText;
    private EditText profile_college_name_editText;
    private EditText profile_year_of_study_editText;
    private EditText profile_department_editText;
    private Button profile_save_button;

    private Button profile_sign_out_button;
    private Button profile_add_badge_button;

    private FirebaseAuth mAuth;
    private FirebaseFirestore fStore;

    private TextView profile_user_name;
    private TextView profile_college_name;
    private TextView profile_year_and_department;
    private String userId;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        profileViewModel =
                new ViewModelProvider(this).get(ProfileViewModel.class);

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        profile_full_name_editText = root.findViewById(R.id.profile_full_name_editText);
        profile_college_name_editText = root.findViewById(R.id.profile_college_name_editText);
        profile_year_of_study_editText = root.findViewById(R.id.profile_year_of_study_editText);
        profile_department_editText = root.findViewById(R.id.profile_department_editText);
        profile_save_button = root.findViewById(R.id.profile_save_button);
        profile_sign_out_button = root.findViewById(R.id.profile_sign_out_button);
        profile_add_badge_button = root.findViewById(R.id.profile_add_badge_button);

        profile_user_name = root.findViewById(R.id.profile_user_name);
        profile_college_name = root.findViewById(R.id.profile_user_college_name);
        profile_year_and_department = root.findViewById(R.id.profile_user_year_and_department);


        ArrayList<Badge> list = new ArrayList<>();
        Badge badge1 = new Badge("Skill",R.drawable.android,"Expert","");
        Badge badge2 = new Badge("Skill",R.drawable.python,"Expert","");
        Badge badge3 = new Badge("Job",R.drawable.google,"SDE-II","");
        Badge badge4 = new Badge("Skill",R.drawable.js,"Newbie","");

        list.add(badge1);
        list.add(badge2);
        list.add(badge3);
        list.add(badge4);


        CustomRecyclerViewAdapterForBadgeItem customAdapterForRecyclerView = new CustomRecyclerViewAdapterForBadgeItem(list,getContext());
        recyclerView = root.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        recyclerView.setAdapter(customAdapterForRecyclerView);

        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userId = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
        profile_save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateData();
            }
        });
        DocumentReference documentReference = fStore.collection("users").document(userId).collection("profile").document("info");
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                String fullName = value.getString("fullName");
                String collegeName = value.getString("collegeName");
                String yearOfStudy = value.getString("yearOfStudy");
                String department = value.getString("department");

                profile_user_name.setText(fullName);
                profile_college_name.setText(collegeName);
                profile_year_and_department.setText(yearOfStudy+" year, "+ department);
            }
        });

        CollectionReference collectionReference = fStore.collection("users").document(userId).collection("profile");
        collectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                assert value != null;
                List<DocumentSnapshot> documentSnapshots = value.getDocuments();
                ArrayList<Badge> badges = new ArrayList<>();
                for(DocumentSnapshot documentSnapshot:documentSnapshots){
                    if(documentSnapshot.contains("badge_type")){
                        String badge_type = documentSnapshot.getString("badge_type");
                        String badge_level = documentSnapshot.getString("badge_level");
                        String badge_icon = documentSnapshot.getString("badge_icon");
                        String badge_description = documentSnapshot.getString("badge_description");
                        Integer icon = Icons.getIcon(badge_icon);
                        Badge badge = new Badge(badge_type,icon,badge_level,badge_description);
                        badges.add(badge);
                    }
                }
                customAdapterForRecyclerView.updateData(badges);
            }
        });


        profile_add_badge_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AddBadge.class);
                startActivity(intent);
            }
        });


        profile_sign_out_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOut();
            }
        });
        return root;
    }

    private void signOut() {
        mAuth.signOut();
        Toast.makeText(getContext(), "Signed out successfully..", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getContext(), SplashScreen.class);
        startActivity(intent);
        Objects.requireNonNull(getActivity()).finish();
    }

    private void updateData() {
        String fullName = profile_full_name_editText.getText().toString();
        String collegeName = profile_college_name_editText.getText().toString();
        String yearOfStudy = profile_year_of_study_editText.getText().toString();
        String department = profile_department_editText.getText().toString();
        if(TextUtils.isEmpty(fullName)){
            Toast.makeText(getContext(), "Enter fullName", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(collegeName)){
            Toast.makeText(getContext(), "Enter collegeName", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(yearOfStudy)){
            Toast.makeText(getContext(), "Enter yearOfStudy", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(department)){
            Toast.makeText(getContext(), "Enter department", Toast.LENGTH_SHORT).show();
            return;
        }

        DocumentReference documentReference = fStore.collection("users").document(userId).collection("profile").document("info");
        Map<String,Object> user = new HashMap<>();
        user.put("fullName",fullName);
        user.put("collegeName",collegeName);
        user.put("yearOfStudy",yearOfStudy);
        user.put("department",department);
        documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(getContext(), "User profile is updated.", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}