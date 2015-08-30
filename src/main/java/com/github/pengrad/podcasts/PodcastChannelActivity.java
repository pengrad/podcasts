package com.github.pengrad.podcasts;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.Gson;
import com.koushikdutta.ion.Ion;

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

        Ion.with(this)
                .load(feedUrl)
                .asDocument()
                .setCallback((e, result) -> {
                    String json = XmlConverter.toJson(result).getAsJsonObject("rss").getAsJsonObject("channel").toString();
                    Channel channel = new Gson().fromJson(json, Channel.class);
                    mAdapter.addAll(channel.item);
                });
    }
}
