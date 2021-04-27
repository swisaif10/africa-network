package com.mobiblanc.amdie.africa.network.views.dashboard.messages;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mobiblanc.amdie.africa.network.databinding.MessageItemLayoutBinding;
import com.mobiblanc.amdie.africa.network.listeners.OnItemSelectedListener;
import com.mobiblanc.amdie.africa.network.models.messaging.discussions.Discussion;

import java.util.List;

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.ViewHolder> {

    private final Context context;
    private final List<Discussion> discussions;
    private final OnItemSelectedListener onItemSelectedListener;

    public MessagesAdapter(Context context, List<Discussion> discussions, OnItemSelectedListener onItemSelectedListener) {
        this.context = context;
        this.discussions = discussions;
        this.onItemSelectedListener = onItemSelectedListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(MessageItemLayoutBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(discussions.get(position));
    }

    @Override
    public int getItemCount() {
        return discussions.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        MessageItemLayoutBinding itemBinding;

        ViewHolder(MessageItemLayoutBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding = itemBinding;
        }

        private void bind(Discussion discussion) {
            Glide.with(context).load(discussion.getPicture()).into(itemBinding.icon);
            itemBinding.name.setText(discussion.getMessengerName());
            itemBinding.description.setText(String.format("%s/%s", discussion.getCompany(), discussion.getCountry()));
            itemBinding.date.setText(discussion.getDate());

            itemBinding.getRoot().setOnClickListener(v -> onItemSelectedListener.onItemSelectedListener(discussion));
        }
    }
}