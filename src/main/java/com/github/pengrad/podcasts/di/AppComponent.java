package com.github.pengrad.podcasts.di;

import com.github.pengrad.podcasts.ui.screens.MainActivity;
import com.github.pengrad.podcasts.ui.screens.MyPodcastsFragment;
import com.github.pengrad.podcasts.ui.screens.PodcastActivity;
import com.github.pengrad.podcasts.ui.screens.PodcastSearchActivity;
import com.github.pengrad.podcasts.ui.screens.SearchActivity;
import com.github.pengrad.podcasts.ui.screens.SearchFragment;

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

    void inject(MyPodcastsFragment fragment);

    void inject(SearchFragment searchFragment);

    void inject(SearchActivity activity);

    void inject(PodcastActivity podcastActivity);

    void inject(PodcastSearchActivity podcastActivity);

}
