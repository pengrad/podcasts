package com.github.pengrad.podcasts;

import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnItemClick;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener, MenuItemCompat.OnActionExpandListener {

    public static final String TAG = "MainActivity";

    @Bind(R.id.listview) ListView mListview;
    @Bind(R.id.progressBar) ProgressBar mProgressBar;

    private SearchView mSearchView;
    private ArrayAdapter<ItunesResult.Podcast> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        mListview.setAdapter(mAdapter);
        mListview.setEmptyView(findViewById(R.id.emptyView));
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
        mAdapter.clear();
        mProgressBar.setVisibility(View.VISIBLE);
        new ItunesSearchAPI()
                .searchPodcasts(getApplicationContext(), query)
                .setCallback((ex, result) -> {
                    mProgressBar.setVisibility(View.GONE);
                    if (result != null) {
                        mAdapter.addAll(result.results);
                    }
                });
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
        Log.d(TAG, "onMenuItemActionCollapse() returned: " + true);
        mAdapter.clear();
        return true;
    }

    @OnItemClick(R.id.listview)
    void onItemClicked(int position) {
        PodcastChannelActivity.start(this, mAdapter.getItem(position).feedUrl);
    }
}
