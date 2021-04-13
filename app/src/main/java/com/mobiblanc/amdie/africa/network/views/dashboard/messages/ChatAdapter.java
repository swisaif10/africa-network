package com.mobiblanc.amdie.africa.network.views.dashboard.messages;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobiblanc.amdie.africa.network.databinding.ReceiveChatItemLayoutBinding;
import com.mobiblanc.amdie.africa.network.databinding.SendChatItemLayoutBinding;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Context context;

    public ChatAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case 0:
            case 2:
                return new ReceiveChatViewHolder(ReceiveChatItemLayoutBinding.inflate(
                        LayoutInflater.from(parent.getContext()),
                        parent,
                        false));
            case 1:
                return new SendChatViewHolder(SendChatItemLayoutBinding.inflate(
                        LayoutInflater.from(parent.getContext()),
                        parent,
                        false));
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof SendChatViewHolder)
            ((SendChatViewHolder) holder).bind();
        else
            ((ReceiveChatViewHolder) holder).bind();
    }

    @Override
    public int getItemCount() {
        return 3;
    }


    class SendChatViewHolder extends RecyclerView.ViewHolder {

        SendChatItemLayoutBinding itemBinding;

        SendChatViewHolder(SendChatItemLayoutBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding = itemBinding;
        }

        private void bind() {

        }
    }

    class ReceiveChatViewHolder extends RecyclerView.ViewHolder {

        ReceiveChatItemLayoutBinding itemBinding;

        ReceiveChatViewHolder(ReceiveChatItemLayoutBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding = itemBinding;
        }

        private void bind() {
            if (getAdapterPosition() == 0)
                itemBinding.message.setText("Bonjour");
            else
                itemBinding.message.setText("Je souhaite investir dans votre projet. Êtes vous disponible cette semaine pour une éventuelle rencontre ?");

        }
    }
}