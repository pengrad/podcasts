package com.github.pengrad.podcasts.ui;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.pengrad.podcasts.MyApp;
import com.github.pengrad.podcasts.R;
import com.github.pengrad.podcasts.model.PodcastModel;
import com.github.pengrad.podcasts.model.data.FeedChannel;
import com.github.pengrad.podcasts.model.data.FeedEpisode;
import com.github.pengrad.podcasts.model.data.Podcast;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * stas
 * 8/27/15
 */
public class PodcastSearchActivity extends AppCompatActivity {

    private static final String TAG = "PodcastSearchActivity";
    private static final String EXTRA_PODCAST = "extra_podcast";

    public static void start(Activity context, Podcast podcast, Bundle options) {
        Intent starter = new Intent(context, PodcastSearchActivity.class);
        starter.putExtra(EXTRA_PODCAST, podcast);
        ActivityCompat.startActivity(context, starter, options);
    }

    @Inject PodcastModel mPodcastModel;

    @Bind(R.id.listview) ListView mListView;

    @Bind(R.id.podcastImage) ImageView mPodcastImage;
    @Bind(R.id.podcastArtist) TextView mPodcastArtist;
    @Bind(R.id.buttonSubscribe) TextView mButtonSubscribe;

    Podcast mPodcast;
    ArrayAdapter<FeedEpisode> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_podcast_search);

        MyApp.get(this).getAppComponent().inject(this);
        ButterKnife.bind(this);

        mPodcast = getPodcast();

        initList();
        initPodcastView(mPodcast);
        initTransition();

        getFeedData(mPodcast);
    }

    Podcast getPodcast() {
        Podcast podcast = (Podcast) getIntent().getSerializableExtra(EXTRA_PODCAST);
        return mPodcastModel.syncPodcast(podcast);
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
        Glide.with(this).load(podcast.getImageUrl()).into(mPodcastImage);
        setTitle(podcast.getTitle());
        mPodcastArtist.setText(podcast.getArtistName());
        initSubscribeButton(podcast);
    }

    void initSubscribeButton(Podcast podcast) {
        if (podcast.isSubscribed()) {
            mButtonSubscribe.setText(R.string.button_unsubscribe);
            mButtonSubscribe.setOnClickListener(v -> {
                mPodcastModel.onUnsubscribe(podcast);
                initSubscribeButton(podcast);
            });
        } else {
            mButtonSubscribe.setText(R.string.button_subscribe);
            mButtonSubscribe.setOnClickListener(v -> {
                mPodcastModel.onSubscribe(podcast);
                initSubscribeButton(podcast);
            });
        }
    }

    void getFeedData(Podcast podcast) {
        mPodcastModel.getFeed(podcast.getFeedUrl())
                .onErrorReturn(throwable -> new FeedChannel("title", "desc", new ArrayList<>()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onChannelLoaded);
    }

    private void onChannelLoaded(FeedChannel channel) {
        mPodcast.setDesc(channel.desc);
//        mPodcastDesc.setText(mPodcast.getDesc());
        mAdapter.addAll(channel.item);
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
