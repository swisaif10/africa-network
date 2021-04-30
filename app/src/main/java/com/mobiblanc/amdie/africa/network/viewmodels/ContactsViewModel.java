package com.mobiblanc.amdie.africa.network.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.mobiblanc.amdie.africa.network.utilities.Resource;
import com.mobiblanc.amdie.africa.network.models.contacts.favourite.AddFavouriteData;
import com.mobiblanc.amdie.africa.network.models.contacts.list.ContactsListData;
import com.mobiblanc.amdie.africa.network.repository.ContactRepository;

public class ContactsViewModel extends AndroidViewModel {

    private final ContactRepository repository;

    private final MutableLiveData<Resource<ContactsListData>> contactsListLiveData;
    private final MutableLiveData<Resource<ContactsListData>> favouritesListLiveData;
    private final MutableLiveData<Resource<ContactsListData>> suggestionsListLiveData;
    private final MutableLiveData<Resource<AddFavouriteData>> addFavouriteLiveData;
    private final MutableLiveData<Resource<AddFavouriteData>> removeFavouriteLiveData;

    public ContactsViewModel(@NonNull Application application) {
        super(application);

        this.repository = new ContactRepository();
        contactsListLiveData = new MutableLiveData<>();
        addFavouriteLiveData = new MutableLiveData<>();
        removeFavouriteLiveData = new MutableLiveData<>();
        favouritesListLiveData = new MutableLiveData<>();
        suggestionsListLiveData = new MutableLiveData<>();
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

    public void getContactsList(String token, int page, String searchValue, String lang) {
        repository.getContactsList(token, page, searchValue, lang, contactsListLiveData);
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
}
