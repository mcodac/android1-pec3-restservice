package edu.uoc.android.restservice.rest.service;

import java.util.List;

import edu.uoc.android.restservice.rest.contants.ApiConstants;
import edu.uoc.android.restservice.rest.model.Follower;
import edu.uoc.android.restservice.rest.model.Owner;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GitHubService {

    @GET(ApiConstants.GITHUB_USER_ENDPOINT)
    Call<Owner> getOwner(@Path("owner") String owner);

    // Resolve service uri for get followers request.
    @GET(ApiConstants.GITHUB_USER_FOLLOWERS_ENDPOINT)
    Call<List<Follower>> getFollowers(@Path("owner") String owner);
}
