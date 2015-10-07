package com.github.pengrad.podcasts.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.github.pengrad.podcasts.MyApp;
import com.github.pengrad.podcasts.R;
import com.github.pengrad.podcasts.model.PodcastModel;
import com.github.pengrad.podcasts.model.data.Podcast;

import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SearchActivity extends AppCompatActivity implements SearchView.OnQueryTextListener, MenuItemCompat.OnActionExpandListener {

    public static final String ACTION = "SEARCH";
    public static final String EXTRA_QUERY = "query";

    public static void start(Activity context, String query) {
        Intent intent = new Intent(context, SearchActivity.class);
        intent.setAction(ACTION);
        intent.putExtra(EXTRA_QUERY, query);
        context.startActivity(intent);
    }

    @Inject PodcastModel mPodcastModel;

    @Bind(R.id.recycler_view) RecyclerView mRecyclerView;
    @Bind(R.id.progressBar) ProgressBar mProgressBar;
    @Bind(R.id.emptyView) View mEmptyView;

    ItunesSearchRecyclerAdapter mItunesSearchAdapter;
    String mQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        MyApp.get(this).getAppComponent().inject(this);
        ButterKnife.bind(this);

        mItunesSearchAdapter = new ItunesSearchRecyclerAdapter(this::onItemClicked);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mItunesSearchAdapter);

        if (savedInstanceState != null) {
            mQuery = savedInstanceState.getString(EXTRA_QUERY);
        } else {
            mQuery = getIntent().getStringExtra(EXTRA_QUERY);
        }

        searchPodcasts(mQuery);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(EXTRA_QUERY, mQuery);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem menuItem = menu.findItem(R.id.menu_search);
        MenuItemCompat.expandActionView(menuItem);
        MenuItemCompat.setOnActionExpandListener(menuItem, this);

        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQuery(mQuery, false);
        searchView.setOnQueryTextListener(this);
        searchView.clearFocus();

        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) { return false; }

    @Override
    public boolean onMenuItemActionExpand(MenuItem item) { return true; }

    @Override
    public boolean onMenuItemActionCollapse(MenuItem item) {
        finish();
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
        View viewImage = view.findViewById(R.id.podcastImage);
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, viewImage, "");
        PodcastChannelActivity.start(this, podcast, options.toBundle());
    }
}
