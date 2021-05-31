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
import com.mobiblanc.amdie.africa.network.databinding.ItemLoadingLayoutBinding;
import com.mobiblanc.amdie.africa.network.listeners.OnContactSelectedListener;
import com.mobiblanc.amdie.africa.network.models.contacts.list.Contact;
import com.mobiblanc.amdie.africa.network.utilities.LoadingViewHolder;
import com.mobiblanc.amdie.africa.network.views.base.BaseViewHolder;

import java.util.List;

public class ContactsAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private static final int VIEW_TYPE_LOADING = 0;
    private static final int VIEW_TYPE_NORMAL = 1;

    private final Context context;
    private final List<Contact> contacts;
    private final OnContactSelectedListener onContactSelectedListener;
    private final int type;
    private boolean isLoaderVisible = false;

    public ContactsAdapter(Context context, List<Contact> contacts, OnContactSelectedListener onContactSelectedListener, int type) {
        this.context = context;
        this.contacts = contacts;
        this.onContactSelectedListener = onContactSelectedListener;
        this.type = type;
    }

    @Override
    public int getItemViewType(int position) {
        if (isLoaderVisible) {
            return position == contacts.size() - 1 ? VIEW_TYPE_LOADING : VIEW_TYPE_NORMAL;
        } else {
            return VIEW_TYPE_NORMAL;
        }
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_NORMAL:
                return new ViewHolder(ContactItemLayoutBinding.inflate(
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
        return contacts == null ? 0 : contacts.size();
    }

    public void addLoading() {
        isLoaderVisible = true;
        contacts.add(new Contact());
        notifyItemInserted(contacts.size() - 1);
    }

    public void removeLoading() {
        isLoaderVisible = false;
        int position = contacts.size() - 1;
        Contact item = getItem(position);
        if (item != null) {
            contacts.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        contacts.clear();
        notifyDataSetChanged();
    }

    public Contact getItem(int position) {
        return contacts.get(position);
    }

    public void removeItem(int position) {
        contacts.remove(position);
        notifyItemRemoved(position);
    }

    class ViewHolder extends BaseViewHolder {

        ContactItemLayoutBinding itemBinding;

        ViewHolder(ContactItemLayoutBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding = itemBinding;
        }

        public void bind(int position) {
            Contact contact = contacts.get(position);
            itemBinding.name.setText(contact.getUsername());
            itemBinding.description.setText(String.format("%s / %s", contact.getCompanyName(), contact.getCountry()));
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
            itemBinding.getRoot().setOnClickListener(v -> onContactSelectedListener.onContactSelected(contact));
        }

        @Override
        protected void clear() {

        }
    }
}