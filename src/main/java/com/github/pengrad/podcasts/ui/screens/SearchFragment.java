package com.github.pengrad.podcasts.ui.screens;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.github.pengrad.podcasts.MyApp;
import com.github.pengrad.podcasts.R;
import com.github.pengrad.podcasts.model.PodcastModel;
import com.github.pengrad.podcasts.model.data.Podcast;
import com.github.pengrad.podcasts.ui.adapters.ItunesSearchRecyclerAdapter;

import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SearchFragment extends Fragment implements SearchView.OnQueryTextListener, MenuItemCompat.OnActionExpandListener {

    public static final String EXTRA_QUERY = "query";

    public static SearchFragment create(String query) {
        SearchFragment searchFragment = new SearchFragment();
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_QUERY, query);
        searchFragment.setArguments(bundle);
        return searchFragment;
    }

    @Inject PodcastModel mPodcastModel;

    @Bind(R.id.recycler_view) RecyclerView mRecyclerView;
    @Bind(R.id.progressBar) ProgressBar mProgressBar;
    @Bind(R.id.emptyView) View mEmptyView;

    ItunesSearchRecyclerAdapter mItunesSearchAdapter;
    String mQuery;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        Activity activity = getActivity();

        View view = inflater.inflate(R.layout.activity_search, container, false);
        MyApp.getInjector(activity).inject(this);
        ButterKnife.bind(this, view);

        mItunesSearchAdapter = new ItunesSearchRecyclerAdapter(this::onItemClicked);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(activity));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mItunesSearchAdapter);

        if (savedInstanceState != null) {
            mQuery = savedInstanceState.getString(EXTRA_QUERY);
        } else {
            mQuery = getArguments().getString(EXTRA_QUERY);
        }

        searchPodcasts(mQuery);

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(EXTRA_QUERY, mQuery);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_search, menu);
        MenuItem menuItem = menu.findItem(R.id.menu_search);
        MenuItemCompat.expandActionView(menuItem);
        MenuItemCompat.setOnActionExpandListener(menuItem, this);

        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQuery(mQuery, false);
        searchView.setOnQueryTextListener(this);
        searchView.clearFocus();
    }

    @Override
    public boolean onQueryTextChange(String newText) { return false; }

    @Override
    public boolean onMenuItemActionExpand(MenuItem item) { return true; }

    @Override
    public boolean onMenuItemActionCollapse(MenuItem item) {
        getFragmentManager().popBackStack();
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        mQuery = query;
        searchPodcasts(query);
        return true;
    }

    void searchPodcasts(String query) {
        mItunesSearchAdapter.clear();
        mProgressBar.setVisibility(View.VISIBLE);
        mPodcastModel.searchPodcast(query)
                .onErrorReturn(throwable -> new ArrayList<>(0))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onPodcastsLoaded);
    }

    void onPodcastsLoaded(Collection<Podcast> podcasts) {
        mProgressBar.setVisibility(View.GONE);
        if (podcasts != null) {
            mItunesSearchAdapter.addAll(podcasts);
        }
    }

    void onItemClicked(Podcast podcast, View view, int adapterPosition) {
        Activity activity = getActivity();
        View viewImage = view.findViewById(R.id.podcastImage);
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, viewImage, "");
        PodcastSearchActivity.start(activity, podcast, options.toBundle());
    }
}
