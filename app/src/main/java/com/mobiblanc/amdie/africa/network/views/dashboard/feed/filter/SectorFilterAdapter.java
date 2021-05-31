package com.mobiblanc.amdie.africa.network.views.dashboard.feed.filter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobiblanc.amdie.africa.network.databinding.FilterItemLayoutBinding;
import com.mobiblanc.amdie.africa.network.listeners.OnFilterCheckedChangeListener;
import com.mobiblanc.amdie.africa.network.models.feed.Sector;

import java.util.List;

public class SectorFilterAdapter extends RecyclerView.Adapter<SectorFilterAdapter.ViewHolder> {

    private final List<Sector> sectors;
    private final OnFilterCheckedChangeListener onFilterCheckedChangeListener;

    public SectorFilterAdapter(List<Sector> sectors, OnFilterCheckedChangeListener onFilterCheckedChangeListener) {
        this.sectors = sectors;
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
        holder.bind(sectors.get(position));
    }

    @Override
    public int getItemCount() {
        return sectors.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        FilterItemLayoutBinding itemBinding;

        ViewHolder(FilterItemLayoutBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding = itemBinding;
        }

        private void bind(Sector sector) {

            itemBinding.title.setText(sector.getValue());
            itemBinding.checkbox.setChecked(sector.getChecked());

            itemBinding.getRoot().setOnClickListener(v -> {
                if (sector.getChecked()) {
                    sector.setChecked(false);
                    notifyDataSetChanged();
                    onFilterCheckedChangeListener.onFilterUnchecked(sector);
                } else {
                    sector.setChecked(true);
                    notifyDataSetChanged();
                    onFilterCheckedChangeListener.onFilterChecked(sector);
                }
            });
        }
    }
}