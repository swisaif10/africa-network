package com.mobiblanc.amdie.africa.network.repository;

import androidx.lifecycle.MutableLiveData;

import com.mobiblanc.amdie.africa.network.datamanager.ApiManager;
import com.mobiblanc.amdie.africa.network.models.contacts.favourite.AddFavouriteData;
import com.mobiblanc.amdie.africa.network.models.contacts.filter.ContactsFilterData;
import com.mobiblanc.amdie.africa.network.models.contacts.list.ContactsListData;
import com.mobiblanc.amdie.africa.network.utilities.Resource;

public class ContactsRepository {
    public void getContactsList(String token,
                                int page,
                                String countryId,
                                String sectorId,
                                String lang,
                                MutableLiveData<Resource<ContactsListData>> mutableLiveData) {
        new ApiManager().getContactsList(token, page, countryId, sectorId, lang, mutableLiveData);
    }

    public void addFavourite(String token,
                             int receiver,
                             MutableLiveData<Resource<AddFavouriteData>> mutableLiveData) {
        new ApiManager().addFavourite(token, receiver, mutableLiveData);
    }

    public void removeFavourite(String token,
                                int id,
                                MutableLiveData<Resource<AddFavouriteData>> mutableLiveData) {
        new ApiManager().removeFavourite(token, id, mutableLiveData);
    }

    public void getFavouritesList(String token,
                                  String lang,
                                  MutableLiveData<Resource<ContactsListData>> mutableLiveData) {
        new ApiManager().getFavouritesList(token, lang, mutableLiveData);
    }

    public void getSuggestionsList(String token,
                                   int page,
                                   String searchValue,
                                   String lang,
                                   MutableLiveData<Resource<ContactsListData>> mutableLiveData) {
        new ApiManager().getSuggestionsList(token, page, searchValue, lang, mutableLiveData);
    }

    public void getContactsFilterForm(String token,
                                      String lang,
                                      MutableLiveData<Resource<ContactsFilterData>> mutableLiveData) {
        new ApiManager().getContactsFilterForm(token, lang, mutableLiveData);
    }

}
