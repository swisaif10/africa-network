package com.mobiblanc.amdie.africa.network.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.mobiblanc.amdie.africa.network.models.contacts.favourite.AddFavouriteData;
import com.mobiblanc.amdie.africa.network.models.contacts.filter.ContactsFilterData;
import com.mobiblanc.amdie.africa.network.models.contacts.list.ContactsListData;
import com.mobiblanc.amdie.africa.network.repository.ContactsRepository;
import com.mobiblanc.amdie.africa.network.utilities.Resource;

public class ContactsViewModel extends AndroidViewModel {

    private final ContactsRepository repository;

    private final MutableLiveData<Resource<ContactsListData>> contactsListLiveData;
    private final MutableLiveData<Resource<ContactsListData>> favouritesListLiveData;
    private final MutableLiveData<Resource<ContactsListData>> suggestionsListLiveData;
    private final MutableLiveData<Resource<AddFavouriteData>> addFavouriteLiveData;
    private final MutableLiveData<Resource<AddFavouriteData>> removeFavouriteLiveData;
    private final MutableLiveData<Resource<ContactsFilterData>> contactsFilterLiveData;

    public ContactsViewModel(@NonNull Application application) {
        super(application);

        this.repository = new ContactsRepository();
        contactsListLiveData = new MutableLiveData<>();
        addFavouriteLiveData = new MutableLiveData<>();
        removeFavouriteLiveData = new MutableLiveData<>();
        favouritesListLiveData = new MutableLiveData<>();
        suggestionsListLiveData = new MutableLiveData<>();
        contactsFilterLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<Resource<ContactsListData>> getContactsListLiveData() {
        return contactsListLiveData;
    }

    public MutableLiveData<Resource<AddFavouriteData>> getAddFavouriteLiveData() {
        return addFavouriteLiveData;
    }

    public MutableLiveData<Resource<AddFavouriteData>> getRemoveFavouriteLiveData() {
        return removeFavouriteLiveData;
    }

    public MutableLiveData<Resource<ContactsListData>> getFavouritesListLiveData() {
        return favouritesListLiveData;
    }

    public MutableLiveData<Resource<ContactsListData>> getSuggestionsListLiveData() {
        return suggestionsListLiveData;
    }

    public MutableLiveData<Resource<ContactsFilterData>> getContactsFilterLiveData() {
        return contactsFilterLiveData;
    }

    public void getContactsList(String token, int page, String countryId, String sectorId, String lang) {
        repository.getContactsList(token, page, countryId, sectorId, lang, contactsListLiveData);
    }

    public void addFavourite(String token, int receiver) {
        repository.addFavourite(token, receiver, addFavouriteLiveData);
    }

    public void removeFavourite(String token, int id) {
        repository.removeFavourite(token, id, removeFavouriteLiveData);
    }

    public void getFavouritesList(String token, String lang) {
        repository.getFavouritesList(token, lang, favouritesListLiveData);
    }

    public void getSuggestionsList(String token, int page, String searchValue, String lang) {
        repository.getSuggestionsList(token, page, searchValue, lang, suggestionsListLiveData);
    }

    public void getContactsFilterForm(String token, String lang) {
        repository.getContactsFilterForm(token, lang, contactsFilterLiveData);
    }
}
