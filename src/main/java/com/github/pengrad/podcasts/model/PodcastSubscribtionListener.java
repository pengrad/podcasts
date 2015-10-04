package com.github.pengrad.podcasts.model;

import com.github.pengrad.podcasts.model.data.Podcast;

import javax.inject.Inject;

/**
 * Stas Parshin
 * 04 October 2015
 */
public class PodcastSubscribtionListener {

    @Inject
    PodcastSubscribtionListener() {
    }

    public void onSubscribe(Podcast podcast) {
        podcast.setSubscribed(true);
        podcast.save(() -> {});
    }

    public void onUnsubscribe(Podcast podcast) {
        podcast.setSubscribed(false);
        podcast.save(() -> {});

    }
}
