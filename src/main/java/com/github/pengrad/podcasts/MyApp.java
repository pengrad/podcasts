package com.github.pengrad.podcasts;

import android.app.Application;
import android.content.Context;

import com.github.pengrad.podcasts.di.AppComponent;
import com.github.pengrad.podcasts.di.AppModule;
import com.github.pengrad.podcasts.di.DaggerAppComponent;
import com.squareup.leakcanary.LeakCanary;

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

        LeakCanary.install(this);
        mAppComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }
}
