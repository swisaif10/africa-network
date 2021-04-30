package com.mobiblanc.amdie.africa.network.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.mobiblanc.amdie.africa.network.utilities.Resource;
import com.mobiblanc.amdie.africa.network.models.search.init_montoring.InitMontoringData;
import com.mobiblanc.amdie.africa.network.models.search.update_mentore.UpdateMentoreData;
import com.mobiblanc.amdie.africa.network.repository.ContactsRepository;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class SearchViewModel extends AndroidViewModel {

    private final ContactsRepository repository;

    private final MutableLiveData<Resource<UpdateMentoreData>> updateMentoreDataLiveData;
    private final MutableLiveData<Resource<InitMontoringData>> InitMontoringLiveData;
    private final MutableLiveData<Resource<InitMontoringData>> InitProfileLiveData;

    public SearchViewModel(@NonNull Application application) {
        super(application);

        this.repository = new ContactsRepository();
        updateMentoreDataLiveData = new MutableLiveData<>();
        InitMontoringLiveData = new MutableLiveData<>();
        InitProfileLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<Resource<UpdateMentoreData>> getUpdateMentoreLiveData() {
        return updateMentoreDataLiveData;
    }

    public MutableLiveData<Resource<InitMontoringData>> getInitMontoringLiveData() {
        return InitMontoringLiveData;
    }

    public MutableLiveData<Resource<InitMontoringData>> getInitProfileLiveData() {
        return InitProfileLiveData;
    }

    public void updateMentore(RequestBody token,
                              MultipartBody.Part pictureProfil,
                              MultipartBody.Part pictureEntreprise,
                              RequestBody lang,
                              RequestBody canal,
                              RequestBody presentation,
                              RequestBody siege,
                              RequestBody secteur,
                              RequestBody chiffredaffaire,
                              RequestBody effectif,
                              RequestBody topics,
                              RequestBody devise,
                              RequestBody produit) {
        repository.updateMentore(token, pictureProfil, pictureEntreprise, lang, canal, presentation, siege, secteur, chiffredaffaire,
                effectif, topics, devise, produit, updateMentoreDataLiveData);
    }

    public void getInitOntoring(String token,
                                String lang) {
        repository.getInitMontoring(token, lang, InitMontoringLiveData);
    }

    public void getInitProfile(String token,
                               String lang) {
        repository.getInitMontoring(token, lang, InitProfileLiveData);
    }
}
