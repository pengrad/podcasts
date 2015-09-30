package com.github.pengrad.podcasts.di;

import com.github.pengrad.podcasts.MainActivity;
import com.github.pengrad.podcasts.PodcastChannelActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * 29 September 2015
 */

@Singleton
@Component(modules = {NetworkModule.class, ModelModule.class})
public interface AppComponent {

    void inject(MainActivity mainActivity);

    void inject(PodcastChannelActivity podcastChannelActivity);

}
