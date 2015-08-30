package com.github.pengrad.podcasts;

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

        @Override
        public String toString() {
            return title;
        }
    }
}
