package com.github.pengrad.podcasts;

import android.content.Context;
import android.util.Log;

import com.google.gson.JsonObject;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.future.ResponseFuture;

/**
 * stas
 * 8/21/15
 */
public class ItunesSearchAPI {

    ResponseFuture<JsonObject> searchPodcasts(Context context, String query) {
        return Ion.with(context)
                .load("https://itunes.apple.com/search?media=podcast&term=" + query)
                .setLogging("ItunesSearchAPi", Log.DEBUG)
//                .as
                .asJsonObject();
    }

}
