package com.github.pengrad.podcasts.model;

import com.github.pengrad.podcasts.model.data.Podcast;

import javax.inject.Inject;

/**
 * Stas Parshin
 * 04 October 2015
 */
public class PodcastSubscribtionListener {

    private final PodcastStore mPodcastStore;

    @Inject
    public PodcastSubscribtionListener(PodcastStore podcastStore) {
        mPodcastStore = podcastStore;
    }

    public void onSubscribe(Podcast podcast) {
        podcast.setSubscribed(true);
        mPodcastStore.savePodcast(podcast);
    }

    public void onUnsubscribe(Podcast podcast) {
        podcast.setSubscribed(false);
        mPodcastStore.deletePodcast(podcast);
    }
}
