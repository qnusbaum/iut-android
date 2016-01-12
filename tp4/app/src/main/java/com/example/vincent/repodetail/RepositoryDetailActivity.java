package com.example.vincent.repodetail;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vincent.repodetail.rest.DummyGitHubService;
import com.example.vincent.repodetail.rest.GitHubService;
import com.example.vincent.repodetail.rest.Repository;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class RepositoryDetailActivity extends FragmentActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repository_detail_container);

        if (savedInstanceState == null) {
            Bundle arguments = new Bundle();
            arguments.putString(RepositoriesListActivity.USER_NAME_KEY, getIntent().getStringExtra(RepositoriesListActivity.USER_NAME_KEY));
            arguments.putString(RepositoriesListActivity.REPO_NAME_KEY, getIntent().getStringExtra(RepositoriesListActivity.REPO_NAME_KEY));
            RepositoryDetailFragment fragment = new RepositoryDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.detailContainer, fragment).commit();
        }
    }
}
