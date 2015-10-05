package com.github.pengrad.podcasts.model;

import com.github.pengrad.podcasts.model.data.ItunesSearchResult;
import com.github.pengrad.podcasts.model.data.Podcast;
import com.github.pengrad.podcasts.utils.StringHttpSubscriber;
import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;

/**
 * Stas Parshin
 * 28 September 2015
 */
public class PodcastModel {

    private final OkHttpClient mOkHttpClient;
    private final Gson mGson;
    private final PodcastStore mPodcastStore;

    @Inject
    public PodcastModel(OkHttpClient okHttpClient, Gson gson, PodcastStore podcastStore) {
        mPodcastStore = podcastStore;
        mOkHttpClient = okHttpClient;
        mGson = gson;
    }

    public Observable<List<Podcast>> searchPodcast(String query) {
        String url = "https://itunes.apple.com/search?media=podcast&term=" + query;
        Request request = new Request.Builder().url(url).build();

        return Observable
                .create(new StringHttpSubscriber(mOkHttpClient, request))
                .map(str -> mGson.fromJson(str, ItunesSearchResult.class))
                .map(itunesSearchResult -> itunesSearchResult.results)
                .flatMapIterable(podcasts -> podcasts)
                .map(mPodcastStore::syncPodcast)
                .toList();
    }

    public Observable<Collection<Podcast>> getMyPodcasts() {
        return Observable.defer(() -> Observable.just(mPodcastStore.getPodcasts()));
    }
}
