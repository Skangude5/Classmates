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

import com.google.firebase.auth.FirebaseAuth;
import com.skangude5.classmates.R;
import com.skangude5.classmates.SplashScreen;
import com.skangude5.classmates.databinding.FragmentCommunityBinding;

public class CommunityFragment extends Fragment {

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

        final TextView textView = binding.textDashboard;
        communityViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

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
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}