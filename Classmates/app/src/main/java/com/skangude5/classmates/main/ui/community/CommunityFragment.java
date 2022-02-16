package com.skangude5.classmates.main.ui.community;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.skangude5.classmates.R;
import com.skangude5.classmates.SplashScreen;
import com.skangude5.classmates.adapters.CustomRecyclerViewAdapterForUser;
import com.skangude5.classmates.databinding.FragmentCommunityBinding;
import com.skangude5.classmates.model.Badge;
import com.skangude5.classmates.model.User;

import java.util.ArrayList;

public class CommunityFragment extends Fragment {
    private RecyclerView recyclerView_user_list;
    private CommunityViewModel communityViewModel;
    private FragmentCommunityBinding binding;
    private Button button;
    FirebaseAuth mAuth;
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
                mAuth.signOut();
                Toast.makeText(getContext(), "Signed out successfully..", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), SplashScreen.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        ArrayList<User> list = new ArrayList<>();
        ArrayList<Badge> list_badge = new ArrayList<>();
        Badge badge1 = new Badge("Skill",R.drawable.android,"Expert","");
        Badge badge2 = new Badge("Skill",R.drawable.python,"Expert","");
        Badge badge3 = new Badge("Job",R.drawable.google,"SDE-II","");
        Badge badge4 = new Badge("Skill",R.drawable.js,"Newbie","");

        list_badge.add(badge1);
        list_badge.add(badge2);
        list_badge.add(badge3);
        list_badge.add(badge4);

        User user1 = new User("Sharad Kangude","","","",1,list_badge);
        User user2 = new User("Shubham Hake","","","",1,list_badge);
        User user3 = new User("Raman Mankar","","","",1,list_badge);
        User user4 = new User("Narayan Patil","","","",1,list_badge);
        User user5 = new User("Prayas Ingle","","","",1,list_badge);
        list.add(user1);
        list.add(user2);
        list.add(user3);
        list.add(user4);
        list.add(user5);

        CustomRecyclerViewAdapterForUser adapterForUser = new CustomRecyclerViewAdapterForUser(list,getContext());
        recyclerView_user_list.setAdapter(adapterForUser);
        recyclerView_user_list.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView_user_list.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}