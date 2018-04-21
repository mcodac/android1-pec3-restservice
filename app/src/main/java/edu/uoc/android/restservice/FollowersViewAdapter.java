package edu.uoc.android.restservice;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import edu.uoc.android.restservice.rest.model.Follower;

/**
 * This class represents a view adapter for the list of user followers.
 */
public class FollowersViewAdapter extends RecyclerView.Adapter<FollowersViewAdapter.FollowersViewHolder> {
    private List<Follower> mDataset;

    /**
     * This class represent the container for the views elements that represent a follower item.
     */
    public static class FollowersViewHolder
            extends RecyclerView.ViewHolder {

        // The view holder contains a text view for the name and a image view
        // for the profile image.
        private ImageView mImageView;
        private TextView mTextView;

        /**
         * FollowersViewHolder class constructor
         *
         * @param itemView
         */
        public FollowersViewHolder(View itemView) {
            super(itemView);

            // Set views
            mImageView = itemView.findViewById(R.id.follower_profile_image_view);
            mTextView = itemView.findViewById(R.id.follower_name_text_view);
        }

        /**
         * This method set the contents of the view holder view elements
         *
         * @param f
         */
        public void bindFollower(Follower f) {
            // Set profile image using Picssso library
            Picasso.get().load(f.getAvatarUrl()).into(mImageView);
            // Set the follower name
            mTextView.setText(f.getLogin());
        }
    }

    /**
     * FollowersViewAdapter class constructor
     *
     * @param myDataset list of followers used the fill the view.
     */
    public FollowersViewAdapter(List<Follower> myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public FollowersViewAdapter.FollowersViewHolder onCreateViewHolder(ViewGroup parent,
                                                                       int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.follower_layout, parent, false);

        // Creates a new FollowersViewHolder.
        FollowersViewHolder vh = new FollowersViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(FollowersViewHolder holder, int position) {
        // Get element from your dataset at this position and fill the contents
        // of the views with that element contents
        holder.bindFollower(mDataset.get(position));
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
