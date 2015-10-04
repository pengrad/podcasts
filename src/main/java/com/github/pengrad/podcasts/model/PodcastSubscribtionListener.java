package com.github.pengrad.podcasts.model;

import com.github.pengrad.podcasts.model.data.Podcast;

import javax.inject.Inject;

import co.uk.rushorm.core.RushCore;

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
        RushCore.getInstance().registerObjectWithId(podcast, podcast.getPodcastId());
        podcast.save(() -> {});
    }

    public void onUnsubscribe(Podcast podcast) {
        podcast.setSubscribed(false);
        podcast.delete(() -> {});
    }
}
