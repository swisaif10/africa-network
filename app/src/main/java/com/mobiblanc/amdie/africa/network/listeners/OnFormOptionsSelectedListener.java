package com.mobiblanc.amdie.africa.network.listeners;

import com.mobiblanc.amdie.africa.network.models.common.Item;

import java.util.List;

public interface OnFormOptionsSelectedListener {

    void onTopicsSelected(List<Item> selectedOptions, String name);
}
