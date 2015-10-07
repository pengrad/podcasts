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

    private String desc = "";
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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
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
}
