package com.mobiblanc.amdie.africa.network.views.dashboard.messages;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mobiblanc.amdie.africa.network.databinding.ReceivedChatItemLayoutBinding;
import com.mobiblanc.amdie.africa.network.databinding.SendChatItemLayoutBinding;
import com.mobiblanc.amdie.africa.network.models.messaging.messages.Message;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Context context;
    private final List<Message> messages;
    private final int idReceiver;
    private final String picture;

    public ChatAdapter(Context context, List<Message> messages, int idReceiver, String picture) {
        this.context = context;
        this.messages = messages;
        this.idReceiver = idReceiver;
        this.picture = picture;
    }

    @Override
    public int getItemViewType(int position) {
        if (messages.get(position).getIdClient() != idReceiver)
            return 1001;
        else
            return 1002;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case 1001:
                return new SendChatViewHolder(SendChatItemLayoutBinding.inflate(
                        LayoutInflater.from(parent.getContext()),
                        parent,
                        false));
            case 1002:
                return new ReceiveChatViewHolder(ReceivedChatItemLayoutBinding.inflate(
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
            ((SendChatViewHolder) holder).bind(messages.get(position));
        else
            ((ReceiveChatViewHolder) holder).bind(messages.get(position));
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public void addMessage(Message message) {
        messages.add(message);
        notifyItemInserted(messages.size() - 1);
    }

    static class SendChatViewHolder extends RecyclerView.ViewHolder {

        SendChatItemLayoutBinding itemBinding;

        SendChatViewHolder(SendChatItemLayoutBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding = itemBinding;
        }

        private void bind(Message message) {
            itemBinding.text.setText(message.getMessage());
        }
    }

    class ReceiveChatViewHolder extends RecyclerView.ViewHolder {

        ReceivedChatItemLayoutBinding itemBinding;

        ReceiveChatViewHolder(ReceivedChatItemLayoutBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding = itemBinding;
        }

        private void bind(Message message) {
            Glide.with(context).load(picture).into(itemBinding.icon);
            itemBinding.text.setText(message.getMessage());
        }
    }
}