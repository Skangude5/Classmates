package com.skangude5.classmates.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.skangude5.classmates.R;
import com.skangude5.classmates.model.Badge;

import java.util.ArrayList;

public class CustomRecyclerViewAdapterForUserBadge  extends RecyclerView.Adapter<CustomRecyclerViewAdapterForUserBadge.ViewHolder> {

    private ArrayList<Badge> localDataSet;
    private Context context;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView badge_image;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            badge_image = (ImageView) view.findViewById(R.id.user_badge_image);
        }

        public ImageView getBadge_image() {
            return badge_image;
        }

    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used
     * by RecyclerView.
     */
    public CustomRecyclerViewAdapterForUserBadge(ArrayList<Badge> dataSet, Context context) {
        localDataSet = dataSet;
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recycler_view_item_for_user_badge, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        Drawable drawable = context.getResources().getDrawable(localDataSet.get(position).getBadgeImageId());
        viewHolder.getBadge_image().setImageDrawable(drawable);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}


