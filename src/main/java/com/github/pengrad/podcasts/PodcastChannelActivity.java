package com.github.pengrad.podcasts;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.Gson;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.lang.ref.WeakReference;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnItemClick;

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
    ArrayAdapter<Channel.Episode> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel);
        ButterKnife.bind(this);

        mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        mListView.setAdapter(mAdapter);

        String feedUrl = getIntent().getStringExtra(KEY_FEEDURL);

        Ion.with(getApplicationContext())
                .load(feedUrl)
                .asString()
                .setCallback(new EpisodesLoadedCallback(this));
    }

    @OnItemClick(R.id.listview)
    void onItemClick(int position) {
        String mediaUrl = mAdapter.getItem(position).getMediaUrl();
        Intent intent = new Intent();
        intent.setAction(android.content.Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse(mediaUrl), "audio/mp3");
        startActivity(intent);
    }

    private void onEpisodesLoaded(Exception e, String xml) {
        if (xml != null) {
            String json = XmlConverter.toJson(xml).getAsJsonObject("rss").getAsJsonObject("channel").toString();
            Channel channel = new Gson().fromJson(json, Channel.class);
            mAdapter.addAll(channel.item);
        }
    }

    public static class EpisodesLoadedCallback implements FutureCallback<String> {

        private WeakReference<PodcastChannelActivity> activityRef;

        public EpisodesLoadedCallback(PodcastChannelActivity activity) {
            this.activityRef = new WeakReference<>(activity);
        }

        @Override
        public void onCompleted(Exception e, String result) {
            PodcastChannelActivity activity = activityRef.get();
            if (activity != null) {
                activity.onEpisodesLoaded(e, result);
            }
        }
    }
}
