package com.mobiblanc.amdie.africa.network.views.dashboard.feed;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mobiblanc.amdie.africa.network.R;
import com.mobiblanc.amdie.africa.network.databinding.FeedItemLayoutBinding;
import com.mobiblanc.amdie.africa.network.listeners.OnItemSelectedListener;
import com.mobiblanc.amdie.africa.network.models.feed.Feed;

import java.util.List;

public class FeedsAdapter extends RecyclerView.Adapter<FeedsAdapter.ViewHolder> {

    private final Context context;
    private final List<Feed> feeds;
    private final OnItemSelectedListener onItemSelectedListener;

    public FeedsAdapter(Context context, List<Feed> feeds, OnItemSelectedListener onItemSelectedListener) {
        this.context = context;
        this.feeds = feeds;
        this.onItemSelectedListener = onItemSelectedListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(FeedItemLayoutBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(feeds.get(position));
    }

    @Override
    public int getItemCount() {
        return feeds.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        FeedItemLayoutBinding itemBinding;

        ViewHolder(FeedItemLayoutBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding = itemBinding;
        }

        private void bind(Feed feed) {
            Glide.with(context).load(feed.getLogo()).fitCenter().into(itemBinding.icon);
            itemBinding.name.setText(feed.getTitle());
            itemBinding.date.setText(feed.getDate());
            itemBinding.description.setText(Html.fromHtml(feed.getBody()));
            Glide.with(context).load(feed.getImage()).fitCenter().into(itemBinding.image);

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
                onItemSelectedListener.onItemSelectedListener(feed);
            });

        }
    }
}