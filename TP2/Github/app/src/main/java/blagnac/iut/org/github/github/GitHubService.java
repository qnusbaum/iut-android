package blagnac.iut.org.github.github;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

public interface GitHubService {
  @GET("/repos/{owner}/{repo}")
  void getRepoDetail(@Path("owner") String owner, @Path("repo") String repo, Callback<Repo> callback);
}