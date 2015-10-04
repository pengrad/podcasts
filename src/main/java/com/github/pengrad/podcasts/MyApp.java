package com.github.pengrad.podcasts;

import android.app.Application;
import android.content.Context;

import com.github.pengrad.podcasts.di.AppComponent;
import com.github.pengrad.podcasts.di.DaggerAppComponent;
import com.squareup.leakcanary.LeakCanary;

import co.uk.rushorm.android.AndroidInitializeConfig;
import co.uk.rushorm.core.RushCore;

/**
 * stas
 * 8/29/15
 */
public class MyApp extends Application {

    public static MyApp get(Context context) {
        return (MyApp) context.getApplicationContext();
    }

    private AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        mAppComponent = DaggerAppComponent.create();
        LeakCanary.install(this);
        RushCore.initialize(new AndroidInitializeConfig(this));
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }
}
