package com.mobiblanc.amdie.africa.network.views.dashboard.contacts;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobiblanc.amdie.africa.network.databinding.FilterItemLayoutBinding;
import com.mobiblanc.amdie.africa.network.listeners.OnFilterCheckedChangeListener;
import com.mobiblanc.amdie.africa.network.models.common.Item;
import com.mobiblanc.amdie.africa.network.models.feed.Sector;

import java.util.List;

public class ContactsFilterAdapter extends RecyclerView.Adapter<ContactsFilterAdapter.ViewHolder> {

    private final List<Item> items;
    private final OnFilterCheckedChangeListener onFilterCheckedChangeListener;

    public ContactsFilterAdapter(List<Item> items, OnFilterCheckedChangeListener onFilterCheckedChangeListener) {
        this.items = items;
        this.onFilterCheckedChangeListener = onFilterCheckedChangeListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewsector) {
        return new ViewHolder(FilterItemLayoutBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        FilterItemLayoutBinding itemBinding;

        ViewHolder(FilterItemLayoutBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding = itemBinding;
        }

        private void bind(Item item) {

            itemBinding.title.setText(item.getName());
            itemBinding.checkbox.setChecked(item.getSelected());

            itemBinding.getRoot().setOnClickListener(v -> {

            });
        }
    }
}