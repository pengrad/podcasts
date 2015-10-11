package com.github.pengrad.podcasts.di;

import android.content.Context;

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
    PodcastStore providePodcastStore(Context context) {
        return new PodcastStore(context);
    }

}
