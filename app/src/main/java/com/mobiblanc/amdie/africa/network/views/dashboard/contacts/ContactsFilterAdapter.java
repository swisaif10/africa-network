package com.mobiblanc.amdie.africa.network.views.dashboard.contacts;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobiblanc.amdie.africa.network.databinding.FilterItemLayoutBinding;
import com.mobiblanc.amdie.africa.network.listeners.OnFilterCheckedChangeListener;
import com.mobiblanc.amdie.africa.network.models.common.Item;

import java.util.List;

public class ContactsFilterAdapter extends RecyclerView.Adapter<ContactsFilterAdapter.ViewHolder> {

    private final List<Item> items;
    private final OnFilterCheckedChangeListener onFilterCheckedChangeListener;
    private final String type;
    private int lastSelectedPosition = -1;

    public ContactsFilterAdapter(List<Item> items, OnFilterCheckedChangeListener onFilterCheckedChangeListener, String type) {
        this.items = items;
        this.onFilterCheckedChangeListener = onFilterCheckedChangeListener;
        this.type = type;
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
        holder.bind(position);
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

        private void bind(int pos) {
            itemBinding.title.setText(items.get(pos).getName());
            itemBinding.checkbox.setChecked(items.get(pos).getSelected());

            itemBinding.container.setOnClickListener(v -> {
                if (lastSelectedPosition == -1) {
                    items.get(pos).setSelected(true);
                    lastSelectedPosition = pos;
                    notifyItemChanged(pos);
                    onFilterCheckedChangeListener.onFilterChecked(items.get(pos), type);
                } else if (items.get(pos).getSelected()) {
                    items.get(pos).setSelected(false);
                    lastSelectedPosition = -1;
                    notifyItemChanged(pos);
                } else {
                    items.get(lastSelectedPosition).setSelected(false);
                    items.get(pos).setSelected(true);
                    onFilterCheckedChangeListener.onFilterChecked(items.get(pos), type);
                    onFilterCheckedChangeListener.onFilterUnchecked(items.get(lastSelectedPosition), "");
                    notifyItemChanged(pos);
                    notifyItemChanged(lastSelectedPosition);
                    lastSelectedPosition = pos;
                }
            });
        }
    }
}