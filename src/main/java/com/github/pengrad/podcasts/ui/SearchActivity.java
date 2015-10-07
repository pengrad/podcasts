package com.github.pengrad.podcasts.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;

import com.github.pengrad.podcasts.R;

public class SearchActivity extends AppCompatActivity implements SearchView.OnQueryTextListener, MenuItemCompat.OnActionExpandListener {

    public static final String ACTION = "SEARCH";
    public static final String EXTRA_QUERY = "query";

    public static void start(Context context, String query) {
        Intent intent = new Intent(context, SearchActivity.class);
        intent.setAction(ACTION);
        intent.putExtra(EXTRA_QUERY, query);
        context.startActivity(intent);
    }

    SearchView mSearchView;
    String mQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        mQuery = getIntent().getStringExtra(EXTRA_QUERY);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem menuItem = menu.findItem(R.id.menu_search);
        MenuItemCompat.expandActionView(menuItem);
        MenuItemCompat.setOnActionExpandListener(menuItem, this);
        mSearchView = (SearchView) menuItem.getActionView();
        mSearchView.setQuery(mQuery, false);
        mSearchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onMenuItemActionExpand(MenuItem item) {
        return true;
    }

    @Override
    public boolean onMenuItemActionCollapse(MenuItem item) {
        finish();
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}
