package com.github.pengrad.podcasts.ui;

import android.content.Context;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.widget.FrameLayout;

import com.github.pengrad.podcasts.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Stas Parshin
 * 15 October 2015
 */
public class RotatingFab extends FrameLayout {

    @Bind(R.id.fab) FloatingActionButton mFab;
    @Bind(R.id.fabImage) View mFabImage;

    public RotatingFab(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
        mFabImage.setVisibility(GONE);
    }

    @Override
    public void startAnimation(Animation animation) {
        mFab.setEnabled(false);
        if (Build.VERSION.SDK_INT >= 21) {
            mFab.startAnimation(animation);
        } else {
            mFabImage.setVisibility(VISIBLE);
            mFabImage.post(() -> mFabImage.startAnimation(animation));
        }
    }

    @Override
    public void clearAnimation() {
        if (Build.VERSION.SDK_INT >= 21) {
            mFab.clearAnimation();
        } else {
            mFabImage.clearAnimation();
            mFabImage.setVisibility(GONE);
        }
        mFab.setEnabled(true);
    }
}
