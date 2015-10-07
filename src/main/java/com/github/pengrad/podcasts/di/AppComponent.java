package com.github.pengrad.podcasts.di;

import com.github.pengrad.podcasts.ui.MainActivity;
import com.github.pengrad.podcasts.ui.PodcastChannelActivity;
import com.github.pengrad.podcasts.ui.SearchActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Stas Parshin
 * 29 September 2015
 */

@Singleton
@Component(modules = {NetworkModule.class, ModelModule.class})
public interface AppComponent {

    void inject(MainActivity mainActivity);

    void inject(SearchActivity activity);

    void inject(PodcastChannelActivity podcastChannelActivity);

}
