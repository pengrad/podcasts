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
import android.view.animation.Animation;
import android.widget.ProgressBar;

import com.github.pengrad.podcasts.MyApp;
import com.github.pengrad.podcasts.R;
import com.github.pengrad.podcasts.model.PodcastModel;
import com.github.pengrad.podcasts.model.data.Podcast;
import com.github.pengrad.podcasts.ui.adapters.ItunesSearchRecyclerAdapter;

import java.util.Collection;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Stas Parshin
 * 12 November 2015
 */
public class MyPodcastsFragment extends Fragment implements SearchView.OnQueryTextListener {

    @Inject PodcastModel mPodcastModel;

    @Bind(R.id.recycler_view) RecyclerView mRecyclerView;
    @Bind(R.id.progressBar) ProgressBar mProgressBar;
    @Bind(R.id.emptyView) View mEmptyView;
    @Bind(R.id.rotatingFab) View mRotatingFab;

    ItunesSearchRecyclerAdapter mItunesSearchAdapter;

    MenuItem mSearchMenuItem;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_podcasts, container, false);

        Activity activity = getActivity();
        MyApp.getInjector(activity).inject(this);
        ButterKnife.bind(this, view);

        mItunesSearchAdapter = new ItunesSearchRecyclerAdapter(this::onItemClicked);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(activity));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mItunesSearchAdapter);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_main, menu);
        mSearchMenuItem = menu.findItem(R.id.menu_search);
        SearchView searchView = (SearchView) mSearchMenuItem.getActionView();
        searchView.setOnQueryTextListener(this);
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
    public void onResume() {
        super.onResume();
        showMyPodcasts();
    }

    void showMyPodcasts() {
        mPodcastModel.getMyPodcasts().subscribe(this::onPodcastsLoaded);
    }

    void onPodcastsLoaded(Collection<Podcast> podcasts) {
        mProgressBar.setVisibility(View.GONE);
        mItunesSearchAdapter.setData(podcasts);
    }

    void onItemClicked(Podcast podcast, View view, int adapterPosition) {
        Activity activity = getActivity();
        View viewImage = view.findViewById(R.id.podcastImage);
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, viewImage, "");
        PodcastActivity.start(activity, podcast, options.toBundle());
    }

    @OnClick(R.id.fab)
    void onFabClick() {
        rotateAnimation(mRotatingFab);
    }

    private void rotateAnimation(View view) {

        Animation animation = android.view.animation.AnimationUtils.loadAnimation(getActivity(), R.anim.rotate);
        view.startAnimation(animation);


        view.postDelayed(() -> {
            view.clearAnimation();
        }, 2100);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        ((MainActivity) getActivity()).navigateToSearchFragment(query);
//        collapseSearchView();
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}
