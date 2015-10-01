package com.github.pengrad.podcasts.model;

import com.github.pengrad.podcasts.utils.StringHttpSubscriber;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

import rx.Observable;

/**
 * Stas Parshin
 * 28 September 2015
 */
public class FeedModel {

    private final OkHttpClient mOkHttpClient;

    public FeedModel(OkHttpClient okHttpClient) {
        mOkHttpClient = okHttpClient;
    }

    public Observable<String> getFeed(String url) {
        Request request = new Request.Builder().url(url).build();
        return Observable.create(new StringHttpSubscriber(mOkHttpClient, request));
    }

}
