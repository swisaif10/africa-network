package com.mobiblanc.amdie.africa.network.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.mobiblanc.amdie.africa.network.models.authentication.checksms.CheckSMSData;
import com.mobiblanc.amdie.africa.network.models.authentication.completeregistraion.CompleteRegistrationData;
import com.mobiblanc.amdie.africa.network.models.authentication.sendsms.SendSMSData;
import com.mobiblanc.amdie.africa.network.repository.AuthenticationRepository;
import com.mobiblanc.amdie.africa.network.utilities.Resource;

public class AuthenticationViewModel extends AndroidViewModel {

    private final AuthenticationRepository repository;
    private final MutableLiveData<Resource<SendSMSData>> sendSMSLiveData;
    private final MutableLiveData<Resource<SendSMSData>> sendOTPBYEmailLiveData;
    private final MutableLiveData<Resource<CheckSMSData>> LoginWithLinkedInLiveData;
    private final MutableLiveData<Resource<CheckSMSData>> checkSMSLiveData;
    private final MutableLiveData<Resource<CheckSMSData>> checkOTPByEmailLiveData;
    private final MutableLiveData<Resource<CompleteRegistrationData>> updateProfileLiveData;

    public AuthenticationViewModel(@NonNull Application application) {
        super(application);

        this.repository = new AuthenticationRepository();
        sendSMSLiveData = new MutableLiveData<>();
        checkSMSLiveData = new MutableLiveData<>();
        updateProfileLiveData = new MutableLiveData<>();
        sendOTPBYEmailLiveData = new MutableLiveData<>();
        checkOTPByEmailLiveData = new MutableLiveData<>();
        LoginWithLinkedInLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<Resource<SendSMSData>> getSendSMSLiveData() {
        return sendSMSLiveData;
    }

    public MutableLiveData<Resource<CheckSMSData>> getCheckSMSLiveData() {
        return checkSMSLiveData;
    }

    public MutableLiveData<Resource<CompleteRegistrationData>> getUpdateProfileLiveData() {
        return updateProfileLiveData;
    }

    public MutableLiveData<Resource<SendSMSData>> getSendOTPBYEmailLiveData() {
        return sendOTPBYEmailLiveData;
    }

    public MutableLiveData<Resource<CheckSMSData>> getCheckOTPByEmailLiveData() {
        return checkOTPByEmailLiveData;
    }

    public MutableLiveData<Resource<CheckSMSData>> getLoginWithLinkedInLiveData() {
        return LoginWithLinkedInLiveData;
    }

    public void sendSMS(String msisdn, String lang, String uid) {
        repository.sendSMS(msisdn, lang, uid, sendSMSLiveData);
    }

    public void checkSMS(String msisdn, String code, String firebaseToken, String lang) {
        repository.checkSMS(msisdn, code, firebaseToken, lang, checkSMSLiveData);
    }

    public void loginWithLinkedin(String code, String lang) {
        repository.loginWithLinkedin(code, lang, LoginWithLinkedInLiveData);
    }

    public void sendOTPByEmail(String msisdn, String lang, String uid) {
        repository.sendOTPByEmail(msisdn, lang, uid, sendOTPBYEmailLiveData);
    }

    public void checkOTPByEmail(String msisdn, String code, String firebaseToken, String lang) {
        repository.checkOTPByEmail(msisdn, code, firebaseToken, lang, checkOTPByEmailLiveData);
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
                                     String otherJob) {
        repository.completeRegistration(token, gender, lastName, company, job, email, firstName, country, city, nationality, firebaseToken, code, phoneNumber, otherJob, updateProfileLiveData);
    }
}
