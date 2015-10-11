package com.github.pengrad.podcasts.model.data;

import com.google.gson.JsonObject;

/**
 * Stas Parshin
 * 01 October 2015
 */
public class FeedEpisode {

    public final String title;

    private final JsonObject enclosure;

    public FeedEpisode(String title, JsonObject enclosure) {
        this.title = title;
        this.enclosure = enclosure;
    }

    public String getMediaUrl() {
        try {
            return enclosure.getAsJsonPrimitive("url").getAsString();
        } catch (Exception e) {
            return "";
        }
    }

    @Override
    public String toString() {
        return title;
    }
}
