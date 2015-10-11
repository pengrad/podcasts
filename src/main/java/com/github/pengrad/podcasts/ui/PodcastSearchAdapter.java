package com.github.pengrad.podcasts.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.pengrad.podcasts.R;
import com.github.pengrad.podcasts.model.data.FeedEpisode;
import com.github.pengrad.recyclerview.ItemClickListener;
import com.github.pengrad.recyclerview.RecyclerViewHolder;
import com.github.pengrad.recyclerview.RecyclerViewListAdapter;

import java.util.Collection;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Stas Parshin
 * 11 October 2015
 */
public class PodcastSearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int VIEW_TYPE_DESC = 0, VIEW_TYPE_LIST = 1;

    private String mDescription;
    private EpisodeAdapter mEpisodeAdapter;

    public PodcastSearchAdapter(ItemClickListener<FeedEpisode> itemClickListener) {
        mDescription = "";
        mEpisodeAdapter = new EpisodeAdapter(itemClickListener);
    }

    public void setDescription(String description) {
        mDescription = description;
        notifyItemChanged(0);
    }

    public void setEpisodes(Collection<FeedEpisode> episodes) {
        mEpisodeAdapter.setData(episodes);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_DESC) {
            return new DescViewHolder(new TextView(parent.getContext()));
        } else {
            return mEpisodeAdapter.onCreateViewHolder(parent, viewType);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == VIEW_TYPE_DESC) {
            DescViewHolder viewHolder = (DescViewHolder) holder;
            viewHolder.onBindItem(mDescription);
        } else {
            mEpisodeAdapter.onBindViewHolder((RecyclerViewHolder<FeedEpisode>) holder, position - 1);
        }
    }

    @Override
    public int getItemCount() {
        return 1 + mEpisodeAdapter.getItemCount();
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? VIEW_TYPE_DESC : VIEW_TYPE_LIST;
    }

    public static class DescViewHolder extends RecyclerViewHolder<String> {

        private TextView mTextDesc;

        public DescViewHolder(View itemView) {
            super(itemView);
            mTextDesc = (TextView) itemView;
        }

        @Override
        public void onBindItem(String item) {
            mTextDesc.setText(item);
        }
    }

    public static class EpisodeAdapter extends RecyclerViewListAdapter<FeedEpisode> {

        public EpisodeAdapter(ItemClickListener<FeedEpisode> itemClickListener) {
            super(itemClickListener);
        }

        @Override
        public RecyclerViewHolder<FeedEpisode> onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.episode, parent, false);
            return new EpisodeViewHolder(view);
        }
    }

    public static class EpisodeViewHolder extends RecyclerViewHolder<FeedEpisode> {

        @Bind(R.id.episodeTitle) TextView mTextTitle;
        @Bind(R.id.episodeSummary) TextView mTextSummary;

        public EpisodeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onBindItem(FeedEpisode episode) {
            mTextTitle.setText(episode.title);
        }
    }
}
