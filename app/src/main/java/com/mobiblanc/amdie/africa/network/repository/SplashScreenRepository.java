package com.mobiblanc.amdie.africa.network.repository;

import androidx.lifecycle.MutableLiveData;

import com.mobiblanc.amdie.africa.network.datamanager.ApiManager;
import com.mobiblanc.amdie.africa.network.models.checkversion.CheckVersionData;
import com.mobiblanc.amdie.africa.network.utilities.Resource;

public class SplashScreenRepository {

    public void checkVersion(MutableLiveData<Resource<CheckVersionData>> mutableLiveData) {
        new ApiManager().checkVersion(mutableLiveData);
    }
}
