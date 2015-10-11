package com.github.pengrad.podcasts.di;

import android.content.Context;

import com.github.pengrad.podcasts.MyApp;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Stas Parshin
 * 11 October 2015
 */
@Module
public class AppModule {

    private MyApp mApp;

    public AppModule(MyApp app) {
        mApp = app;
    }

    @Provides
    @Singleton
    Context provideApplicationContext() {
        return mApp;
    }
}
