package com.github.pengrad.podcasts.model.data;

import java.util.List;

/**
 * stas
 * 8/22/15
 */
public class ItunesSearchResult {

    public final int resultCount;
    public final List<Podcast> results;

    public ItunesSearchResult(int resultCount, List<Podcast> results) {
        this.resultCount = resultCount;
        this.results = results;
    }
}
