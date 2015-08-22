package com.github.pengrad.podcasts;

import android.content.Context;
import android.util.Log;

import com.google.gson.reflect.TypeToken;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.future.ResponseFuture;

/**
 * stas
 * 8/21/15
 */
public class ItunesSearchAPI {

    ResponseFuture<ItunesResult> searchPodcasts(Context context, String query) {
        return Ion.with(context)
                .load("https://itunes.apple.com/search?media=podcast&term=" + query)
                .setLogging("ItunesSearchAPi", Log.DEBUG)
                .as(TypeToken.get(ItunesResult.class));
    }

}
