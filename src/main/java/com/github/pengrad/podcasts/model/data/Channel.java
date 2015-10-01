package com.github.pengrad.podcasts.model.data;

import com.google.gson.JsonObject;

import java.util.Arrays;
import java.util.List;

/**
 * stas
 * 8/31/15
 */
public class Channel {

    public final String title;
    public final List<Episode> item;

    public Channel(String title, List<Episode> item) {
        this.title = title;
        this.item = item;
    }

    @Override
    public String toString() {
        return title + " \n " + Arrays.toString(item.toArray());
    }

    public static class Episode {
        String title;
        private JsonObject enclosure;

        public String getMediaUrl() {
            return enclosure.getAsJsonPrimitive("url").getAsString();
        }

        @Override
        public String toString() {
            return title;
        }
    }
}
