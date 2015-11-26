package com.example.vincent.repodetail;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;

public interface GitHubService {
    @GET("/repos/{user}/{repo}")
    Call<Repo> getRepoDetail(@Path("user") String username, @Path("repo") String repoName);
}
