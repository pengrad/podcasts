package com.github.pengrad.podcasts.ui;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.pengrad.podcasts.MyApp;
import com.github.pengrad.podcasts.R;
import com.github.pengrad.podcasts.model.PodcastModel;
import com.github.pengrad.podcasts.model.data.FeedEpisode;
import com.github.pengrad.podcasts.model.data.Podcast;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnItemClick;

/**
 * stas
 * 8/27/15
 */
public class PodcastActivity extends AppCompatActivity {

    private static final String TAG = "PodcastActivity";
    private static final String EXTRA_PODCAST = "extra_podcast";

    public static void start(Activity context, Podcast podcast, Bundle options) {
        Intent starter = new Intent(context, PodcastActivity.class);
        starter.putExtra(EXTRA_PODCAST, podcast);
        ActivityCompat.startActivity(context, starter, options);
    }

    @Inject PodcastModel mPodcastModel;

    @Bind(R.id.listview) ListView mListView;

    @Bind(R.id.podcastImage) ImageView mPodcastImage;
    @Bind(R.id.podcastArtist) TextView mPodcastArtist;

    Podcast mPodcast;
    ArrayAdapter<FeedEpisode> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_podcast);

        MyApp.get(this).getAppComponent().inject(this);
        ButterKnife.bind(this);

        mPodcast = getPodcast();

        initToolbar();
        initTransition();
        initPodcastView(mPodcast);
        initList();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    Podcast getPodcast() {
        Podcast podcast = (Podcast) getIntent().getSerializableExtra(EXTRA_PODCAST);
        return mPodcastModel.syncPodcast(podcast);
    }

    void initToolbar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    void initTransition() {
        ViewCompat.setTransitionName(mPodcastImage, "");
        FadeTransition.create(getWindow());
    }

    void initList() {
        mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        mListView.setAdapter(mAdapter);
    }

    void initPodcastView(Podcast podcast) {
        setTitle(podcast.getTitle());
        mPodcastArtist.setText(podcast.getArtistName());
        Glide.with(this).load(podcast.getImageUrl()).into(mPodcastImage);
    }

    @OnItemClick(R.id.listview)
    void onItemClick(int position) {
        String mediaUrl = mAdapter.getItem(position).getMediaUrl();
        Intent intent = new Intent();
        intent.setAction(android.content.Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse(mediaUrl), "audio/mp3");
        startActivity(intent);
    }
}
