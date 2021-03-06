package com.skangude5.classmates.main.ui.feed;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.skangude5.classmates.R;
import com.skangude5.classmates.adapters.AdapterPosts;
import com.skangude5.classmates.databinding.FragmentFeedBinding;
import com.skangude5.classmates.model.ModelPost;

import java.util.ArrayList;
import java.util.List;


public class FeedFragment extends Fragment {

    private FeedViewModel feedViewModel;
    private FragmentFeedBinding binding;

    FirebaseAuth firebaseAuth;
    String myuid;
    RecyclerView recyclerView;
    List<ModelPost> posts;
    AdapterPosts adapterPosts;

    private TextView empty_textview;
    private ProgressBar progress_bar;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        feedViewModel =
                new ViewModelProvider(this).get(FeedViewModel.class);

        binding = FragmentFeedBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

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
        adapterPosts = new AdapterPosts(getActivity(), posts, false);
        recyclerView.setAdapter(adapterPosts);
        loadPosts();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void loadPosts() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Posts");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                posts.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    ModelPost modelPost = dataSnapshot1.getValue(ModelPost.class);
                    posts.add(modelPost);
                    //
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
                Bundle bundle = new Bundle();
                bundle.putString("from","feed");
                NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
                navController.navigate(R.id.addPostFragment,bundle);
            }
            return false;
        });
    }

}