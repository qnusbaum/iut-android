package com.example.vincent.repodetail;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class RepoListActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.repo_list);
        ListView listView = (ListView) findViewById(R.id.repoList);
        final MyAdapter myAdapter = new MyAdapter(this, R.layout.repo_item);
        listView.setAdapter(myAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Repo repo = myAdapter.getItem(position);
                Intent intent = new Intent(RepoListActivity.this, RepositoryDetailActivity.class);
                intent.putExtra("repoName", repo.getName());
                startActivity(intent);
            }
        });

        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.github.com").addConverterFactory(GsonConverterFactory.create()).build();
        Call<List<Repo>> call = retrofit.create(GitHubService.class).getReposFromUser("vferries");
        call.enqueue(new Callback<List<Repo>>() {
            @Override
            public void onResponse(Response<List<Repo>> response, Retrofit retrofit) {
                myAdapter.setListRepos(response.body());
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("RepoListActivity", t.getMessage());
            }
        });

    }
}
