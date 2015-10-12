package com.github.pengrad.podcasts.model;

import com.github.pengrad.podcasts.model.data.Podcast;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import co.uk.rushorm.core.RushCore;
import co.uk.rushorm.core.RushSearch;

/**
 * Stas Parshin
 * 04 October 2015
 */
public class PodcastStore {

    private Map<String, Podcast> mPodcasts;

    public PodcastStore() {
        mPodcasts = Collections.synchronizedMap(new HashMap<>());
        List<Podcast> list = new RushSearch().find(Podcast.class);
        for (Podcast podcast : list) {
            mPodcasts.put(podcast.getPodcastId(), podcast);
        }
    }

    public Collection<Podcast> getPodcasts() {
        return mPodcasts.values();
    }

    private void register(Podcast podcast) {
        if (podcast.getId() == null) {
            RushCore.getInstance().registerObjectWithId(podcast, podcast.getPodcastId());
        }
    }

    public void savePodcast(Podcast podcast) {
        register(podcast);
        mPodcasts.put(podcast.getPodcastId(), podcast);
        podcast.save(() -> {});
    }

    public void deletePodcast(Podcast podcast) {
        register(podcast);
        mPodcasts.remove(podcast.getPodcastId());
        podcast.delete(() -> {});
    }

    public Podcast loadPodcast(Podcast podcast) {
        Podcast savedPodcast = mPodcasts.get(podcast.getPodcastId());
        return savedPodcast != null ? savedPodcast : podcast;
    }
}
