package com.github.pengrad.podcasts;

import java.util.Date;

/**
 * stas
 * 8/22/15
 */
public class ItunesResult {

    public final int resultCount;
    public final Podcast[] results;

    public ItunesResult(int resultCount, Podcast[] results) {
        this.resultCount = resultCount;
        this.results = results;
    }

    static class Podcast {
        public long collectionId;
        public String collectionName;
        public String artistName;
        public String artworkUrl600;
        public String feedUrl;
        public Date releaseDate;

        @Override
        public String toString() {
            return collectionName + artistName + artworkUrl600;
        }
    }

}
