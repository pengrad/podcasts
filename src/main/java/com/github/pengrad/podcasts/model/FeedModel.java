package com.github.pengrad.podcasts.model;

import com.github.pengrad.podcasts.model.data.Channel;
import com.github.pengrad.podcasts.utils.StringHttpSubscriber;
import com.github.pengrad.podcasts.utils.XmlConverter;
import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

import java.util.List;

import rx.Observable;

/**
 * Stas Parshin
 * 28 September 2015
 */
public class FeedModel {

    private final OkHttpClient mOkHttpClient;
    private final Gson mGson;

    public FeedModel(OkHttpClient okHttpClient, Gson gson) {
        mOkHttpClient = okHttpClient;
        mGson = gson;
    }

    public Observable<List<Channel.Episode>> getFeed(String url) {
        Request request = new Request.Builder().url(url).build();
        return Observable
                .create(new StringHttpSubscriber(mOkHttpClient, request))
                .map(xml -> xmlToChannel(xml).item);
    }

    private Channel xmlToChannel(String xml) {
        String json = XmlConverter.toJson(xml).getAsJsonObject("rss").getAsJsonObject("channel").toString();
        return mGson.fromJson(json, Channel.class);
    }

}
