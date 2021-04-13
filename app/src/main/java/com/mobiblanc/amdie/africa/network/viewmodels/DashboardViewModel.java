package com.mobiblanc.amdie.africa.network.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.mobiblanc.amdie.africa.network.Utilities.Resource;
import com.mobiblanc.amdie.africa.network.models.feed.GetFeedData;
import com.mobiblanc.amdie.africa.network.models.menu.MenuData;
import com.mobiblanc.amdie.africa.network.repository.DashboardRepository;

public class DashboardViewModel extends AndroidViewModel {

    private final DashboardRepository repository;
    private final MutableLiveData<Resource<GetFeedData>> getFeedsLiveData;
    private final MutableLiveData<Resource<MenuData>> getMenuLiveData;

    public DashboardViewModel(@NonNull Application application) {
        super(application);

        this.repository = new DashboardRepository();
        getFeedsLiveData = new MutableLiveData<>();
        getMenuLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<Resource<GetFeedData>> getGetFeedsLiveData() {
        return getFeedsLiveData;
    }

    public MutableLiveData<Resource<MenuData>> getGetMenuLiveData() {
        return getMenuLiveData;
    }

    public void getFeeds(String token,
                         String sectors,
                         String type,
                         String date,
                         String lang) {
        repository.getFeeds(token, sectors, type, date, lang, getFeedsLiveData);
    }

    public void getMenu(String token,
                        String lang) {
        repository.getMenu(token, lang, getMenuLiveData);
    }
}
