package com.github.pengrad.podcasts.ui;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ProgressBar;

import com.github.pengrad.podcasts.MyApp;
import com.github.pengrad.podcasts.R;
import com.github.pengrad.podcasts.model.PodcastModel;
import com.github.pengrad.podcasts.model.data.Podcast;

import java.util.Collection;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    public static final String TAG = "MainActivity";

    @Inject PodcastModel mPodcastModel;

    @Bind(R.id.recycler_view) RecyclerView mRecyclerView;
    @Bind(R.id.progressBar) ProgressBar mProgressBar;
    @Bind(R.id.emptyView) View mEmptyView;
    @Bind(R.id.fab) FloatingActionButton mFab;

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
        SearchActivity.start(this, "bob");
    }

    @Override
    protected void onResume() {
        super.onResume();
        showMyPodcasts();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        mSearchMenuItem = menu.findItem(R.id.menu_search);
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
    public boolean onQueryTextSubmit(String query) {
        SearchActivity.start(this, query);
        collapseSearchView();
        return true;
    }

    void showMyPodcasts() {
        mPodcastModel.getMyPodcasts().subscribe(this::onPodcastsLoaded);
    }

    void onPodcastsLoaded(Collection<Podcast> podcasts) {
        mProgressBar.setVisibility(View.GONE);
        mItunesSearchAdapter.setData(podcasts);
    }

    void onItemClicked(Podcast podcast, View view, int adapterPosition) {
        View viewImage = view.findViewById(R.id.podcastImage);
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, viewImage, "");
        PodcastActivity.start(this, podcast, options.toBundle());
    }

    @OnClick(R.id.fab)
    void onFabClick() {
        rotateAnimation(mFab);
    }

    private void rotateAnimation(View view) {
        RotateAnimation animation = new RotateAnimation(0, 360, view.getWidth() / 2, view.getHeight() / 2);
        animation.setDuration(600); // duration in ms
        animation.setRepeatCount(Animation.INFINITE);
        animation.setFillAfter(true);
        animation.setInterpolator(new LinearInterpolator());
        view.startAnimation(animation);

        new Thread() {
            @Override
            public void run() {
                SystemClock.sleep(3000);
                runOnUiThread(animation::cancel);
            }
        }.start();
    }
}
