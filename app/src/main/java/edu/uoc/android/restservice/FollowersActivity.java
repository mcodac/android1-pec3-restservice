package edu.uoc.android.restservice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import edu.uoc.android.restservice.rest.adapter.GitHubAdapter;
import edu.uoc.android.restservice.rest.model.Follower;
import edu.uoc.android.restservice.rest.model.Owner;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Activity that shows git lab user information and followers list.
 */
public class FollowersActivity extends AppCompatActivity {

    // Key value for extra data received through intent
    private final String mUserNameExtra = "user_name";

    // GItHubAdapter instance
    private GitHubAdapter mGitHubAdapter;

    // Views
    private TextView mRepositoriesTextView;
    private TextView mFollowingTextView;
    private TextView mErrorTextView;
    private ImageView mProfileImageView;
    private ProgressBar mProgressView;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_followers);

        // Retrieve user name from intent extra data
        String userName = this.getIntent().getStringExtra(mUserNameExtra);

        // Set views
        mRepositoriesTextView = findViewById(R.id.repositories_text_view);
        mFollowingTextView = findViewById(R.id.following_text_view);
        mErrorTextView = findViewById(R.id.error_text_view);
        mProfileImageView = findViewById(R.id.profile_image_view);
        mProgressView = findViewById(R.id.progress_view);
        mRecyclerView = findViewById(R.id.followers_recycler_view);

        // Use a vertical linear layout manager for recycle view
        mRecyclerView.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        // Creates a GitHubAdapter instance
        mGitHubAdapter = new GitHubAdapter();

        // Launch the request process for user info.
        callForOwner(userName);
    }

    /**
     * This method performs the user information request, and define the callback methods for
     * response.
     *
     * @param userName name of the user to request for
     */
    public void callForOwner(final String userName) {
        // Get the service call for owner request
        Call<Owner> getOwnerCall = mGitHubAdapter.getOwner(userName);

        // Enqueue the user information request and define callback for response.
        getOwnerCall.enqueue(new Callback<Owner>() {

            @Override
            public void onResponse(Call<Owner> call, Response<Owner> response) {
                if (response.isSuccessful()) {
                    // If response have been successful, extract the owner data from the
                    // response body and fill layout views with the received information.
                    // The image view will be filled using Picasso library.
                    Owner owner = response.body();
                    mRepositoriesTextView.append(" " + Integer.toString(owner.getPublicRepos()));
                    mFollowingTextView.append(" " + Integer.toString(owner.getFollowing()));
                    Picasso.get().load(owner.getAvatarUrl()).into(mProfileImageView);

                    // Then, call for user followers
                    callForFollowers(userName);
                } else {
                    // If user was not found, show text view with this message
                    mErrorTextView.setText(getString(R.string.user_not_found));
                    showError(true);
                }
            }

            @Override
            public void onFailure(Call<Owner> call, Throwable t) {
                // In case of error, show a text view with this info.
                mErrorTextView.setText(getString(R.string.request_error));
                showError(true);
            }
        });

        // After the async request call, show the progress view.
        showProgress(true);
    }

    /**
     * This method performs the user followers request, and define the callback methods for
     * response.
     *
     * @param userName name of the user which followers are requested.
     */
    public void callForFollowers(final String userName) {
        // Get the service call for owner followers
        Call<List<Follower>> getFollowersCall = mGitHubAdapter.getFollowers(userName);

        // Enqueue the user followers request and define callback for response.
        getFollowersCall.enqueue(new Callback<List<Follower>>() {

            @Override
            public void onResponse(Call<List<Follower>> call, Response<List<Follower>> response) {
                if (response.isSuccessful()) {
                    // If response have been successful, extract the list of followers
                    List<Follower> followers = response.body();
                    // Specify an adapter for the recycle view using the list of followers.
                    mAdapter = new FollowersViewAdapter(followers);
                    mRecyclerView.setAdapter(mAdapter);

                    // End processing, so switch progress view visualisation.
                    showProgress(false);
                } else {
                    // If request not found, show a text view with a message
                    mErrorTextView.setText(getString(R.string.user_not_found));
                    showError(true);
                }
            }

            @Override
            public void onFailure(Call<List<Follower>> call, Throwable t) {
                // In case of error, show a text view with this info.
                mErrorTextView.setText(getString(R.string.request_error));
                showError(true);
            }
        });
    }

    /**
     * Switch between progress view visualization and the rest of layout views.
     *
     * @param show <code>true</code> if progress view shall be visible, <code>false</code>
     *             if the rest of layout views shall be visible.
     */
    private void showProgress(final boolean show) {
        mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
        mErrorTextView.setVisibility(View.GONE);
        mRepositoriesTextView.setVisibility(show ? View.GONE : View.VISIBLE);
        mFollowingTextView.setVisibility(show ? View.GONE : View.VISIBLE);
        mProfileImageView.setVisibility(show ? View.GONE : View.VISIBLE);
        findViewById(R.id.followers_text_view).setVisibility(show ? View.GONE : View.VISIBLE);
        findViewById(R.id.divider1).setVisibility(show ? View.GONE : View.VISIBLE);
        findViewById(R.id.divider2).setVisibility(show ? View.GONE : View.VISIBLE);
        mRecyclerView.setVisibility(show ? View.GONE : View.VISIBLE);
    }

    /**
     * Switch between error text view visualisation and the rest of layout views.
     * @param show <code>true</code> if text error view shall be visible, <code>false</code>
     *             if the rest of layout views shall be visible.
     */
    private void showError(final boolean show) {
        mErrorTextView.setVisibility(show ? View.VISIBLE : View.GONE);
        mProgressView.setVisibility(View.GONE);
        mRepositoriesTextView.setVisibility(show ? View.GONE : View.VISIBLE);
        mFollowingTextView.setVisibility(show ? View.GONE : View.VISIBLE);
        mProfileImageView.setVisibility(show ? View.GONE : View.VISIBLE);
        findViewById(R.id.followers_text_view).setVisibility(show ? View.GONE : View.VISIBLE);
        findViewById(R.id.divider1).setVisibility(show ? View.GONE : View.VISIBLE);
        findViewById(R.id.divider2).setVisibility(show ? View.GONE : View.VISIBLE);
        mRecyclerView.setVisibility(show ? View.GONE : View.VISIBLE);
    }
}
