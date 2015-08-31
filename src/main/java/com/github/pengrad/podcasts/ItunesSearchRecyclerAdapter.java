package com.github.pengrad.podcasts;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.pengrad.podcasts.recyclerview.ItemClickListener;
import com.github.pengrad.podcasts.recyclerview.RecyclerViewHolder;
import com.github.pengrad.podcasts.recyclerview.RecyclerViewListAdapter;

/**
 * stas
 * 8/31/15
 */
public class ItunesSearchRecyclerAdapter extends RecyclerViewListAdapter<ItunesResult.Podcast> {

    public ItunesSearchRecyclerAdapter(ItemClickListener<ItunesResult.Podcast> itemClickListener) {
        super(itemClickListener);
    }

    @Override
    public RecyclerViewHolder<ItunesResult.Podcast> onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        return new ViewHolder(view);
    }

    public static class ViewHolder extends RecyclerViewHolder<ItunesResult.Podcast> {

        public ViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindItem(ItunesResult.Podcast item) {
            TextView textView = (TextView) super.itemView.findViewById(android.R.id.text1);
            textView.setText(item.toString());
        }
    }
}
