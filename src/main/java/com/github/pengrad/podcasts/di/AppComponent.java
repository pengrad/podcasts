package com.github.pengrad.podcasts.di;

import com.github.pengrad.podcasts.ui.MainActivity;
import com.github.pengrad.podcasts.ui.PodcastChannelActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Stanislav Parshin
 * 29 September 2015
 */

@Singleton
@Component(modules = {NetworkModule.class, ModelModule.class})
public interface AppComponent {

    void inject(MainActivity mainActivity);

    void inject(PodcastChannelActivity podcastChannelActivity);

}
