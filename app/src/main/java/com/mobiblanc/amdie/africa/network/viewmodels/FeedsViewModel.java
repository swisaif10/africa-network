package com.mobiblanc.amdie.africa.network.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.mobiblanc.amdie.africa.network.models.feed.GetFeedData;
import com.mobiblanc.amdie.africa.network.models.like.LikeFeedData;
import com.mobiblanc.amdie.africa.network.models.logout.LogoutData;
import com.mobiblanc.amdie.africa.network.models.menu.MenuData;
import com.mobiblanc.amdie.africa.network.models.share.ShareAppData;
import com.mobiblanc.amdie.africa.network.repository.DashboardRepository;
import com.mobiblanc.amdie.africa.network.repository.FeedsRepository;
import com.mobiblanc.amdie.africa.network.utilities.Resource;

public class FeedsViewModel extends AndroidViewModel {

    private final FeedsRepository repository;
    private final MutableLiveData<Resource<GetFeedData>> feedsLiveData;
    private final MutableLiveData<Resource<LikeFeedData>> likeFeedLiveData;

    public FeedsViewModel(@NonNull Application application) {
        super(application);

        this.repository = new FeedsRepository();
        feedsLiveData = new MutableLiveData<>();
        likeFeedLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<Resource<GetFeedData>> getFeedsLiveData() {
        return feedsLiveData;
    }

    public MutableLiveData<Resource<LikeFeedData>> getLikeFeedLiveData() {
        return likeFeedLiveData;
    }

    public void getFeeds(String token,
                         String sectors,
                         String type,
                         String date,
                         Boolean mostLiked,
                         int offset,
                         String lang) {
        repository.getFeeds(token, sectors, type, date, mostLiked, offset, lang, feedsLiveData);
    }

    public void likeFeed(String token,
                         int id) {
        repository.likeFeed(token, id, likeFeedLiveData);
    }
}
