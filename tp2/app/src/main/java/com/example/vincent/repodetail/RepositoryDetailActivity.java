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

public class RepositoryDetailActivity extends AppCompatActivity {
    private static String USERNAME = "vferries";
    private static String REPONAME = "iut-android";
    private TextView repoNameTextView;
    private TextView repoDescriptionTextView;
    private TextView createdAtTextView;
    private TextView repoUsernameTextView;
    private ImageView repoUserImageView;
    private Button githubButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repository_detail);

        repoNameTextView = (TextView) findViewById(R.id.repoName);
        repoDescriptionTextView = (TextView) findViewById(R.id.repoDescription);
        createdAtTextView = (TextView) findViewById(R.id.createdAt);
        repoUsernameTextView = (TextView) findViewById(R.id.repoUsername);
        repoUserImageView = (ImageView) findViewById(R.id.repoUserImage);
        githubButton = (Button) findViewById(R.id.githubButton);

        GithubService service = new DummyGitHubService();
        Call<Repository> repositoryCall = service.getRepoDetail(USERNAME, REPONAME);
        repositoryCall.enqueue(new Callback<Repository>() {
            @Override
            public void onResponse(Call<Repository> call, Response<Repository> response) {
                refreshView(response.body());
            }

            @Override
            public void onFailure(Call<Repository> call, Throwable t) {
                Log.e("RepositoryDetail", "Failed to retrieve repository detail", t);
            }
        });

    }

    private void refreshView(final Repository repository) {
        repoNameTextView.setText(repository.getName());
        repoDescriptionTextView.setText(repository.getDescription());
        createdAtTextView.setText("Created at " + repository.getCreated_at());
        repoUsernameTextView.setText(repository.getOwner().getLogin());
        githubButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(String.format("https://github.com/%s/%s", repository.getOwner().getLogin(), repository.getName())));
                startActivity(intent);
            }
        });
        new DownloadImageTask(repoUserImageView).execute(repository.getOwner().getAvatar_url());
    }
}
