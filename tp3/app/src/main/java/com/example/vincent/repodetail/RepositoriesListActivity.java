package com.example.vincent.repodetail;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RepositoriesListActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_repositories_list);
        ListView listView = (ListView) findViewById(R.id.listView);
        final RepoListAdapter repoListAdapter = new RepoListAdapter(this);
        listView.setAdapter(repoListAdapter);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GitHubService service = retrofit.create(GitHubService.class);
        Call<List<Repository>> repositoryList = service.getRepositoryList("vferries");
        repositoryList.enqueue(new Callback<List<Repository>>() {
            @Override
            public void onResponse(Call<List<Repository>> call, Response<List<Repository>> response) {
                List<Repository> repos = response.body();
                repoListAdapter.addAll(repos);
            }

            @Override
            public void onFailure(Call<List<Repository>> call, Throwable t) {
                Log.e("RepositoryList", "Impossible de récupérer les repositories", t);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Repository repo = repoListAdapter.getItem(position);
                Intent detailIntent = new Intent(RepositoriesListActivity.this, RepositoryDetailActivity.class);
                //TODO Envoyer le repo en paramètre de l'intent...
                detailIntent.putExtra("repoName", repo.getName());
                detailIntent.putExtra("userName", repo.getOwner().getLogin());

                startActivity(detailIntent);
            }
        });
    }
}
