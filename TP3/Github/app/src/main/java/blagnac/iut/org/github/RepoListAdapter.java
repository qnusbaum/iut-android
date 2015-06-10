package blagnac.iut.org.github;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import blagnac.iut.org.github.github.Repo;

/**
 * Created by vincent on 07/06/15.
 */
public class RepoListAdapter extends ArrayAdapter<Repo> {
    static class ViewHolderItem {
        TextView name;
        TextView description;
        TextView forksCount;
    }

    private List<Repo> listRepos = new ArrayList<>();
    private Context context;

    public RepoListAdapter(Context context, int resource) {
        super(context, resource);
        this.context = context;
    }

    @Override
    public int getCount() {
        return listRepos.size();
    }

    @Override
    public Repo getItem(int position) {
        return listRepos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolderItem viewHolder;
        if (convertView == null){
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(R.layout.list_element, parent, false);
            viewHolder = new ViewHolderItem();
            viewHolder.name = (TextView) convertView.findViewById(R.id.item_name);
            viewHolder.description = (TextView) convertView.findViewById(R.id.item_description);
            viewHolder.forksCount = (TextView) convertView.findViewById(R.id.item_forks_count);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolderItem) convertView.getTag();
        }
        Repo repo = getItem(position);
        if (repo != null) {
            viewHolder.name.setText(repo.getName());
            viewHolder.description.setText(repo.getDescription());
            viewHolder.forksCount.setText(repo.getForks_count());
        }

        return convertView;
    }

    public void setRepos(List<Repo> repos) {
        listRepos = repos;
        notifyDataSetChanged();
    }
}
