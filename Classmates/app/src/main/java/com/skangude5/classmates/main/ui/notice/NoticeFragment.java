package com.skangude5.classmates.main.ui.notice;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.skangude5.classmates.R;
import com.skangude5.classmates.adapters.AdapterPosts;
import com.skangude5.classmates.model.ModelPost;

import java.util.ArrayList;
import java.util.List;

public class NoticeFragment extends Fragment {

    FirebaseAuth firebaseAuth;
    String myuid;
    RecyclerView recyclerView;
    List<ModelPost> posts;
    AdapterPosts adapterPosts;

    private TextView empty_textview;
    private ProgressBar progress_bar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_notice, container, false);

        progress_bar = root.findViewById(R.id.progress_bar);
        progress_bar.setVisibility(View.VISIBLE);

        setUpToolbar(root);
        empty_textview = root.findViewById(R.id.empty_textview);

        firebaseAuth = FirebaseAuth.getInstance();
        recyclerView = root.findViewById(R.id.postrecyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);
        posts = new ArrayList<>();
        adapterPosts = new AdapterPosts(getActivity(), posts, true);
        recyclerView.setAdapter(adapterPosts);
        loadPosts();
        return root;
    }

    private void loadPosts() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Notices");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                posts.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    ModelPost modelPost = dataSnapshot1.getValue(ModelPost.class);
                    posts.add(modelPost);
                }


                if(posts.size()==0){
                    recyclerView.setVisibility(View.INVISIBLE);
                    empty_textview.setVisibility(View.VISIBLE);
                } else {
                    if(adapterPosts!=null){
                        adapterPosts.updateData(posts);
                    }
                    recyclerView.setVisibility(View.VISIBLE);
                    empty_textview.setVisibility(View.INVISIBLE);

                }
                progress_bar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(getActivity(), databaseError.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    private void setUpToolbar(View root) {
        Toolbar toolbar = root.findViewById(R.id.myToolBar);
        toolbar.setOnMenuItemClickListener(item -> {
            int itemId = item.getItemId();// action with ID action_refresh was selected
            if (itemId == R.id.add_post) {
                if(isValid()){
                    Bundle bundle = new Bundle();
                    bundle.putString("from","notice");
                    NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
                    navController.navigate(R.id.addPostFragment,bundle);
                } else {
                    Toast.makeText(requireContext(), "Only Admin can post notice", Toast.LENGTH_SHORT).show();
                }
            }
            return false;
        });
    }

    public static boolean isValid(){
        try{
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            String email = mAuth.getCurrentUser().getEmail();
            return email.equals("principal@gcoej.edu");
        } catch (Exception ignored){
            return false;
        }
    }
}