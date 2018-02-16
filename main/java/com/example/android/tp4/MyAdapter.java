package com.example.android.tp4;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Created by Android on 15/02/2018.
 */

public class MyAdapter extends ArrayAdapter<Repository> {
    private List<Repository> repositories;
    private final Context context;

    public MyAdapter(@NonNull Context context) {
        super(context, 0);
        this.context = context;
    }

    @Override
    public int getCount() {
        return this.repositories.size();
    }

    public void setList(List<Repository> liste) {
        this.repositories = liste;
        notifyDataSetChanged();
    }

    public void addAll(List<Repository> repositories) {
        this.repositories.addAll((repositories));
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolderItem viewHolder;
        if (convertView == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(R.layout.view_layout, parent, false);
            viewHolder = new ViewHolderItem();
            viewHolder.forkCount = convertView.findViewById(R.id.forkCount);
            viewHolder.name = convertView.findViewById(R.id.name);
            viewHolder.descritpion = convertView.findViewById(R.id.description);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolderItem) convertView.getTag();
        }
        Repository item = getItem(position);
        if (item != null) {
            viewHolder.name.setText(item.getName());
            viewHolder.descritpion.setText(item.getDescription());
            viewHolder.forkCount.setText(item.getForkCount());
        }
        return convertView;
    }

    @Override
    public Repository getItem(int position) {
        return super.getItem(position);
    }
}
