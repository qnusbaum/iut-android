package com.example.vincent.repodetail;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GitHubService {
    @GET("/repos/{user}/{repo}")
    Call<Repo> getRepoDetail(@Path("user") String username, @Path("repo") String repoName);
}
