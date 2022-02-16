package com.skangude5.classmates.main.ui.community;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.skangude5.classmates.R;
import com.skangude5.classmates.adapters.CustomRecyclerViewAdapterForUser;
import com.skangude5.classmates.databinding.FragmentCommunityBinding;
import com.skangude5.classmates.model.Badge;
import com.skangude5.classmates.model.User;

import java.util.ArrayList;
import java.util.Objects;

public class CommunityFragment extends Fragment {
    private RecyclerView recyclerView_user_list;
    private CommunityViewModel communityViewModel;
    private FragmentCommunityBinding binding;
    private Button button;
    FirebaseAuth mAuth;

    private FirebaseFirestore fStore;
    private String userId;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        communityViewModel =
                new ViewModelProvider(this).get(CommunityViewModel.class);

        binding = FragmentCommunityBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        recyclerView_user_list = root.findViewById(R.id.recyclerView_for_user);
        button = root.findViewById(R.id.button);
        mAuth = FirebaseAuth.getInstance();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        ArrayList<User> list = new ArrayList<>();
        ArrayList<Badge> badges = new ArrayList<>();
        Badge badge1 = new Badge("Skill",R.drawable.android,"Expert","");
        Badge badge2 = new Badge("Job",R.drawable.google,"SDE-II","");
        Badge badge3 = new Badge("Skill",R.drawable.python,"Newbie","");
        Badge badge4 = new Badge("Skill",R.drawable.js,"Newbie","");
        badges.add(badge1);
        badges.add(badge2);
        badges.add(badge3);
        badges.add(badge4);
        User user1 = new User("Shubham Hake","","","",1,badges);
        User user2 = new User("Gaurav Gavhane","","","",1,badges);
        User user3 = new User("Krushna Sase","","","",1,badges);
        User user4 = new User("Raman Mankar","","","",1,badges);
        User user5 = new User("Narayan Patil","","","",1,badges);
        User user6 = new User("Kalpesh Palve","","","",1,badges);
        list.add(user1);
        list.add(user2);
        list.add(user3);
        list.add(user4);
        list.add(user5);
        list.add(user6);
        CustomRecyclerViewAdapterForUser adapterForUser = new CustomRecyclerViewAdapterForUser(list,getContext());
        recyclerView_user_list.setAdapter(adapterForUser);
        recyclerView_user_list.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView_user_list.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));


        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userId = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}