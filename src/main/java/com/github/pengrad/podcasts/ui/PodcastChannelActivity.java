package com.github.pengrad.podcasts.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.github.pengrad.podcasts.MyApp;
import com.github.pengrad.podcasts.R;
import com.github.pengrad.podcasts.model.FeedModel;
import com.github.pengrad.podcasts.model.data.FeedChannel;
import com.github.pengrad.podcasts.model.data.FeedEpisode;

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
public class PodcastChannelActivity extends AppCompatActivity {

    private static final String TAG = "PodcastActivity";
    private static final String KEY_FEEDURL = "FEEDURL";

    public static void start(Context context, String feedUrl) {
        Intent starter = new Intent(context, PodcastChannelActivity.class);
        starter.putExtra(KEY_FEEDURL, feedUrl);
        context.startActivity(starter);
    }

    @Bind(R.id.listview) ListView mListView;
    ArrayAdapter<FeedEpisode> mAdapter;

    @Inject FeedModel mFeedModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel);

        MyApp.get(this).getAppComponent().inject(this);
        ButterKnife.bind(this);

        mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        mListView.setAdapter(mAdapter);

        String feedUrl = getIntent().getStringExtra(KEY_FEEDURL);

        mFeedModel.getFeed(feedUrl)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onChannelLoaded);
    }

    @OnItemClick(R.id.listview)
    void onItemClick(int position) {
        String mediaUrl = mAdapter.getItem(position).getMediaUrl();
        Intent intent = new Intent();
        intent.setAction(android.content.Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse(mediaUrl), "audio/mp3");
        startActivity(intent);
    }

    private void onChannelLoaded(FeedChannel channel) {
        mAdapter.addAll(channel.item);
    }
}
