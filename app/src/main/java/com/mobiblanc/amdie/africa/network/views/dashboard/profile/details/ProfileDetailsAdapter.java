package com.mobiblanc.amdie.africa.network.views.dashboard.profile.details;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mobiblanc.amdie.africa.network.databinding.SectorItemLayoutBinding;
import com.mobiblanc.amdie.africa.network.databinding.TopicItemLayoutBinding;
import com.mobiblanc.amdie.africa.network.models.common.Item;

import java.util.List;

public class ProfileDetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Context context;
    private final List<Item> topics;
    private final String types;

    public ProfileDetailsAdapter(Context context, List<Item> topics, String types) {
        this.context = context;
        this.topics = topics;
        this.types = types;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (types.equalsIgnoreCase("sectors"))
            return new SectorViewHolder(SectorItemLayoutBinding.inflate(
                    LayoutInflater.from(parent.getContext()),
                    parent,
                    false));
        else
            return new TopicViewHolder(TopicItemLayoutBinding.inflate(
                    LayoutInflater.from(parent.getContext()),
                    parent,
                    false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof SectorViewHolder)
            ((SectorViewHolder) holder).bind(topics.get(position));
        else
            ((TopicViewHolder) holder).bind(topics.get(position));
    }

    @Override
    public int getItemCount() {
        return topics.size();
    }

    static class SectorViewHolder extends RecyclerView.ViewHolder {

        private final SectorItemLayoutBinding itemBinding;

        public SectorViewHolder(SectorItemLayoutBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding = itemBinding;
        }

        private void bind(Item item) {
            itemBinding.name.setText(item.getName());
        }
    }

    class TopicViewHolder extends RecyclerView.ViewHolder {

        private final TopicItemLayoutBinding itemBinding;

        public TopicViewHolder(TopicItemLayoutBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding = itemBinding;
        }

        private void bind(Item item) {
            itemBinding.name.setText(item.getName());
            Glide.with(context).load(item.getIcon()).into(itemBinding.icon);
        }
    }
}