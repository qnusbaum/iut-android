package com.example.vincent.repodetail;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.vincent.repodetail.rest.DummyGitHubService;
import com.example.vincent.repodetail.rest.Repository;

import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class RepositoriesListFragment extends ListFragment {
    public interface Callbacks {
        public void onItemSelected(String repoName);
    }

    private static Callbacks dummyCallbacks = new Callbacks() {
        @Override
        public void onItemSelected(String repoName) {
        }
    };

    private static final String STATE_ACTIVATED_POSITION = "activated_position";
    private String userName = "vferries";
    private RepositoryListAdapter adapter;
    private Callbacks callbacks = dummyCallbacks;
    private int activatedPosition = ListView.INVALID_POSITION;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new RepositoryListAdapter(getActivity(), R.layout.repo_item);
        setListAdapter(adapter);
        Call<List<Repository>> call = new DummyGitHubService().getRepositoriesList(userName);
        call.enqueue(new Callback<List<Repository>>() {
            @Override
            public void onResponse(Response<List<Repository>> response, Retrofit retrofit) {
                List<Repository> repos = response.body();
                adapter.setRepositories(repos);
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e(RepositoriesListFragment.class.getName(), t.getMessage());
            }
        });
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (savedInstanceState != null
                && savedInstanceState.containsKey(STATE_ACTIVATED_POSITION)) {
            setActivatedPosition(savedInstanceState
                    .getInt(STATE_ACTIVATED_POSITION));
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (!(activity instanceof Callbacks)) {
            throw new IllegalStateException("Activity must implement fragment's callbacks.");
        }
        callbacks = (Callbacks) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callbacks = dummyCallbacks;
    }

    @Override
    public void onListItemClick(ListView listView, View view, int position, long id) {
        super.onListItemClick(listView, view, position, id);
        callbacks.onItemSelected(adapter.getItem(position).getName());
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (activatedPosition != ListView.INVALID_POSITION) {
            outState.putInt(STATE_ACTIVATED_POSITION, activatedPosition);
        }
    }

    public void setActivateOnItemClick(boolean activateOnItemClick) {
        getListView().setChoiceMode(
                activateOnItemClick ? ListView.CHOICE_MODE_SINGLE
                        : ListView.CHOICE_MODE_NONE);
    }

    private void setActivatedPosition(int position) {
        if (position == ListView.INVALID_POSITION) {
            getListView().setItemChecked(activatedPosition, false);
        } else {
            getListView().setItemChecked(position, true);
        }
        activatedPosition = position;
    }
}
