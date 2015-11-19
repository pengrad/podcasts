package com.github.pengrad.podcasts.ui.screens;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;

import com.github.pengrad.podcasts.R;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    public static final String TAG = "MainActivity";

    MenuItem mSearchMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, new MyPodcastsFragment(), "SEARCH_FRAGMENT");
        transaction.commit();
    }

//    @Override
    public boolean onCreateOptionsMenu2(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        mSearchMenuItem = menu.findItem(R.id.menu_search);
        SearchView searchView = (SearchView) mSearchMenuItem.getActionView();
        searchView.setOnQueryTextListener(this);
//        searchView.setOnQueryTextFocusChangeListener((v, hasFocus) -> collapseSearchView());
        return true;
    }

//    @Override
    public boolean onOptionsItemSelected2(MenuItem item) {
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
        navigateToSearchFragment(query);
//        collapseSearchView();
        return true;
    }

    public void navigateToSearchFragment(String query) {

        Fragment fragment = SearchFragment.create(query);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        transaction.setCustomAnimations(
//                R.animator.slide_in_from_right, R.animator.slide_out_to_left,
//                R.animator.slide_in_from_left, R.animator.slide_out_to_right);
        transaction.replace(R.id.container, fragment, "SEARCH_FRAGMENT");
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
