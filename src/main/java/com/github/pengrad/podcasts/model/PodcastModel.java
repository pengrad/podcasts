package com.github.pengrad.podcasts.model;

import com.github.pengrad.podcasts.model.data.FeedChannel;
import com.github.pengrad.podcasts.model.data.ItunesSearchResult;
import com.github.pengrad.podcasts.model.data.Podcast;
import com.github.pengrad.podcasts.model.data.PodcastEpisode;
import com.github.pengrad.podcasts.utils.StringHttpSubscriber;
import com.github.pengrad.podcasts.utils.XmlConverter;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

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
                .map(mPodcastStore::loadPodcast)
                .toList();
    }

    public Observable<List<Podcast>> getMyPodcasts() {
        return Observable
                .defer(() -> Observable.just(mPodcastStore.getPodcasts()))
                .flatMapIterable(podcasts -> podcasts)
                .toSortedList(Podcast::compareTo);
    }

    public Podcast loadPodcast(Podcast podcast) {
        return mPodcastStore.loadPodcast(podcast);
    }

    public Observable<FeedChannel> getFeed(Podcast podcast) {
        Request request = new Request.Builder().url(podcast.getFeedUrl()).build();
        return Observable
                .create(new StringHttpSubscriber(mOkHttpClient, request))
                .map(this::xmlToChannel);
    }

    private FeedChannel xmlToChannel(String xml) {
        JsonObject json = XmlConverter.toJson(xml).getAsJsonObject("rss").getAsJsonObject("channel");
        return mGson.fromJson(json, FeedChannel.class);
    }

    public void onSubscribe(Podcast podcast) {
        podcast.setSubscribed(true);
        podcast.setSubscriptionDate(System.currentTimeMillis());
        mPodcastStore.savePodcast(podcast);
    }

    public void onUnsubscribe(Podcast podcast) {
        podcast.setSubscribed(false);
        mPodcastStore.deletePodcast(podcast);
    }

    public Observable<Podcast> refreshPodcast(Podcast podcast) {
        return getFeed(podcast)
                .flatMapIterable(feedChannel -> feedChannel.item)
                .map(PodcastEpisode::new)
                .toList()
                .map(podcastEpisodes -> {
                    podcast.setEpisodes(podcastEpisodes);
                    return podcast;
                });
    }
}
