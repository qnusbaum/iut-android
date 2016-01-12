package com.example.vincent.repodetail.rest;

import java.util.List;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;

public interface GitHubService {
    @GET("/repos/{user}/{repo}")
    Call<Repository> getRepositoryDetail(@Path("user") String username, @Path("repo") String repoName);

    @GET("/users/{owner}/repos")
    Call<List<Repository>> getRepositoriesList(@Path("owner") String username);
}
