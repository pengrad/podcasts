package com.github.pengrad.podcasts.ui;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;

/**
 * Stas Parshin
 * 11 October 2015
 */
public class AnimationUtils {

    public static void startRotateAnimation(View view) {
        RotateAnimation animation = new RotateAnimation(0, 360, view.getWidth() / 2, view.getHeight() / 2);
        animation.setDuration(600); // duration in ms
        animation.setRepeatCount(Animation.INFINITE);
        animation.setFillAfter(true);
        animation.setInterpolator(new LinearInterpolator());
        view.startAnimation(animation);
    }

}
