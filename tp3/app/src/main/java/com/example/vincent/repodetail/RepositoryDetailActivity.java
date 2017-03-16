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
    private TextView repoName;
    private TextView repoDescription;
    private TextView repoCreatedAt;
    private TextView username;
    private ImageView userAvatar;
    private Button githubButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repository_detail);

        String repoNameString = getIntent().getStringExtra("repoName");
        String userNameString = getIntent().getStringExtra("userName");

        repoName = (TextView) findViewById(R.id.repoName);
        repoDescription = (TextView) findViewById(R.id.repoDescription);
        repoCreatedAt = (TextView) findViewById(R.id.createdAt);
        username = (TextView) findViewById(R.id.repoUsername);
        userAvatar = (ImageView) findViewById(R.id.repoUserImage);
        githubButton = (Button) findViewById(R.id.githubButton);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GitHubService service = retrofit.create(GitHubService.class);
        Call<Repository> repositoryDetail = service.getRepositoryDetail(userNameString, repoNameString);
        repositoryDetail.enqueue(new Callback<Repository>() {
            @Override
            public void onResponse(Call<Repository> call, Response<Repository> response) {
                Repository repository = response.body();
                updateView(repository);
            }

            @Override
            public void onFailure(Call<Repository> call, Throwable t) {
                Log.e("RepositoryDetail", "Impossible de récupérer user detail", t);
            }
        });
    }

    private void updateView(final Repository repository) {
        if (repository == null) {
            Log.e("RepositoryDetail", "No repository found");
            return;
        }
        repoName.setText(repository.getName());
        repoDescription.setText(repository.getDescription());
        repoCreatedAt.setText(String.format("Created at : %s", repository.getCreated_at()));
        username.setText(repository.getOwner().getLogin());
        githubButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(String.format("https://github.com/%s/%s", repository.getOwner().getLogin(), repository.getName())));
                startActivity(intent);
            }
        });
        new DownloadImageTask(userAvatar).execute(repository.getOwner().getAvatar_url());
    }
}
