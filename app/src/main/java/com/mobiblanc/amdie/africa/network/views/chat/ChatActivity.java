package com.mobiblanc.amdie.africa.network.views.chat;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.mobiblanc.amdie.africa.network.R;
import com.mobiblanc.amdie.africa.network.databinding.ActivityChatBinding;
import com.mobiblanc.amdie.africa.network.views.dashboard.messages.ChatAdapter;

public class ChatActivity extends AppCompatActivity {

    private ActivityChatBinding activityBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityBinding = DataBindingUtil.setContentView(this, R.layout.activity_chat);

        init();
    }

    private void init() {
        activityBinding.backBtn.setOnClickListener(v -> onBackPressed());
        activityBinding.chatRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        activityBinding.chatRecycler.setAdapter(new ChatAdapter(this));
    }
}