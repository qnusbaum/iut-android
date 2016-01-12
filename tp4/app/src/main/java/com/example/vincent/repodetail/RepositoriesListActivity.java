package com.example.vincent.repodetail;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.vincent.repodetail.rest.DummyGitHubService;
import com.example.vincent.repodetail.rest.GitHubService;
import com.example.vincent.repodetail.rest.Repository;

import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class RepositoriesListActivity extends FragmentActivity implements RepositoriesListFragment.Callbacks {
    public static final String USER_NAME_KEY = "user-name";
    public static final String REPO_NAME_KEY = "repo-name";
    private boolean twoPane;
    private String userName = "vferries";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repositories_list);

        if (findViewById(R.id.detailFrameLayout) != null) {
            twoPane = true;
            ((RepositoriesListFragment) getSupportFragmentManager().findFragmentById(
                    R.id.listFragment)).setActivateOnItemClick(true);
        }
    }

    @Override
    public void onItemSelected(String repoName) {
        if (twoPane) {
            Bundle arguments = new Bundle();
            arguments.putString(USER_NAME_KEY, userName);
            arguments.putString(REPO_NAME_KEY, repoName);
            RepositoryDetailFragment fragment = new RepositoryDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detailFrameLayout, fragment).commit();
        } else {
            Intent detailIntent = new Intent(this, RepositoryDetailActivity.class);
            detailIntent.putExtra(USER_NAME_KEY, userName);
            detailIntent.putExtra(REPO_NAME_KEY, repoName);
            startActivity(detailIntent);
        }
    }
}
