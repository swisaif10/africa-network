package com.mobiblanc.amdie.africa.network.repository;

import androidx.lifecycle.MutableLiveData;

import com.mobiblanc.amdie.africa.network.Utilities.Resource;
import com.mobiblanc.amdie.africa.network.datamanager.ApiManager;
import com.mobiblanc.amdie.africa.network.models.messaging.discussions.DiscussionsListData;
import com.mobiblanc.amdie.africa.network.models.messaging.messages.MessagesListData;
import com.mobiblanc.amdie.africa.network.models.messaging.sending.SendMessageData;

public class MessagesRepository {

    public void getDiscussionsList(String token,
                                   String lang,
                                   MutableLiveData<Resource<DiscussionsListData>> mutableLiveData) {
        new ApiManager().getDiscussionsList(token, lang, mutableLiveData);
    }

    public void getMessagesList(String token,
                                int receiver,
                                String lang,
                                MutableLiveData<Resource<MessagesListData>> mutableLiveData) {
        new ApiManager().getMessagesList(token, receiver, lang, mutableLiveData);
    }

    public void sendMessage(String token,
                            int receiver,
                            String message,
                            String lang,
                            MutableLiveData<Resource<SendMessageData>> mutableLiveData) {
        new ApiManager().sendMessage(token, receiver, message, lang, mutableLiveData);
    }
}
