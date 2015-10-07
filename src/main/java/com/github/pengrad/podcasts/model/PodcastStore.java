package com.github.pengrad.podcasts.model;

import com.github.pengrad.podcasts.model.data.Podcast;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import co.uk.rushorm.core.RushCore;
import co.uk.rushorm.core.RushSearch;

/**
 * Stas Parshin
 * 04 October 2015
 */
public class PodcastStore {

    private HashMap<String, Podcast> mPodcasts = new HashMap<>(0);

    public PodcastStore() {
        List<Podcast> list = new RushSearch().find(Podcast.class);
        mPodcasts = new HashMap<>(list.size());
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

    public Podcast syncPodcast(Podcast podcast) {
        Podcast savedPodcast = mPodcasts.get(podcast.getPodcastId());
        return savedPodcast == null ? podcast : savedPodcast;
    }
}
