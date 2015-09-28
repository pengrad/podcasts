package com.github.pengrad.podcasts;

import android.util.Log;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

import java.io.IOException;

import rx.Observable;

/**
 * Stas Parshin
 * 28 September 2015
 */
public class FeedModel {

    protected OkHttpClient mOkHttpClient;

    public FeedModel() {
        mOkHttpClient = new OkHttpClient();
    }

    public Observable<String> getFeed(String url) {
        Request request = new Request.Builder().url(url).build();
        return Observable.create(subscriber -> {
            try {
                String response = mOkHttpClient.newCall(request).execute().body().string();
                subscriber.onNext(response);
            } catch (IOException e) {
                Log.d("FeedModel", "OkHttpClient error", e);
            }
            subscriber.onCompleted();
        });
    }

}
