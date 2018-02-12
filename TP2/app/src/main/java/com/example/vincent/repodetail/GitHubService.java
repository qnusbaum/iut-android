package com.example.vincent.repodetail;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Android on 12/02/2018.
 */

public interface GitHubService {
    @GET("repos/{owner}/{repo}")
    Call<Repository> getRepo(@Path("owner") String owner, @Path("repo") String repo);
}
