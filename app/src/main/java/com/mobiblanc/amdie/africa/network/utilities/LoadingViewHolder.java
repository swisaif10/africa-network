package com.mobiblanc.amdie.africa.network.utilities;

import com.mobiblanc.amdie.africa.network.databinding.ItemLoadingLayoutBinding;
import com.mobiblanc.amdie.africa.network.views.base.BaseViewHolder;

public class LoadingViewHolder extends BaseViewHolder {

    ItemLoadingLayoutBinding itemBinding;

    public LoadingViewHolder(ItemLoadingLayoutBinding itemBinding) {
        super(itemBinding.getRoot());
        this.itemBinding = itemBinding;
    }

    @Override
    protected void clear() {

    }

}