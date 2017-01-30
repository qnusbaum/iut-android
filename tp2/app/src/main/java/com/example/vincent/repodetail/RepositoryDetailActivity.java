package com.example.vincent.repodetail;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RepositoryDetailActivity extends AppCompatActivity {
    private String userName = "vferries";
    private String repoName = "iut-android";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repository_detail);
        final TextView repoNameTextView = (TextView) findViewById(R.id.repoName);
        final TextView repoDescriptionTextView = (TextView) findViewById(R.id.repoDescription);
        final TextView repoCreatedAtTextView = (TextView) findViewById(R.id.repoCreatedAt);
        final TextView repoUserNameTextView = (TextView) findViewById(R.id.repoUsername);
        final ImageView repoUserImage = (ImageView) findViewById(R.id.repoUserImage);

        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.github.com").addConverterFactory(GsonConverterFactory.create()).build();
        Call<Repo> call = retrofit.create(GitHubService.class).getRepoDetail(userName, repoName);
        call.enqueue(new Callback<Repo>() {
            @Override
            public void onResponse(Call<Repo> call, Response<Repo> response) {
                Repo repo = response.body();
                repoNameTextView.setText(repo.getName());
                repoDescriptionTextView.setText(repo.getDescription());
                repoCreatedAtTextView.setText(repo.getCreated_at());
                repoUserNameTextView.setText(repo.getOwner().getLogin());
                new DownloadImageTask(repoUserImage).execute(repo.getOwner().getAvatar_url());
            }

            @Override
            public void onFailure(Call<Repo> call, Throwable t) {
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
