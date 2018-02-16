package com.example.android.tp4;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Android on 15/02/2018.
 */

public interface GitHubService {
    @GET("repos/{owner}/{repo}")
    Call<Repository> getRepo(@Path("owner") String owner, @Path("repo") String repo);

    @GET("/users/{owner}/repos")
    Call<List<Repository>> getReposFromUser(@Path("owner") String owner);
}
