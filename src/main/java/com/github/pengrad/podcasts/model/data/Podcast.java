package com.github.pengrad.podcasts.model.data;

import java.io.Serializable;
import java.util.Date;

import co.uk.rushorm.core.RushObject;

/**
 * Stas Parshin
 * 01 October 2015
 */
public class Podcast extends RushObject implements Serializable {

    private long collectionId;
    private String collectionName;
    private String artistName;
    private String artworkUrl600;
    private String feedUrl;
    private Date releaseDate;

    private boolean isSubscribed = false;

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

    @Override
    public String toString() {
        return collectionName + artistName + artworkUrl600;
    }
}
