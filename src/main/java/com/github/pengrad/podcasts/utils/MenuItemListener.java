package com.github.pengrad.podcasts.utils;

import android.support.v4.view.MenuItemCompat;
import android.view.MenuItem;

/**
 * Stas Parshin
 * 06 October 2015
 */
public class MenuItemListener implements MenuItemCompat.OnActionExpandListener {

    public interface OnItemCollapseListener {
        boolean onItemCollapse(MenuItem item);
    }

    private OnItemCollapseListener mCollapseListener;

    public MenuItemListener(OnItemCollapseListener collapseListener) {
        mCollapseListener = collapseListener;
    }

    @Override
    public boolean onMenuItemActionExpand(MenuItem item) {
        return true;
    }

    @Override
    public boolean onMenuItemActionCollapse(MenuItem item) {
        return mCollapseListener.onItemCollapse(item);
    }
}
