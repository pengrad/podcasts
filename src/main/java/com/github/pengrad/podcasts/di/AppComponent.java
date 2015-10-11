package com.github.pengrad.podcasts.di;

import com.github.pengrad.podcasts.ui.MainActivity;
import com.github.pengrad.podcasts.ui.PodcastActivity;
import com.github.pengrad.podcasts.ui.PodcastSearchActivity;
import com.github.pengrad.podcasts.ui.SearchActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Stas Parshin
 * 29 September 2015
 */

@Singleton
@Component(modules = {AppModule.class, NetworkModule.class, ModelModule.class})
public interface AppComponent {

    void inject(MainActivity mainActivity);

    void inject(SearchActivity activity);

    void inject(PodcastActivity podcastActivity);

    void inject(PodcastSearchActivity podcastActivity);

}
