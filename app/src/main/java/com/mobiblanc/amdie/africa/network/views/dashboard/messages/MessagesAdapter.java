package com.mobiblanc.amdie.africa.network.views.dashboard.messages;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobiblanc.amdie.africa.network.databinding.MessageItemLayoutBinding;
import com.mobiblanc.amdie.africa.network.views.chat.ChatActivity;

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.ViewHolder> {

    private final Context context;

    public MessagesAdapter(Context context) {
        this.context = context;
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
        holder.bind();
    }

    @Override
    public int getItemCount() {
        return 10;
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        MessageItemLayoutBinding itemBinding;

        ViewHolder(MessageItemLayoutBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding = itemBinding;
        }

        private void bind() {
            itemBinding.getRoot().setOnClickListener(v -> context.startActivity(new Intent(context, ChatActivity.class)));
        }
    }
}