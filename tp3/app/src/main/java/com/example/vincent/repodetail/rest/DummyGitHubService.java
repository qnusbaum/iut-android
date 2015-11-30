package com.example.vincent.repodetail.rest;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.http.Path;

/**
 * Created by vincent on 30/11/15.
 */
public class DummyGitHubService implements GitHubService {
    private Map<String, Repository> map = new HashMap<>();

    public DummyGitHubService() {
        User vf = new User();
        vf.setLogin("Vincent Ferries");
        vf.setAvatar_url("https://avatars.githubusercontent.com/u/2457031?v=3");
        User facebook = new User();
        facebook.setLogin("facebook");
        facebook.setAvatar_url("https://avatars.githubusercontent.com/u/69631?v=3");
        addRepository("iut-android", "Repository du cours pour l'iut Blagnac", "30/11/2015", 12, vf);
        addRepository("apprendre", "Des ressources pour apprendre le code", "31/11/2015", 3, vf);
        addRepository("react", "Framework JS so 2015", "32/11/2015", 12567, facebook);
    }

    private void addRepository(String name, String description, String created_at, int forks_count, User owner) {
        Repository repository = new Repository();
        repository.setName(name);
        repository.setCreated_at(created_at);
        repository.setForks_count(forks_count);
        repository.setDescription("Description du repository");
        repository.setOwner(owner);
        map.put(name, repository);
    }

    @Override
    public Call<Repository> getRepositoryDetail(@Path("user") String username, @Path("repo") String repoName) {
        return new DummyCall<>(map.get(repoName));
    }

    @Override
    public Call<List<Repository>> getRepositoriesList(@Path("owner") String username) {
        return null;
    }

    private class DummyCall<T> implements Call<T> {
        private T result;

        public DummyCall(T result) {
            this.result = result;
        }

        @Override
        public Response execute() throws IOException {
            return Response.success(result);
        }

        @Override
        public void enqueue(Callback callback) {
            Response response = Response.success(result);
            callback.onResponse(response, null);
        }

        @Override
        public void cancel() {

        }

        @Override
        public Call clone() {
            return null;
        }
    }
}
