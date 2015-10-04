package com.github.pengrad.podcasts.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.pengrad.podcasts.R;
import com.github.pengrad.podcasts.model.data.Podcast;
import com.github.pengrad.recyclerview.ItemClickListener;
import com.github.pengrad.recyclerview.RecyclerViewHolder;
import com.github.pengrad.recyclerview.RecyclerViewListAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * stas
 * 8/31/15
 */
public class ItunesSearchRecyclerAdapter extends RecyclerViewListAdapter<Podcast> {

    public ItunesSearchRecyclerAdapter(ItemClickListener<Podcast> itemClickListener) {
        super(itemClickListener);
    }

    @Override
    public RecyclerViewHolder<Podcast> onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_podcast, parent, false);
        return new ViewHolder(view);
    }

    public static class ViewHolder extends RecyclerViewHolder<Podcast> {

        @Bind(R.id.imageView) ImageView mImageView;
        @Bind(R.id.text_title) TextView mTextTitle;
        @Bind(R.id.text_artist) TextView mTextArtist;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onBindItem(Podcast podcast) {
            mTextTitle.setText(podcast.getTitle());
            mTextArtist.setText(podcast.getArtistName());
            Glide.with(mImageView.getContext()).load(podcast.getImageUrl()).into(mImageView);
        }
    }
}
