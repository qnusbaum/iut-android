package com.example.android.tp4;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView listView = findViewById(R.id.listView);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GitHubService service = retrofit.create(GitHubService.class);
        Call<List<Repository>> call = service.getReposFromUser("angular");
        final MyAdapter myAdapter = new MyAdapter(getApplicationContext());
        listView.setAdapter(myAdapter);
        call.enqueue(new Callback<List<Repository>>() {
            @Override
            public void onResponse(Call<List<Repository>> call, Response<List<Repository>> response) {
                List<Repository> listRepos = response.body();
                Log.i("RETROFIT", "success !");
                myAdapter.setList(listRepos);
            }

            @Override
            public void onFailure(Call<List<Repository>> call, Throwable t) {
                Log.i("RETROFIT","Call failed");
            }
        });
    }
}


    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button githubButton = findViewById(R.id.githubButton);
        githubButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://github.com/vferries/iut-android"));
                startActivity(intent);
            }
        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GitHubService service = retrofit.create(GitHubService.class);
        retrofit2.Call<Repository> call = service.getRepo("angular", "angular");
        call.enqueue(new Callback<Repository>() {
            @Override
            public void onResponse(retrofit2.Call<Repository> call, Response<Repository> response) {
                Log.i("RETROFIT", response.body().getName());
                Repository repository = response.body();
            }

            @Override
            public void onFailure(retrofit2.Call<Repository> call, Throwable t) {
                Log.i("RETROFIT","Call failed");
            }
        });
    }*/