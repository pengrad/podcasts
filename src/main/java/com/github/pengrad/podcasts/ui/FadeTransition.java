package com.github.pengrad.podcasts.ui;

import android.os.Build;
import android.transition.Fade;
import android.transition.Transition;
import android.view.Window;

/**
 * Stas Parshin
 * 07 October 2015
 */
public class FadeTransition {

    public static void create(Window window) {
        if (Build.VERSION.SDK_INT >= 21) {
            Transition fade = new Fade();
            fade.excludeTarget(android.R.id.statusBarBackground, true);
            fade.excludeTarget(android.R.id.navigationBarBackground, true);
            window.setExitTransition(fade);
            window.setEnterTransition(fade);
        }
    }
}
