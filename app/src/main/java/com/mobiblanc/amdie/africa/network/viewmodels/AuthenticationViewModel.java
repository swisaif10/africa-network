package com.mobiblanc.amdie.africa.network.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.mobiblanc.amdie.africa.network.utilities.Resource;
import com.mobiblanc.amdie.africa.network.models.authentication.checksms.CheckSMSData;
import com.mobiblanc.amdie.africa.network.models.authentication.sendsms.SendSMSData;
import com.mobiblanc.amdie.africa.network.models.authentication.updateprofile.UpdateProfileData;
import com.mobiblanc.amdie.africa.network.repository.AuthenticationRepository;

public class AuthenticationViewModel extends AndroidViewModel {

    private final AuthenticationRepository repository;
    private final MutableLiveData<Resource<SendSMSData>> sendSMSLiveData;
    private final MutableLiveData<Resource<SendSMSData>> sendOTPBYEmailLiveData;
    private final MutableLiveData<Resource<CheckSMSData>> checkSMSLiveData;
    private final MutableLiveData<Resource<CheckSMSData>> checkOTPByEmailLiveData;
    private final MutableLiveData<Resource<UpdateProfileData>> updateProfileLiveData;

    public AuthenticationViewModel(@NonNull Application application) {
        super(application);

        this.repository = new AuthenticationRepository();
        sendSMSLiveData = new MutableLiveData<>();
        checkSMSLiveData = new MutableLiveData<>();
        updateProfileLiveData = new MutableLiveData<>();
        sendOTPBYEmailLiveData = new MutableLiveData<>();
        checkOTPByEmailLiveData = new MutableLiveData<>();
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

    public MutableLiveData<Resource<SendSMSData>> getSendOTPBYEmailLiveData() {
        return sendOTPBYEmailLiveData;
    }

    public MutableLiveData<Resource<CheckSMSData>> getCheckOTPByEmailLiveData() {
        return checkOTPByEmailLiveData;
    }

    public void sendSMS(String msisdn, String lang, String uid) {
        repository.sendSMS(msisdn, lang, uid, sendSMSLiveData);
    }

    public void checkSMS(String msisdn, String code, String firebaseToken, String lang) {
        repository.checkSMS(msisdn, code, firebaseToken, lang, checkSMSLiveData);
    }

    public void sendOTPByEmail(String msisdn, String lang, String uid) {
        repository.sendOTPByEmail(msisdn, lang, uid, sendSMSLiveData);
    }

    public void checkOTPByEmail(String msisdn, String code, String firebaseToken, String lang) {
        repository.checkOTPByEmail(msisdn, code, firebaseToken, lang, checkSMSLiveData);
    }

    public void updateProfile(String token,
                              String gender,
                              String lastName,
                              String company,
                              String job,
                              String email,
                              String firstName,
                              int country,
                              int city,
                              int nationality,
                              String firebaseToken) {
        repository.updateProfile(token, gender, lastName, company, job, email, firstName, country, city, nationality, firebaseToken, updateProfileLiveData);
    }
}
