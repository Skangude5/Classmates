package com.skangude5.classmates.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.skangude5.classmates.R;
import com.skangude5.classmates.model.Badge;

import java.util.ArrayList;

public class CustomRecyclerViewAdapterForBadgeItem  extends RecyclerView.Adapter<CustomRecyclerViewAdapterForBadgeItem.ViewHolder> {

    private ArrayList<Badge> localDataSet;
    private Context context;

    public void updateData(ArrayList<Badge> badges) {
        localDataSet = badges;
        this.notifyDataSetChanged();
    }

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView badge_type;
        private final ImageView badge_image;
        private final TextView badge_level;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            badge_type = (TextView) view.findViewById(R.id.badge_type);
            badge_level = (TextView) view.findViewById(R.id.badge_level);
            badge_image = (ImageView) view.findViewById(R.id.badge_image);
        }

        public TextView getBadge_type() {
            return badge_type;
        }

        public ImageView getBadge_image() {
            return badge_image;
        }

        public TextView getBadge_level() {
            return badge_level;
        }
    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used
     * by RecyclerView.
     */
    public CustomRecyclerViewAdapterForBadgeItem(ArrayList<Badge> dataSet, Context context) {
        localDataSet = dataSet;
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recycler_view_item_for_badge, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.getBadge_type().setText(localDataSet.get(position).getBadgeType());
        viewHolder.getBadge_level().setText(localDataSet.get(position).getBadgeLevel());
        Drawable drawable = context.getResources().getDrawable(localDataSet.get(position).getBadgeImageId());
        viewHolder.getBadge_image().setImageDrawable(drawable);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}

