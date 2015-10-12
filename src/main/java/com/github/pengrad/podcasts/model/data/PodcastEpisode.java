package com.github.pengrad.podcasts.model.data;

import java.io.Serializable;

import co.uk.rushorm.core.RushObject;

/**
 * Stas Parshin
 * 08 October 2015
 */
public class PodcastEpisode extends RushObject implements Serializable {

    private String title;
    private String mediaUrl;
    private boolean isNew;

    public PodcastEpisode() {
        // for RushOrm
    }

    public PodcastEpisode(FeedEpisode episode) {
        this.title = episode.title;
        this.mediaUrl = episode.getMediaUrl();
        this.isNew = true;
    }

    public String getTitle() {
        return title;
    }

    public String getMediaUrl() {
        return mediaUrl;
    }

    public boolean isNew() {
        return isNew;
    }

    public void setIsNew(boolean isNew) {
        this.isNew = isNew;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PodcastEpisode that = (PodcastEpisode) o;
        return title.equals(that.title) && mediaUrl.equals(that.mediaUrl);
    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (mediaUrl != null ? mediaUrl.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return isNew ? "[NEW] " + title : title;
    }
}
