package com.mobiblanc.amdie.africa.network.listeners;

import com.mobiblanc.amdie.africa.network.models.contacts.list.Contact;

public interface OnContactSelectedListener {
    void onFavouriteClick(Contact contact);

    void onMessageClick(Contact contact);
}
