package com.mobiblanc.amdie.africa.network.repository;

import androidx.lifecycle.MutableLiveData;

import com.mobiblanc.amdie.africa.network.datamanager.ApiManager;
import com.mobiblanc.amdie.africa.network.models.cgu.CGUData;
import com.mobiblanc.amdie.africa.network.utilities.Resource;

public class CGURepository {

    public void getCGU(String token, String lang, MutableLiveData<Resource<CGUData>> mutableLiveData) {
        new ApiManager().getCGU(token, lang, mutableLiveData);
    }
}
