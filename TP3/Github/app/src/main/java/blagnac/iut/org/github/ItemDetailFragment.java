package blagnac.iut.org.github;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import blagnac.iut.org.github.dummy.DummyContent;
import blagnac.iut.org.github.github.GitHubService;
import blagnac.iut.org.github.github.Repo;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a {@link ItemListActivity}
 * in two-pane mode (on tablets) or a {@link ItemDetailActivity}
 * on handsets.
 */
public class ItemDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private DummyContent.DummyItem mItem;
    private GitHubService service;
    private String name;
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ItemDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        name = getArguments().getString(ARG_ITEM_ID);
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("https://api.github.com")
                .build();
        service = restAdapter.create(GitHubService.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_item_detail, container, false);
        final Button button = (Button)rootView.findViewById(R.id.viewGithubButton);
        final TextView repoName = (TextView) rootView.findViewById(R.id.repoName);
        final TextView repoDescription = (TextView) rootView.findViewById(R.id.repoDescription);
        final TextView repoCreatedAt = (TextView) rootView.findViewById(R.id.repoCreatedAt);
        final TextView userName = (TextView) rootView.findViewById(R.id.userName);
        final ImageView avatar = (ImageView) rootView.findViewById(R.id.userImage);

        service.getRepoDetail("vferries", name, new Callback<Repo>() {
            @Override
            public void success(final Repo repo, Response response) {
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(repo.getHtml_url()));
                        getActivity().startActivity(intent);
                    }
                });
                repoName.setText(repo.getName());
                repoDescription.setText(repo.getDescription());
                repoCreatedAt.setText(repo.getCreated_at());
                userName.setText(repo.getOwner().getLogin());
                new DownloadImageTask(avatar).execute(repo.getOwner().getAvatar_url());
            }
            @Override
            public void failure(RetrofitError retrofitError) {
                Log.e("DETAIL", "Error retrieving repo from Github");
                retrofitError.printStackTrace();
            }
        });

        return rootView;
    }
}
