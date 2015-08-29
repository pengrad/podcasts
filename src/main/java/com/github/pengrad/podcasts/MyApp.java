package com.github.pengrad.podcasts;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

/**
 * stas
 * 8/29/15
 */
public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        LeakCanary.install(this);
    }
}
