package blagnac.iut.org.github.github;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

public interface GitHubService {
  @GET("/users/{owner}/repos")
  void getReposFromUser(@Path("owner") String owner, Callback<List<Repo>> callback);

  @GET("/repos/{owner}/{repo}")
  void getRepoDetail(@Path("owner") String owner, @Path("repo") String repo, Callback<Repo> callback);
}