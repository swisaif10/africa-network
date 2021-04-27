package com.mobiblanc.amdie.africa.network.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.mobiblanc.amdie.africa.network.Utilities.Resource;
import com.mobiblanc.amdie.africa.network.models.authentication.SendSMSData;
import com.mobiblanc.amdie.africa.network.models.authentication.checkSMS.CheckSMSData;
import com.mobiblanc.amdie.africa.network.models.authentication.updateprofile.UpdateProfileData;
import com.mobiblanc.amdie.africa.network.repository.AuthenticationRepository;

public class AuthenticationViewModel extends AndroidViewModel {

    private final AuthenticationRepository repository;
    private final MutableLiveData<Resource<SendSMSData>> sendSMSLiveData;
    private final MutableLiveData<Resource<CheckSMSData>> checkSMSLiveData;
    private final MutableLiveData<Resource<UpdateProfileData>> updateProfileLiveData;

    public AuthenticationViewModel(@NonNull Application application) {
        super(application);

        this.repository = new AuthenticationRepository();
        sendSMSLiveData = new MutableLiveData<>();
        checkSMSLiveData = new MutableLiveData<>();
        updateProfileLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<Resource<SendSMSData>> getSendSMSLiveData() {
        return sendSMSLiveData;
    }

    public MutableLiveData<Resource<CheckSMSData>> getCheckSMSLiveData() {
        return checkSMSLiveData;
    }

    public MutableLiveData<Resource<UpdateProfileData>> getUpdateProfileLiveData() {
        return updateProfileLiveData;
    }

    public void sendSMS(String msisdn, String lang, String uid) {
        repository.sendSMS(msisdn, lang, uid, sendSMSLiveData);
    }

    public void checkSMS(String msisdn, String code, String firebaseToken, String lang) {
        repository.checkSMS(msisdn, code, firebaseToken, lang, checkSMSLiveData);
    }

    public void updateProfile(String token,
                              String lastName,
                              String company,
                              String job,
                              String email,
                              String firstName,
                              int country,
                              int city,
                              int nationality) {
        repository.updateProfile(token, lastName, company, job, email, firstName, country, city, nationality, updateProfileLiveData);
    }
}
