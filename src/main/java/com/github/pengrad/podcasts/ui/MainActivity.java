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

import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener, MenuItemCompat.OnActionExpandListener {

    public static final String TAG = "MainActivity";

    @Inject PodcastModel mPodcastModel;

    @Bind(R.id.recycler_view) RecyclerView mRecyclerView;
    @Bind(R.id.progressBar) ProgressBar mProgressBar;
    @Bind(R.id.emptyView) View mEmptyView;

    SearchView mSearchView;
    ItunesSearchRecyclerAdapter mItunesSearchAdapter;

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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItemCompat.setOnActionExpandListener(menu.findItem(R.id.menu_search), this);
        mSearchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        mSearchView.setOnQueryTextListener(this);
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

    @Override
    public boolean onQueryTextSubmit(String query) {
        SearchActivity.start(this, query);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        return false;
    }

    @Override
    public boolean onMenuItemActionExpand(MenuItem item) {
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
//        Pair<View, String>[] sharedElements = new Pair[2];
//        sharedElements[0] = new Pair<>(view.findViewById(R.id.podcastImage), "podcastImage");
//        sharedElements[1] = new Pair<>(view.findViewById(R.id.podcastTitle), "podcastTitle");
//        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, sharedElements);
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, view.findViewById(R.id.podcastImage), "");
        PodcastChannelActivity.start(this, podcast, options.toBundle());
    }
}
