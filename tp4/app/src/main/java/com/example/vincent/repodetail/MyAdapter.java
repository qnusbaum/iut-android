package com.example.vincent.repodetail;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends ArrayAdapter<Repo> {
    private List<Repo> repos = new ArrayList<>();
    private Context context;

    public MyAdapter(Context context, int resource) {
        super(context, resource);
        this.context = context;
    }

    @Override
    public int getCount() {
        return repos.size();
    }

    @Override
    public Repo getItem(int position) {
        return repos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolderItem viewHolder;
        if (convertView == null) {
            convertView = ((Activity) context).getLayoutInflater().inflate(R.layout.repo_item, parent, false);

            viewHolder = new ViewHolderItem();
            viewHolder.name = (TextView) convertView .findViewById(R.id.repoName);
            viewHolder.description = (TextView) convertView .findViewById(R.id.repoDescription);
            viewHolder.forks = (TextView) convertView .findViewById(R.id.forkCount);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolderItem) convertView.getTag();
        }

        Repo repo = getItem(position);
        viewHolder.name.setText(repo.getName());
        viewHolder.description.setText(repo.getDescription());
        viewHolder.forks.setText("" + repo.getForks_count());

        return convertView;
    }

    public void setListRepos(List repos) {
        this.repos = repos;
        notifyDataSetChanged();
    }

    private class ViewHolderItem {
        TextView name;
        TextView description;
        TextView forks;
    }
}
