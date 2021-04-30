package com.mobiblanc.amdie.africa.network.views.chat;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;

import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.mobiblanc.amdie.africa.network.BuildConfig;
import com.mobiblanc.amdie.africa.network.R;
import com.mobiblanc.amdie.africa.network.utilities.Constants;
import com.mobiblanc.amdie.africa.network.utilities.Resource;
import com.mobiblanc.amdie.africa.network.utilities.Utilities;
import com.mobiblanc.amdie.africa.network.databinding.ActivityChatBinding;
import com.mobiblanc.amdie.africa.network.datamanager.sharedpref.PreferenceManager;
import com.mobiblanc.amdie.africa.network.models.messaging.messages.Message;
import com.mobiblanc.amdie.africa.network.models.messaging.messages.MessagesListData;
import com.mobiblanc.amdie.africa.network.models.messaging.sending.SendMessageData;
import com.mobiblanc.amdie.africa.network.viewmodels.MessagesViewModel;
import com.mobiblanc.amdie.africa.network.views.base.BaseActivity;
import com.mobiblanc.amdie.africa.network.views.dashboard.messages.ChatAdapter;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends BaseActivity {

    private ActivityChatBinding activityBinding;
    private int id;
    private String username;
    private String picture;
    private List<Message> messages;
    private ChatAdapter chatAdapter;
    private MessagesViewModel viewModel;
    private PreferenceManager preferenceManager;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityBinding = DataBindingUtil.setContentView(this, R.layout.activity_chat);

        if (getIntent().hasExtra("id")) {
            id = getIntent().getIntExtra("id", -1);
            username = getIntent().getStringExtra("username");
            picture = getIntent().getStringExtra("picture");
        }

        viewModel = ViewModelProviders.of(this).get(MessagesViewModel.class);
        viewModel.getMessagesListLiveData().observe(this, this::handleMessagesListData);
        viewModel.getSendMessageLiveData().observe(this, this::handleSendMessageData);

        preferenceManager = new PreferenceManager.Builder(this, Context.MODE_PRIVATE)
                .name(BuildConfig.APPLICATION_ID)
                .build();

        handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                getMessagesList();
                handler.postDelayed(this, 5000);
            }
        });

        init();
        getMessagesList();
    }

    private void init() {
        Glide.with(this).load(picture).into(activityBinding.icon);
        activityBinding.name.setText(username);
        activityBinding.backBtn.setOnClickListener(v -> {
            handler.removeCallbacksAndMessages(null);
            onBackPressed();
        });
        activityBinding.sendBtn.setOnClickListener(v -> {
            if (!activityBinding.input.getText().toString().equalsIgnoreCase(""))
                sendMessage();
        });

        activityBinding.input.setFocusableInTouchMode(true);
        activityBinding.input.requestFocus();
        activityBinding.input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().equalsIgnoreCase(""))
                    activityBinding.sendBtn.setTextColor(ContextCompat.getColor(ChatActivity.this, R.color.blue5));
                else
                    activityBinding.sendBtn.setTextColor(ContextCompat.getColor(ChatActivity.this, R.color.blue4));
            }
        });

        messages = new ArrayList<>();
        activityBinding.chatRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        chatAdapter = new ChatAdapter(this, messages, id, picture);
        activityBinding.chatRecycler.setAdapter(chatAdapter);
    }

    private void getMessagesList() {
        viewModel.getMessagesList(preferenceManager.getValue(Constants.TOKEN, ""), id, preferenceManager.getValue(Constants.LANGUAGE, "fr"));
    }

    private void handleMessagesListData(Resource<MessagesListData> responseData) {
        switch (responseData.status) {
            case SUCCESS:
                if (responseData.data.getResults().size() > messages.size())
                    initRecycler(responseData.data.getResults());
                break;
            case INVALID_TOKEN:
                break;
            case ERROR:
                Utilities.showErrorPopup(this, responseData.message);
                break;
        }
    }

    private void initRecycler(List<Message> messageList) {
        messages.clear();
        messages.addAll(messageList);
        chatAdapter.notifyDataSetChanged();

        activityBinding.chatRecycler.scrollToPosition(messages.size() - 1);
        activityBinding.chatRecycler.addOnLayoutChangeListener((v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom) -> {
            if (bottom < oldBottom) {
                activityBinding.chatRecycler.postDelayed(() -> activityBinding.chatRecycler.smoothScrollToPosition(chatAdapter.getItemCount()), 100);
            }
        });
    }

    private void sendMessage() {
        viewModel.sendMessage(preferenceManager.getValue(Constants.TOKEN, ""), id, activityBinding.input.getText().toString(), preferenceManager.getValue(Constants.LANGUAGE, "fr"));
        activityBinding.input.setText("");
    }

    private void handleSendMessageData(Resource<SendMessageData> responseData) {
        switch (responseData.status) {
            case SUCCESS:
                Message message = new Message(responseData.data.getResults().getDate(), responseData.data.getResults().getIdSender(), responseData.data.getResults().getMessage(), responseData.data.getResults().getUsername());
                chatAdapter.addMessage(message);
                activityBinding.chatRecycler.scrollToPosition(chatAdapter.getItemCount() - 1);
                break;
            case INVALID_TOKEN:
                Utilities.showErrorPopupWithCLick(this, responseData.data.getHeader().getMessage(), v -> {
                    preferenceManager.clearAll();
                    tokenExpired();
                });
                break;
            case ERROR:
                Utilities.showErrorPopup(this, responseData.message);
                break;
        }
    }
}