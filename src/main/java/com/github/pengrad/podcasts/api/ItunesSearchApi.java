package com.github.pengrad.podcasts.api;

import com.github.pengrad.podcasts.ItunesResult;

import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

/**
 * Stas Parshin
 * 28 September 2015
 */
public interface ItunesSearchApi {

    @GET("search?media=podcast")
    Observable<ItunesResult> searchPodcasts(@Query("term") String query);

}
