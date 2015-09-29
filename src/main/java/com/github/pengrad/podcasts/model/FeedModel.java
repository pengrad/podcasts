package com.github.pengrad.podcasts.model;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

import javax.inject.Inject;

import rx.Observable;

/**
 * Stas Parshin
 * 28 September 2015
 */
public class FeedModel {

    private final OkHttpClient mOkHttpClient;

    @Inject
    public FeedModel(OkHttpClient okHttpClient) {
        mOkHttpClient = okHttpClient;
    }

    public Observable<String> getFeed(String url) {
        Request request = new Request.Builder().url(url).build();
        return Observable.create(new StringHttpSubscriber(mOkHttpClient, request));
    }

}
