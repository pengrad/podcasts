package com.github.pengrad.podcasts.ui;

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

import java.util.Collection;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener, MenuItemCompat.OnActionExpandListener {

    public static final String TAG = "MainActivity";

    @Inject PodcastModel mPodcastModel;

    @Bind(R.id.recycler_view) RecyclerView mRecyclerView;
    @Bind(R.id.progressBar) ProgressBar mProgressBar;
    @Bind(R.id.emptyView) View mEmptyView;

    ItunesSearchRecyclerAdapter mItunesSearchAdapter;
    MenuItem mSearchMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MyApp.get(this).getAppComponent().inject(this);
        ButterKnife.bind(this);

        mItunesSearchAdapter = new ItunesSearchRecyclerAdapter(this::onItemClicked);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mItunesSearchAdapter);

        showMyPodcasts();
//        SearchActivity.start(this, "bob");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        mSearchMenuItem = menu.findItem(R.id.menu_search);
        MenuItemCompat.setOnActionExpandListener(mSearchMenuItem, this);
        SearchView searchView = (SearchView) mSearchMenuItem.getActionView();
        searchView.setOnQueryTextListener(this);
        searchView.setOnQueryTextFocusChangeListener((v, hasFocus) -> collapseSearchView());
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_search:
                MenuItemCompat.expandActionView(item);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    void collapseSearchView() {
        MenuItemCompat.collapseActionView(mSearchMenuItem);
    }

    @Override
    public boolean onQueryTextChange(String s) { return false; }

    @Override
    public boolean onMenuItemActionExpand(MenuItem item) { return true; }

    @Override
    public boolean onQueryTextSubmit(String query) {
        SearchActivity.start(this, query);
        collapseSearchView();
        return true;
    }

    @Override
    public boolean onMenuItemActionCollapse(MenuItem item) {
        showMyPodcasts();
        return true;
    }

    void showMyPodcasts() {
        mItunesSearchAdapter.clear();
        mPodcastModel.getMyPodcasts().subscribe(this::onPodcastsLoaded);
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
