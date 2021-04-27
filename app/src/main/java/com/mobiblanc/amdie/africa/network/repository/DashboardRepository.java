package com.mobiblanc.amdie.africa.network.repository;

import androidx.lifecycle.MutableLiveData;

import com.mobiblanc.amdie.africa.network.Utilities.Resource;
import com.mobiblanc.amdie.africa.network.datamanager.ApiManager;
import com.mobiblanc.amdie.africa.network.models.feed.GetFeedData;
import com.mobiblanc.amdie.africa.network.models.like.LikeFeedData;
import com.mobiblanc.amdie.africa.network.models.logout.LogoutData;
import com.mobiblanc.amdie.africa.network.models.menu.MenuData;
import com.mobiblanc.amdie.africa.network.models.share.ShareAppData;

public class DashboardRepository {

    public void getFeeds(String token,
                         String sectors,
                         String type,
                         String date,
                         String lang,
                         MutableLiveData<Resource<GetFeedData>> mutableLiveData) {
        new ApiManager().getFeeds(token, sectors, type, date, lang, mutableLiveData);
    }

    public void getMenu(String token,
                        String lang,
                        MutableLiveData<Resource<MenuData>> mutableLiveData) {
        new ApiManager().getMenu(token, lang, mutableLiveData);
    }

    public void getShareLink(String token,
                             String lang,
                             MutableLiveData<Resource<ShareAppData>> mutableLiveData) {
        new ApiManager().getShareLink(token, lang, mutableLiveData);
    }

    public void logout(String token,
                       String lang,
                       MutableLiveData<Resource<LogoutData>> mutableLiveData) {
        new ApiManager().logout(token, lang, mutableLiveData);
    }

    public void likeFeed(String token,
                         int id,
                         MutableLiveData<Resource<LikeFeedData>> mutableLiveData) {
        new ApiManager().likeFeed(token, id, mutableLiveData);
    }
}
