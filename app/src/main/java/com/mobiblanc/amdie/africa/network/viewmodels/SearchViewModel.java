package com.mobiblanc.amdie.africa.network.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.mobiblanc.amdie.africa.network.Utilities.Resource;
import com.mobiblanc.amdie.africa.network.models.authentication.SendSMSData;
import com.mobiblanc.amdie.africa.network.models.authentication.checkSMS.CheckSMSData;
import com.mobiblanc.amdie.africa.network.models.authentication.updateprofile.UpdateProfileData;

import com.mobiblanc.amdie.africa.network.models.search.update_mentore.UpdateMentoreData;
import com.mobiblanc.amdie.africa.network.repository.ContactsRepository;

public class SearchViewModel extends AndroidViewModel {

    private final ContactsRepository repository;

    private final MutableLiveData<Resource<UpdateMentoreData>> updateMentoreDataLiveData;

    public SearchViewModel(@NonNull Application application) {
        super(application);

        this.repository = new ContactsRepository();
        updateMentoreDataLiveData = new MutableLiveData<>();
    }

    public void updateMentore(String token,
                              String pictureProfil,
                              String pictureEntreprise,
                              String lang,
                              String canal,
                              String presentation,
                              String siege,
                              String secteur,
                              String chiffredaffaire,
                              String effectif,
                              String topics,
                              String experiences,
                              MutableLiveData<Resource<UpdateProfileData>> mutableLiveData) {
        repository.updateProfile(token, pictureProfil, pictureEntreprise, lang, canal,   presentation,   siege,    secteur,  chiffredaffaire,
                effectif,     topics,  experiences,  mutableLiveData);
    }
}
