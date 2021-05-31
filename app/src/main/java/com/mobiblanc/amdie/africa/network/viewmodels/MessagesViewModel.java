package com.mobiblanc.amdie.africa.network.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.mobiblanc.amdie.africa.network.models.messaging.discussions.DiscussionsListData;
import com.mobiblanc.amdie.africa.network.models.messaging.messages.MessagesListData;
import com.mobiblanc.amdie.africa.network.models.messaging.sending.SendMessageData;
import com.mobiblanc.amdie.africa.network.repository.MessagesRepository;
import com.mobiblanc.amdie.africa.network.utilities.Resource;

public class MessagesViewModel extends AndroidViewModel {

    private final MessagesRepository repository;
    private final MutableLiveData<Resource<DiscussionsListData>> discussionsListLiveData;
    private final MutableLiveData<Resource<MessagesListData>> messagesListLiveData;
    private final MutableLiveData<Resource<SendMessageData>> sendMessageLiveData;

    public MessagesViewModel(@NonNull Application application) {
        super(application);

        this.repository = new MessagesRepository();
        discussionsListLiveData = new MutableLiveData<>();
        messagesListLiveData = new MutableLiveData<>();
        sendMessageLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<Resource<DiscussionsListData>> getDiscussionsListLiveData() {
        return discussionsListLiveData;
    }

    public MutableLiveData<Resource<MessagesListData>> getMessagesListLiveData() {
        return messagesListLiveData;
    }

    public MutableLiveData<Resource<SendMessageData>> getSendMessageLiveData() {
        return sendMessageLiveData;
    }

    public void getDiscussionsList(String token, String lang) {
        repository.getDiscussionsList(token, lang, discussionsListLiveData);
    }

    public void getMessagesList(String token, int receiver, String lang) {
        repository.getMessagesList(token, receiver, lang, messagesListLiveData);
    }

    public void sendMessage(String token, int receiver, String message, String lang) {
        repository.sendMessage(token, receiver, message, lang, sendMessageLiveData);
    }
}
