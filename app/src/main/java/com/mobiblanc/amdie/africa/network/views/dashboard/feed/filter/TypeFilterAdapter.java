package com.mobiblanc.amdie.africa.network.views.dashboard.feed.filter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobiblanc.amdie.africa.network.databinding.FilterItemLayoutBinding;
import com.mobiblanc.amdie.africa.network.listeners.OnFilterCheckedChangeListener;
import com.mobiblanc.amdie.africa.network.models.feed.Type;

import java.util.List;

public class TypeFilterAdapter extends RecyclerView.Adapter<TypeFilterAdapter.ViewHolder> {

    private final List<Type> types;
    private final OnFilterCheckedChangeListener onFilterCheckedChangeListener;

    public TypeFilterAdapter(List<Type> types, OnFilterCheckedChangeListener onFilterCheckedChangeListener) {
        this.types = types;
        this.onFilterCheckedChangeListener = onFilterCheckedChangeListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(FilterItemLayoutBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(types.get(position));
    }

    @Override
    public int getItemCount() {
        return types.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        FilterItemLayoutBinding itemBinding;

        ViewHolder(FilterItemLayoutBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding = itemBinding;
        }

        private void bind(Type type) {
            itemBinding.title.setText(type.getValue());
            itemBinding.checkbox.setChecked(type.getChecked());

            itemBinding.getRoot().setOnClickListener(v -> {
                if (type.getChecked()) {
                    type.setChecked(false);
                    onFilterCheckedChangeListener.onFilterUnchecked(type, "");
                } else {
                    type.setChecked(true);
                    onFilterCheckedChangeListener.onFilterChecked(type, "");
                }
                notifyDataSetChanged();
            });
        }
    }
}