package com.example.vincent.repodetail;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vincent.repodetail.rest.GitHubService;
import com.example.vincent.repodetail.rest.Repository;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class RepositoryDetailActivity extends Activity {
    private String userName = "vferries";
    private String repoName = "iut-android";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repository_detail);
        repoName = getIntent().getStringExtra("repoName");
        final TextView repoNameTextView = (TextView) findViewById(R.id.repoName);
        final TextView repoDescriptionTextView = (TextView) findViewById(R.id.repoDescription);
        final TextView repoCreatedAtTextView = (TextView) findViewById(R.id.repoCreatedAt);
        final TextView repoUserNameTextView = (TextView) findViewById(R.id.repoUsername);
        final ImageView repoUserImage = (ImageView) findViewById(R.id.repoUserImage);

        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.github.com").addConverterFactory(GsonConverterFactory.create()).build();
        Call<Repository> call = retrofit.create(GitHubService.class).getRepositoryDetail(userName, repoName);
        call.enqueue(new Callback<Repository>() {
            @Override
            public void onResponse(Response<Repository> response, Retrofit retrofit) {
                Repository repo = response.body();
                repoNameTextView.setText(repo.getName());
                repoDescriptionTextView.setText(repo.getDescription());
                repoCreatedAtTextView.setText(getResources().getString(R.string.created_at) + repo.getCreated_at());
                repoUserNameTextView.setText(repo.getOwner().getLogin());
                new DownloadImageTask(repoUserImage).execute(repo.getOwner().getAvatar_url());
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e(this.getClass().getName(), t.getMessage());
            }
        });

        Button githubButton = (Button) findViewById(R.id.githubButton);
        githubButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://github.com/vferries/iut-android"));
                startActivity(intent);
            }
        });
    }
}
