package com.github.pengrad.podcasts.di;

import com.github.pengrad.podcasts.model.PodcastStore;

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
    PodcastStore providePodcastStore() {
        return new PodcastStore();
    }

}
