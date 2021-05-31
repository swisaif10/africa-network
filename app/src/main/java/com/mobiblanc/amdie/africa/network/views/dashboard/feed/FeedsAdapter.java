package com.mobiblanc.amdie.africa.network.views.dashboard.feed;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mobiblanc.amdie.africa.network.R;
import com.mobiblanc.amdie.africa.network.databinding.FeedItemLayoutBinding;
import com.mobiblanc.amdie.africa.network.databinding.ItemLoadingLayoutBinding;
import com.mobiblanc.amdie.africa.network.listeners.OnItemSelectedListener;
import com.mobiblanc.amdie.africa.network.models.feed.Feed;
import com.mobiblanc.amdie.africa.network.utilities.LoadingViewHolder;
import com.mobiblanc.amdie.africa.network.views.base.BaseViewHolder;

import java.util.List;

public class FeedsAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    private static final int VIEW_TYPE_LOADING = 0;
    private static final int VIEW_TYPE_NORMAL = 1;

    private final Context context;
    private final List<Feed> feeds;
    private final OnItemSelectedListener onItemSelectedListener;
    private boolean isLoaderVisible = false;

    public FeedsAdapter(Context context, List<Feed> feeds, OnItemSelectedListener onItemSelectedListener) {
        this.context = context;
        this.feeds = feeds;
        this.onItemSelectedListener = onItemSelectedListener;
    }

    @Override
    public int getItemViewType(int position) {
        if (isLoaderVisible) {
            return position == feeds.size() - 1 ? VIEW_TYPE_LOADING : VIEW_TYPE_NORMAL;
        } else {
            return VIEW_TYPE_NORMAL;
        }
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_NORMAL:
                return new ViewHolder(FeedItemLayoutBinding.inflate(
                        LayoutInflater.from(parent.getContext()),
                        parent,
                        false));
            case VIEW_TYPE_LOADING:
                return new LoadingViewHolder(ItemLoadingLayoutBinding.inflate(
                        LayoutInflater.from(parent.getContext()),
                        parent,
                        false));
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return feeds == null ? 0 : feeds.size();
    }

    public void addLoading() {
        isLoaderVisible = true;
        feeds.add(new Feed());
        notifyItemInserted(feeds.size() - 1);
    }

    public void removeLoading() {
        isLoaderVisible = false;
        int position = feeds.size() - 1;
        Feed item = getItem(position);
        if (item != null) {
            feeds.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        feeds.clear();
        notifyDataSetChanged();
    }

    public Feed getItem(int position) {
        return feeds.get(position);
    }

    class ViewHolder extends BaseViewHolder {

        FeedItemLayoutBinding itemBinding;

        ViewHolder(FeedItemLayoutBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding = itemBinding;
        }

        @Override
        protected void clear() {

        }

        public void bind(int position) {
            Feed feed = feeds.get(position);
            Glide.with(context).load(feed.getLogo()).fitCenter().into(itemBinding.icon);
            itemBinding.name.setText(feed.getTitle());
            itemBinding.date.setText(feed.getDate());
            itemBinding.description.setText(Html.fromHtml(feed.getBody()));
            Glide.with(context).load(feed.getImage()).fitCenter().into(itemBinding.image);
            itemBinding.pdf.setText(feed.getUrl());
            if (!feed.getUrl().equalsIgnoreCase("#")) {
                itemBinding.pdf.setVisibility(View.VISIBLE);
                itemBinding.image.setOnClickListener(v -> onItemSelectedListener.onItemSelectedListener(feed, true));
            }
            if (feed.getLiked() == 0) {
                itemBinding.likeBtn.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(context, R.drawable.heart_unselected), null, null, null);
                itemBinding.likeBtn.setTextColor(ContextCompat.getColor(context, R.color.likeTextColor));
            } else {
                itemBinding.likeBtn.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(context, R.drawable.heart), null, null, null);
                itemBinding.likeBtn.setTextColor(ContextCompat.getColor(context, R.color.blue4));
            }

            itemBinding.likeBtn.setOnClickListener(v -> {
                if (feed.getLiked() == 0)
                    feed.setLiked(1);
                else
                    feed.setLiked(0);
                notifyDataSetChanged();
                onItemSelectedListener.onItemSelectedListener(feed, false);
            });

        }
    }
}