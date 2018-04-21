package edu.uoc.android.restservice.rest.adapter;

import java.util.List;

import edu.uoc.android.restservice.rest.contants.ApiConstants;
import edu.uoc.android.restservice.rest.model.Follower;
import edu.uoc.android.restservice.rest.model.Owner;
import edu.uoc.android.restservice.rest.service.GitHubService;
import retrofit2.Call;

public class GitHubAdapter extends BaseAdapter implements GitHubService {

    private GitHubService gitHubService;

    public GitHubAdapter() {
        super(ApiConstants.BASE_GITHUB_URL);
        gitHubService = createService(GitHubService.class);
    }

    @Override
    public Call<Owner> getOwner(String owner) {
        return gitHubService.getOwner(owner);
    }

    /**
     * New adapter method created to call for owner followers.
     * @param owner name of the owner which followers have been requested.
     * @return a call for a list of followers.
     */
    @Override
    public Call<List<Follower>> getFollowers(String owner) {
        return gitHubService.getFollowers(owner);
    }
}
