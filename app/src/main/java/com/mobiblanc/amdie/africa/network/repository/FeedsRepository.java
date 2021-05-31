package com.mobiblanc.amdie.africa.network.repository;

import androidx.lifecycle.MutableLiveData;

import com.mobiblanc.amdie.africa.network.datamanager.ApiManager;
import com.mobiblanc.amdie.africa.network.models.feed.GetFeedData;
import com.mobiblanc.amdie.africa.network.models.like.LikeFeedData;
import com.mobiblanc.amdie.africa.network.models.logout.LogoutData;
import com.mobiblanc.amdie.africa.network.models.menu.MenuData;
import com.mobiblanc.amdie.africa.network.models.share.ShareAppData;
import com.mobiblanc.amdie.africa.network.utilities.Resource;

public class FeedsRepository {

    public void getFeeds(String token,
                         String sectors,
                         String type,
                         String date,
                         Boolean mostLiked,
                         int offset,
                         String lang,
                         MutableLiveData<Resource<GetFeedData>> mutableLiveData) {
        new ApiManager().getFeeds(token, sectors, type, date, mostLiked, offset, lang, mutableLiveData);
    }

    public void likeFeed(String token,
                         int id,
                         MutableLiveData<Resource<LikeFeedData>> mutableLiveData) {
        new ApiManager().likeFeed(token, id, mutableLiveData);
    }
}
