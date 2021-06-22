package com.mobiblanc.amdie.africa.network.repository;

import androidx.lifecycle.MutableLiveData;

import com.mobiblanc.amdie.africa.network.datamanager.ApiManager;
import com.mobiblanc.amdie.africa.network.models.authentication.checksms.CheckSMSData;
import com.mobiblanc.amdie.africa.network.models.authentication.completeregistraion.CompleteRegistrationData;
import com.mobiblanc.amdie.africa.network.models.authentication.sendsms.SendSMSData;
import com.mobiblanc.amdie.africa.network.utilities.Resource;

public class AuthenticationRepository {

    public void sendSMS(String msisdn, String lang, String uid, MutableLiveData<Resource<SendSMSData>> mutableLiveData) {
        new ApiManager().sendSMS(msisdn, lang, uid, mutableLiveData);
    }

    public void loginWithLinkedin(String code, String lang, MutableLiveData<Resource<CheckSMSData>> mutableLiveData) {
        new ApiManager().loginWithLinkedin(code, lang, mutableLiveData);
    }

    public void checkSMS(String msisdn, String code, String firebaseToken, String lang, MutableLiveData<Resource<CheckSMSData>> mutableLiveData) {
        new ApiManager().checkSMS(msisdn, code, firebaseToken, lang, mutableLiveData);
    }

    public void sendOTPByEmail(String msisdn, String lang, String uid, MutableLiveData<Resource<SendSMSData>> mutableLiveData) {
        new ApiManager().sendOTPByEmail(msisdn, lang, uid, mutableLiveData);
    }

    public void checkOTPByEmail(String msisdn, String code, String firebaseToken, String lang, MutableLiveData<Resource<CheckSMSData>> mutableLiveData) {
        new ApiManager().checkOTPByEmail(msisdn, code, firebaseToken, lang, mutableLiveData);
    }

    public void completeRegistration(String token,
                                     String gender,
                                     String lastName,
                                     String company,
                                     String job,
                                     String email,
                                     String firstName,
                                     int country,
                                     int city,
                                     int nationality,
                                     String firebaseToken,
                                     String code,
                                     String phoneNumber,
                                     String otherJob,
                                     MutableLiveData<Resource<CompleteRegistrationData>> mutableLiveData) {
        new ApiManager().completeRegistration(token, gender, lastName, company, job, email, firstName, country, city, nationality, firebaseToken, code, phoneNumber, otherJob, mutableLiveData);
    }
}
