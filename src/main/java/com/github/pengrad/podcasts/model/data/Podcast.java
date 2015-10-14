package com.github.pengrad.podcasts.model.data;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import co.uk.rushorm.core.RushObject;
import co.uk.rushorm.core.annotations.RushList;

/**
 * Stas Parshin
 * 01 October 2015
 */
public class Podcast extends RushObject implements Serializable, Comparable<Podcast> {

    private long collectionId;
    private String collectionName;
    private String artistName;
    private String artworkUrl600;
    private String feedUrl;
    private Date releaseDate;

    private boolean isSubscribed = false;
    private long subscriptionDate = 0;

    @RushList(classType = PodcastEpisode.class)
    private List<PodcastEpisode> episodes = new ArrayList<>();

    public Podcast() {
        //need for RushOrm
    }

    public String getPodcastId() {
        return String.valueOf(collectionId);
    }

    public String getTitle() {
        return collectionName;
    }

    public String getArtistName() {
        return artistName;
    }

    public String getImageUrl() {
        return artworkUrl600;
    }

    public String getFeedUrl() {
        return feedUrl;
    }

    public boolean isSubscribed() {
        return isSubscribed;
    }

    public void setSubscribed(boolean isSubscribed) {
        this.isSubscribed = isSubscribed;
    }

    public void setSubscriptionDate(long subscriptionDate) {
        this.subscriptionDate = subscriptionDate;
    }

    public List<PodcastEpisode> getEpisodes() {
        return episodes;
    }

    public void setEpisodes(List<PodcastEpisode> episodes) {
        this.episodes = episodes;
    }

    public boolean isNewEpisode(PodcastEpisode episode) {
        int index = getEpisodes().indexOf(episode);
        if (index >= 0) {
            return getEpisodes().get(index).isNew();
        }
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Podcast podcast = (Podcast) o;

        return collectionId == podcast.collectionId;
    }

    @Override
    public int hashCode() {
        return (int) (collectionId ^ (collectionId >>> 32));
    }

    @Override
    public String toString() {
        return collectionName + artistName + artworkUrl600;
    }

    @Override
    public int compareTo(@NonNull Podcast another) {
        // newest first
        //TODO: to separate Comparator
        return another.subscriptionDate > this.subscriptionDate ? 1 : -1;
    }
}
