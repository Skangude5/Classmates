package com.skangude5.classmates.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.skangude5.classmates.R;
import com.skangude5.classmates.model.User;

import java.util.ArrayList;


public class CustomRecyclerViewAdapterForUser  extends RecyclerView.Adapter<CustomRecyclerViewAdapterForUser.ViewHolder> {

    private ArrayList<User> localDataSet;
    private Context context;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView user_name;
        private final ImageView user_image;
        private final RecyclerView recyclerView_user_badges;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            user_name = (TextView) view.findViewById(R.id.user_name);
            recyclerView_user_badges = (RecyclerView) view.findViewById(R.id.recyclerView_user_badges);
            user_image = (ImageView) view.findViewById(R.id.user_image);
        }

        public TextView getUser_name() {
            return user_name;
        }

        public ImageView getUser_image() {
            return user_image;
        }

        public RecyclerView getRecyclerView_user_badges() {
            return recyclerView_user_badges;
        }
    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used
     * by RecyclerView.
     */
    public CustomRecyclerViewAdapterForUser(ArrayList<User> dataSet, Context context) {
        localDataSet = dataSet;
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recycler_view_item_for_user_list, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.getUser_name().setText(localDataSet.get(position).getFull_name());
        CustomRecyclerViewAdapterForUserBadge adapterForUserBadge = new CustomRecyclerViewAdapterForUserBadge(localDataSet.get(position).getBadges(),context);
        RecyclerView recyclerView = viewHolder.getRecyclerView_user_badges();
        recyclerView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
        recyclerView.setAdapter(adapterForUserBadge);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}

