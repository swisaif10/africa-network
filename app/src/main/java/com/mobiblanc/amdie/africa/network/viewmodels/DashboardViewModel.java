package com.mobiblanc.amdie.africa.network.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.mobiblanc.amdie.africa.network.Utilities.Resource;
import com.mobiblanc.amdie.africa.network.models.feed.GetFeedData;
import com.mobiblanc.amdie.africa.network.models.like.LikeFeedData;
import com.mobiblanc.amdie.africa.network.models.logout.LogoutData;
import com.mobiblanc.amdie.africa.network.models.menu.MenuData;
import com.mobiblanc.amdie.africa.network.models.share.ShareAppData;
import com.mobiblanc.amdie.africa.network.repository.DashboardRepository;

public class DashboardViewModel extends AndroidViewModel {

    private final DashboardRepository repository;
    private final MutableLiveData<Resource<GetFeedData>> feedsLiveData;
    private final MutableLiveData<Resource<MenuData>> menuLiveData;
    private final MutableLiveData<Resource<ShareAppData>> shareAppLiveData;
    private final MutableLiveData<Resource<LogoutData>> logoutLiveData;
    private final MutableLiveData<Resource<LikeFeedData>> likeFeedLiveData;

    public DashboardViewModel(@NonNull Application application) {
        super(application);

        this.repository = new DashboardRepository();
        feedsLiveData = new MutableLiveData<>();
        menuLiveData = new MutableLiveData<>();
        shareAppLiveData = new MutableLiveData<>();
        logoutLiveData = new MutableLiveData<>();
        likeFeedLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<Resource<GetFeedData>> getFeedsLiveData() {
        return feedsLiveData;
    }

    public MutableLiveData<Resource<MenuData>> getMenuLiveData() {
        return menuLiveData;
    }

    public MutableLiveData<Resource<ShareAppData>> getShareAppLiveData() {
        return shareAppLiveData;
    }

    public MutableLiveData<Resource<LogoutData>> getLogoutLiveData() {
        return logoutLiveData;
    }

    public MutableLiveData<Resource<LikeFeedData>> getLikeFeedLiveData() {
        return likeFeedLiveData;
    }

    public void getFeeds(String token,
                         String sectors,
                         String type,
                         String date,
                         String lang) {
        repository.getFeeds(token, sectors, type, date, lang, feedsLiveData);
    }

    public void getMenu(String token,
                        String lang) {
        repository.getMenu(token, lang, menuLiveData);
    }

    public void getShareLink(String token,
                             String lang) {
        repository.getShareLink(token, lang, shareAppLiveData);
    }

    public void logout(String token,
                       String lang) {
        repository.logout(token, lang, logoutLiveData);
    }

    public void likeFeed(String token,
                       int id) {
        repository.likeFeed(token, id, likeFeedLiveData);
    }
}
