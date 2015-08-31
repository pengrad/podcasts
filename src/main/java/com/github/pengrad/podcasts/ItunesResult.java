package com.github.pengrad.podcasts;

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
        public final String collectionName;
        public final String artistName;
        public final String artworkUrl600;
        public final String feedUrl;

        public Podcast(String collectionName, String artistName, String artworkUrl600, String feedUrl) {
            this.collectionName = collectionName;
            this.artistName = artistName;
            this.artworkUrl600 = artworkUrl600;
            this.feedUrl = feedUrl;
        }

        @Override
        public String toString() {
            return collectionName + artistName + artworkUrl600;
        }
    }

}
