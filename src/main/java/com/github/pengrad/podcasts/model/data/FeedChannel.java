package com.github.pengrad.podcasts.model.data;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;
import java.util.List;

/**
 * stas
 * 8/31/15
 */
public class FeedChannel {

    public final String title;
    public final List<FeedEpisode> item;
    @SerializedName("itunes:summary") public final String desc;

    public FeedChannel(String title, String desc, List<FeedEpisode> item) {
        this.title = title;
        this.desc = desc;
        this.item = item;
    }

    @Override
    public String toString() {
        return title + " \n " + Arrays.toString(item.toArray());
    }
}
