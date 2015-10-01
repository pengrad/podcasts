package com.github.pengrad.podcasts.model.data;

import java.util.Arrays;
import java.util.List;

/**
 * stas
 * 8/31/15
 */
public class FeedChannel {

    public final String title;
    public final List<FeedEpisode> item;

    public FeedChannel(String title, List<FeedEpisode> item) {
        this.title = title;
        this.item = item;
    }

    @Override
    public String toString() {
        return title + " \n " + Arrays.toString(item.toArray());
    }
}
