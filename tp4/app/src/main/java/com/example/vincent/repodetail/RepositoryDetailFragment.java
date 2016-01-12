package com.example.vincent.repodetail;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vincent.repodetail.rest.DummyGitHubService;
import com.example.vincent.repodetail.rest.Repository;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class RepositoryDetailFragment extends Fragment {
    private String userName = "vferries";
    private String repoName = "iut-android";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments().containsKey(RepositoriesListActivity.USER_NAME_KEY) && getArguments().containsKey(RepositoriesListActivity.REPO_NAME_KEY)) {
            userName = getArguments().getString(RepositoriesListActivity.USER_NAME_KEY);
            repoName = getArguments().getString(RepositoriesListActivity.REPO_NAME_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_repository_detail, container, false);
        if (userName != null && repoName != null) {
            final TextView repoNameTextView = (TextView) rootView.findViewById(R.id.repoName);
            final TextView repoDescriptionTextView = (TextView) rootView.findViewById(R.id.repoDescription);
            final TextView repoCreatedAtTextView = (TextView) rootView.findViewById(R.id.repoCreatedAt);
            final TextView repoUserNameTextView = (TextView) rootView.findViewById(R.id.repoUsername);
            final ImageView repoUserImage = (ImageView) rootView.findViewById(R.id.repoUserImage);

            Call<Repository> call = new DummyGitHubService().getRepositoryDetail(userName, repoName);
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

            Button githubButton = (Button) rootView.findViewById(R.id.githubButton);
            githubButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("https://github.com/" + userName + "/" + repoName));
                    startActivity(intent);
                }
            });
        }
        return rootView;
    }
}
