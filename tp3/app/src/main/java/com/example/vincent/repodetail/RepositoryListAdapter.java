package com.example.vincent.repodetail;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.vincent.repodetail.rest.Repository;

import java.util.ArrayList;
import java.util.List;

public class RepositoryListAdapter extends ArrayAdapter<Repository> {
    private class ViewHolderItem {
        public TextView name;
        public TextView description;
        public TextView forksCount;
    }

    private List<Repository> repositories = new ArrayList<>();
    private Context context;
    private int layoutId;

    public RepositoryListAdapter(Context context, int resource) {
        super(context, resource);
        this.context = context;
        this.layoutId = resource;
    }

    @Override
    public int getCount() {
        return repositories.size();
    }

    @Override
    public Repository getItem(int position) {
        return repositories.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolderItem viewHolder;
        if (convertView == null){
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(layoutId, parent, false);
            viewHolder = new ViewHolderItem();
            viewHolder.name = (TextView) convertView.findViewById(R.id.repoName);
            viewHolder.description = (TextView) convertView.findViewById(R.id.repoDescription);
            viewHolder.forksCount = (TextView) convertView.findViewById(R.id.fork_count);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolderItem) convertView.getTag();
        }
        Repository repo = getItem(position);
        if (repo != null) {
            viewHolder.name.setText(repo.getName());
            viewHolder.description.setText(repo.getDescription());
            viewHolder.forksCount.setText(String.valueOf(repo.getForks_count()));
        }

        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setRepositories(List<Repository> repositories) {
        this.repositories = repositories;
        notifyDataSetChanged();
    }
}
