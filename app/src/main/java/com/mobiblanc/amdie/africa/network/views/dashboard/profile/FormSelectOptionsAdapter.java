package com.mobiblanc.amdie.africa.network.views.dashboard.profile;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobiblanc.amdie.africa.network.databinding.FormOptionsItemLayoutBinding;
import com.mobiblanc.amdie.africa.network.listeners.OnFormOptionsSelectedListener;
import com.mobiblanc.amdie.africa.network.models.common.Item;

import java.util.ArrayList;
import java.util.List;

public class FormSelectOptionsAdapter extends RecyclerView.Adapter<FormSelectOptionsAdapter.ViewHolder> {

    private final List<Item> referenceObjectValues;
    private final int max;
    private final OnFormOptionsSelectedListener onFormOptionsSelectedListener;
    private final List<Item> selectedOptions;
    private final String name;
    private int nbOptionsChecked = 0;
    private int lowestPosition = -1;
    private Boolean clicked = false;

    public FormSelectOptionsAdapter(String name, List<Item> referenceObjectValues, int max, OnFormOptionsSelectedListener onFormOptionsSelectedListener) {
        this.name = name;
        this.referenceObjectValues = referenceObjectValues;
        this.max = max;
        this.onFormOptionsSelectedListener = onFormOptionsSelectedListener;
        this.selectedOptions = new ArrayList<>();
    }

    @NonNull
    @Override
    public FormSelectOptionsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FormSelectOptionsAdapter.ViewHolder(FormOptionsItemLayoutBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false), onFormOptionsSelectedListener);
    }

    @Override
    public void onBindViewHolder(@NonNull FormSelectOptionsAdapter.ViewHolder holder, int position) {
        if (clicked || position > lowestPosition) {
            holder.bind(position);
            lowestPosition = position;
            clicked = false;
        }
    }

    @Override
    public int getItemCount() {
        return referenceObjectValues.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private final FormOptionsItemLayoutBinding itemBinding;
        private final OnFormOptionsSelectedListener onFormOptionsSelectedListener;

        ViewHolder(FormOptionsItemLayoutBinding itemBinding, OnFormOptionsSelectedListener onFormOptionsSelectedListener) {
            super(itemBinding.getRoot());
            this.itemBinding = itemBinding;
            this.onFormOptionsSelectedListener = onFormOptionsSelectedListener;
        }

        private void bind(int position) {
            Item item = referenceObjectValues.get(position);
            itemBinding.checkBox.setText(item.getName());
            if (item.getSelected()) {
                nbOptionsChecked++;
                selectedOptions.add(item);
                onFormOptionsSelectedListener.onTopicsSelected(selectedOptions, name);
            } else if (selectedOptions.contains(item)) {
                selectedOptions.remove(item);
                nbOptionsChecked--;
                onFormOptionsSelectedListener.onTopicsSelected(selectedOptions, name);
            }
            itemBinding.checkBox.setChecked(item.getSelected());

            itemBinding.container.setOnClickListener(v -> {
                clicked = true;
                if (item.getSelected())
                    item.setSelected(false);
                else if (nbOptionsChecked < max)
                    item.setSelected(true);
                notifyItemChanged(position);
            });
        }
    }
}
