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

public class ContactsViewModel extends AndroidViewModel {

    private final ContactsRepository repository;
//    private final MutableLiveData<Resource<SendSMSData>> sendSMSLiveData;
//    private final MutableLiveData<Resource<CheckSMSData>> checkSMSLiveData;
    private final MutableLiveData<Resource<UpdateMentoreData>> updateMentoreDataLiveData;
//
    public ContactsViewModel(@NonNull Application application) {
        super(application);

        this.repository = new ContactsRepository();
        updateMentoreDataLiveData = new MutableLiveData<>();
    }
//
//    public MutableLiveData<Resource<SendSMSData>> getSendSMSLiveData() {
//        return sendSMSLiveData;
//    }
//
//    public MutableLiveData<Resource<CheckSMSData>> getCheckSMSLiveData() {
//        return checkSMSLiveData;
//    }
//
//    public MutableLiveData<Resource<UpdateProfileData>> getUpdateProfileLiveData() {
//        return updateProfileLiveData;
//    }
//
//    public void sendSMS(String msisdn, String lang, String uid) {
//        repository.sendSMS(msisdn, lang, uid, sendSMSLiveData);
//    }
//
//    public void checkSMS(String msisdn, String code, String lang) {
//        repository.checkSMS(msisdn, code, lang, checkSMSLiveData);
//    }
//
    public void updateMentore(String token,
                              String pictureProfil,
                              String pictureEntreprise,
                              String lang,
                              String canal,
                              String presentation,
                              int siege,
                              int secteur,
                              int chiffredaffaire,
                              int effectif,
                              int topics,
                              int experiences,
                              MutableLiveData<Resource<UpdateProfileData>> mutableLiveData) {
        repository.updateProfile(token, pictureProfil, pictureEntreprise, lang, canal,   presentation,   siege,    secteur,  chiffredaffaire,
                effectif,     topics,  experiences,  mutableLiveData);
    }
}
