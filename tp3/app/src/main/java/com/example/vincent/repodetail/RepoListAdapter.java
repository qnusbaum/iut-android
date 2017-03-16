package com.example.vincent.repodetail;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class RepoListAdapter extends ArrayAdapter<Repository> {
    private Context context;
    private List<Repository> repositories = new ArrayList();

    public RepoListAdapter(Context context) {
        super(context, 0);
        this.context = context;
    }

    public void addAll(List<Repository> repositories) {
        this.repositories.addAll(repositories);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return repositories.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = ((Activity)context).getLayoutInflater().inflate(R.layout.repository_detail, parent, false);

        Repository repository = getItem(position);

        TextView repoName = (TextView) v.findViewById(R.id.repoName);
        TextView forksCount = (TextView) v.findViewById(R.id.forksCount);
        TextView repoDescription = (TextView) v.findViewById(R.id.repoDescription);

        repoName.setText(repository.getName());
        repoDescription.setText(repository.getDescription());
        forksCount.setText("" + repository.getForks_count());

        return v;
    }

    @Nullable
    @Override
    public Repository getItem(int position) {
        return repositories.get(position);
    }
}
