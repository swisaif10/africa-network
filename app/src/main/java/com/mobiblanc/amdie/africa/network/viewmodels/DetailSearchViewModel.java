package com.mobiblanc.amdie.africa.network.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.mobiblanc.amdie.africa.network.Utilities.Resource;
import com.mobiblanc.amdie.africa.network.models.search.profile.Profile;
import com.mobiblanc.amdie.africa.network.repository.ContactsRepository;

public class DetailSearchViewModel extends AndroidViewModel {

    private final ContactsRepository repository;

    private final MutableLiveData<Resource<Profile>> InitProfileLiveData;

    public DetailSearchViewModel(@NonNull Application application) {
        super(application);

        this.repository = new ContactsRepository();

        InitProfileLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<Resource<Profile>> getInitProfileLiveData() {
        return InitProfileLiveData;
    }

    public void getInitProfile(String token,
                               String lang) {
        repository.getProfile(token, lang, InitProfileLiveData);
    }
}
