package com.github.pengrad.podcasts.model.data;

/**
 * stas
 * 8/22/15
 */
public class ItunesSearchResult {

    public final int resultCount;
    public final Podcast[] results;

    public ItunesSearchResult(int resultCount, Podcast[] results) {
        this.resultCount = resultCount;
        this.results = results;
    }

}
