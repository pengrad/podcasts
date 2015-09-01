package com.github.pengrad.podcasts;

import com.google.gson.JsonObject;

import java.util.Arrays;
import java.util.List;

/**
 * stas
 * 8/31/15
 */
public class Channel {

    String title;
    List<Episode> item;

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
