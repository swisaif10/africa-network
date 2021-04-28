package com.mobiblanc.amdie.africa.network.repository;

import androidx.lifecycle.MutableLiveData;

import com.mobiblanc.amdie.africa.network.Utilities.Resource;
import com.mobiblanc.amdie.africa.network.datamanager.ApiManager;
import com.mobiblanc.amdie.africa.network.models.authentication.SendSMSData;
import com.mobiblanc.amdie.africa.network.models.authentication.checkSMS.CheckSMSData;
import com.mobiblanc.amdie.africa.network.models.authentication.updateprofile.UpdateProfileData;

public class AuthenticationRepository {

    public void sendSMS(String msisdn, String lang, String uid, MutableLiveData<Resource<SendSMSData>> mutableLiveData) {
        new ApiManager().sendSMS(msisdn, lang, uid, mutableLiveData);
    }

    public void checkSMS(String msisdn, String code, String firebaseToken, String lang, MutableLiveData<Resource<CheckSMSData>> mutableLiveData) {
        new ApiManager().checkSMS(msisdn, code, firebaseToken, lang, mutableLiveData);
    }

    public void updateProfile(String token,
                              String lastName,
                              String company,
                              String job,
                              String email,
                              String firstName,
                              int country,
                              int city,
                              int nationality,
                              MutableLiveData<Resource<UpdateProfileData>> mutableLiveData) {
        new ApiManager().updateProfile(token, lastName, company, job, email, firstName, country, city, nationality, mutableLiveData);
    }
}
