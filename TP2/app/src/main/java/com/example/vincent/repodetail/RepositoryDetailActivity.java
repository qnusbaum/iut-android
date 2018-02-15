package com.example.vincent.repodetail;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Path;

public class RepositoryDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repository_detail_constraint);
        Button githubButton = findViewById(R.id.githubButton);
        githubButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://github.com/vferries/iut-android"));
                startActivity(intent);
            }
        });

        final TextView repoName = findViewById(R.id.repoName);
        final TextView repoCreationDate = findViewById(R.id.createdAt);
        final TextView repoDescription = findViewById(R.id.repoDescription);
        final ImageView repoImage = findViewById(R.id.repoUserImage);
        final DownloadImageTask image = new DownloadImageTask(repoImage);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GitHubService service = retrofit.create(GitHubService.class);
        Call<Repository> call = service.getRepo("angular", "angular");
        call.enqueue(new Callback<Repository>() {
            @Override
            public void onResponse(Call<Repository> call, Response<Repository> response) {
                Log.i("RETROFIT", response.body().getName());
                Repository repository = response.body();
                repoName.setText(repository.getName());
                repoCreationDate.setText("Created at " + repository.getCreatedAt());
                repoDescription.setText(repository.getDescription());
                image.execute("https://avatars3.githubusercontent.com/u/139426?v=4");
            }

            @Override
            public void onFailure(Call<Repository> call, Throwable t) {
                Log.i("RETROFIT","Call failed");
            }
        });
    }
}
