package com.mobiblanc.amdie.africa.network.views.dashboard.feed.filter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobiblanc.amdie.africa.network.databinding.FilterItemLayoutBinding;
import com.mobiblanc.amdie.africa.network.listeners.OnFilterCheckedChangeListener;
import com.mobiblanc.amdie.africa.network.models.feed.Type;

import java.util.List;

public class TypeFilterAdapter extends RecyclerView.Adapter<TypeFilterAdapter.ViewHolder> {

    private final Context context;
    private final List<Type> types;
    private final OnFilterCheckedChangeListener onFilterCheckedChangeListener;

    public TypeFilterAdapter(Context context, List<Type> types, OnFilterCheckedChangeListener onFilterCheckedChangeListener) {
        this.context = context;
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

            if (type.getChecked())
                itemBinding.checkbox.setChecked(true);
            else
                itemBinding.checkbox.setChecked(false);

            itemBinding.checkbox.setOnClickListener(v -> {
                if (type.getChecked()) {
                    type.setChecked(false);
                    notifyDataSetChanged();
                    onFilterCheckedChangeListener.onFilterUnchecked(type);
                } else {
                    type.setChecked(true);
                    notifyDataSetChanged();
                    onFilterCheckedChangeListener.onFilterChecked(type);
                }
            });

            itemBinding.title.setText(type.getValue());


        }
    }
}