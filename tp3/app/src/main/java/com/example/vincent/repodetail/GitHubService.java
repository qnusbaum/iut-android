package com.example.vincent.repodetail;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GitHubService {
    @GET("repos/{user}/{repo}")
    Call<Repository> getRepositoryDetail(@Path("user") String username, @Path("repo") String repoName);

    @GET("users/{user}/repos")
    Call<List<Repository>> getRepositoryList(@Path("user") String username);
}
