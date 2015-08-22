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
        public final String artworkUrl100;

        public Podcast(String collectionName, String artistName, String artworkUrl100) {
            this.collectionName = collectionName;
            this.artistName = artistName;
            this.artworkUrl100 = artworkUrl100;
        }

        @Override
        public String toString() {
            return collectionName + artistName + artworkUrl100;
        }
    }

}
