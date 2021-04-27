package com.mobiblanc.amdie.africa.network.views.dashboard.contacts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mobiblanc.amdie.africa.network.R;
import com.mobiblanc.amdie.africa.network.databinding.ContactItemLayoutBinding;
import com.mobiblanc.amdie.africa.network.listeners.OnContactSelectedListener;
import com.mobiblanc.amdie.africa.network.models.contacts.list.Contact;

import java.util.List;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ViewHolder> {

    private final Context context;
    private final List<Contact> contacts;
    private final OnContactSelectedListener onContactSelectedListener;
    private final int type;

    public ContactsAdapter(Context context, List<Contact> contacts, OnContactSelectedListener onContactSelectedListener, int type) {
        this.context = context;
        this.contacts = contacts;
        this.onContactSelectedListener = onContactSelectedListener;
        this.type = type;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ContactItemLayoutBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(contacts.get(position));
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public void removeItem(int position) {
        contacts.remove(position);
        notifyItemRemoved(position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ContactItemLayoutBinding itemBinding;

        ViewHolder(ContactItemLayoutBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding = itemBinding;
        }

        private void bind(Contact contact) {
            itemBinding.name.setText(contact.getUsername());
            itemBinding.description.setText(String.format("%s / %s", contact.getCompanyName(), contact.getProvince()));
            Glide.with(context).load(contact.getProfilePicture()).fitCenter().into(itemBinding.icon);

            if (type == 1)
                itemBinding.addToFavouritesBtn.setVisibility(View.GONE);
            else if (contact.isFavourite())
                itemBinding.addToFavouritesBtn.setImageResource(R.drawable.favoris);
            else
                itemBinding.addToFavouritesBtn.setImageResource(R.drawable.favoris_1);

            itemBinding.addToFavouritesBtn.setOnClickListener(v -> {
                contact.setFavourite(!contact.isFavourite());
                notifyDataSetChanged();
                onContactSelectedListener.onFavouriteClick(contact);
            });
            itemBinding.sendMessageBtn.setOnClickListener(v -> onContactSelectedListener.onMessageClick(contact));
        }
    }
}