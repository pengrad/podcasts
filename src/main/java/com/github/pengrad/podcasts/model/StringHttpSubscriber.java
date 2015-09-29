package com.github.pengrad.podcasts.model;

import android.util.Log;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

import java.io.IOException;

import rx.Observable;
import rx.Subscriber;

/**
 * 29 September 2015
 */
public class StringHttpSubscriber implements Observable.OnSubscribe<String> {

    private final OkHttpClient okHttpClient;
    private final Request request;

    public StringHttpSubscriber(OkHttpClient okHttpClient, Request request) {
        this.okHttpClient = okHttpClient;
        this.request = request;
    }

    @Override
    public void call(Subscriber<? super String> subscriber) {
        try {
            String response = okHttpClient.newCall(request).execute().body().string();
            subscriber.onNext(response);
        } catch (IOException e) {
            Log.d("OnSubscribe", "OkHttpClient error", e);
        }
        subscriber.onCompleted();
    }
}
