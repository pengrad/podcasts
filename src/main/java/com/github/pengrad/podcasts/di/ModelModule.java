package com.github.pengrad.podcasts.di;

import com.github.pengrad.podcasts.model.FeedModel;
import com.github.pengrad.podcasts.model.PodcastStore;
import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Stas Parshin
 * 01 October 2015
 */

@Module
public class ModelModule {

    @Provides
    @Singleton
    FeedModel provideFeedModel(OkHttpClient okHttpClient, Gson gson) {
        return new FeedModel(okHttpClient, gson);
    }

    @Provides
    @Singleton
    PodcastStore providePodcastStore() {
        return new PodcastStore();
    }

}
