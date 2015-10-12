package com.github.pengrad.podcasts;

import android.app.Application;
import android.content.Context;

import com.github.pengrad.podcasts.di.AppComponent;
import com.github.pengrad.podcasts.di.AppModule;
import com.github.pengrad.podcasts.di.DaggerAppComponent;
import com.github.pengrad.podcasts.model.data.Podcast;
import com.github.pengrad.podcasts.model.data.PodcastEpisode;
import com.squareup.leakcanary.LeakCanary;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import co.uk.rushorm.android.AndroidInitializeConfig;
import co.uk.rushorm.core.Rush;
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

        LeakCanary.install(this);
        initRush();
        mAppComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
    }

    private void initRush() {
        CountDownLatch latch = new CountDownLatch(1);

        List<Class<? extends Rush>> classes = new ArrayList<>();
        classes.add(Podcast.class);
        classes.add(PodcastEpisode.class);

        AndroidInitializeConfig config = new AndroidInitializeConfig(this);
        config.setClasses(classes);
        config.setInitializeListener(isInit -> latch.countDown());
        RushCore.initialize(config);

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }
}
