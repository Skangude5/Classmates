package com.skangude5.classmates.main.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.skangude5.classmates.R;
import com.skangude5.classmates.adapters.CustomRecyclerViewAdapterForBadgeItem;
import com.skangude5.classmates.databinding.FragmentProfileBinding;
import com.skangude5.classmates.model.Badge;

import java.util.ArrayList;

public class ProfileFragment extends Fragment {

    private ProfileViewModel profileViewModel;
    private FragmentProfileBinding binding;
    private RecyclerView recyclerView;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        profileViewModel =
                new ViewModelProvider(this).get(ProfileViewModel.class);

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

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
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}