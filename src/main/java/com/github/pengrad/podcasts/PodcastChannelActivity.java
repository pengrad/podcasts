package com.github.pengrad.podcasts;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.Gson;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.w3c.dom.Document;

import java.lang.ref.WeakReference;

import butterknife.Bind;
import butterknife.ButterKnife;

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
    ArrayAdapter<Object> mAdapter;

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
                .asDocument()
                .setCallback(new EpisodesLoadedCallback(this));
    }

    private void onEpisodesLoaded(Exception e, Document document) {
        if (document != null) {
            String json = XmlConverter.toJson(document).getAsJsonObject("rss").getAsJsonObject("channel").toString();
            Channel channel = new Gson().fromJson(json, Channel.class);
            mAdapter.addAll(channel.item);
        }
    }

    public static class EpisodesLoadedCallback implements FutureCallback<Document> {

        private WeakReference<PodcastChannelActivity> activityRef;

        public EpisodesLoadedCallback(PodcastChannelActivity activity) {
            this.activityRef = new WeakReference<>(activity);
        }

        @Override
        public void onCompleted(Exception e, Document result) {
            PodcastChannelActivity activity = activityRef.get();
            if (activity != null) {
                activity.onEpisodesLoaded(e, result);
            }
        }
    }
}
