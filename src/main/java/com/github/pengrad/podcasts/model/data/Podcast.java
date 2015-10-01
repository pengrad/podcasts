package com.github.pengrad.podcasts.model.data;

import java.util.Date;

/**
 * Stas Parshin
 * 01 October 2015
 */
public class Podcast {

    public final long collectionId;
    public final String collectionName;
    public final String artistName;
    public final String artworkUrl600;
    public final String feedUrl;
    public final Date releaseDate;

    public Podcast(long collectionId, String collectionName, String artistName, String artworkUrl600, String feedUrl, Date releaseDate) {
        this.collectionId = collectionId;
        this.collectionName = collectionName;
        this.artistName = artistName;
        this.artworkUrl600 = artworkUrl600;
        this.feedUrl = feedUrl;
        this.releaseDate = releaseDate;
    }

    @Override
    public String toString() {
        return collectionName + artistName + artworkUrl600;
    }
}
