package com.example.vincent.repodetail;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.example.vincent.repodetail.rest.GitHubService;
import com.example.vincent.repodetail.rest.Repository;

import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class RepositoriesListActivity extends ListActivity {
    private String userName = "vferries";
    private RepositoryListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repositories_list);
        adapter = new RepositoryListAdapter(this, R.layout.repo_item);
        setListAdapter(adapter);

        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.github.com").addConverterFactory(GsonConverterFactory.create()).build();
        Call<List<Repository>> call = retrofit.create(GitHubService.class).getRepositoriesList(userName);
        call.enqueue(new Callback<List<Repository>>() {
            @Override
            public void onResponse(Response<List<Repository>> response, Retrofit retrofit) {
                List<Repository> repos = response.body();
                adapter.setRepositories(repos);
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e(this.getClass().getName(), t.getMessage());
            }
        });
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Intent detailIntent = new Intent(this, RepositoryDetailActivity.class);
        detailIntent.putExtra("repoName", adapter.getItem(position).getName());
        startActivity(detailIntent);
    }
}
