package com.mobiblanc.amdie.africa.network.firebase;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.mobiblanc.amdie.africa.network.BuildConfig;
import com.mobiblanc.amdie.africa.network.datamanager.sharedpref.PreferenceManager;
import com.mobiblanc.amdie.africa.network.utilities.Constants;

public class FirebaseService extends FirebaseMessagingService {

    private static final String TAG = "FirebaseService";


    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {

        //Log.d(TAG, "From: " + remoteMessage.getFrom());
    }

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        Log.d(TAG, "onNewToken: firebase token has been changed : " + s);

        PreferenceManager preferenceManager = new PreferenceManager.Builder(this, Context.MODE_PRIVATE)
                .name(BuildConfig.APPLICATION_ID)
                .build();

        preferenceManager.putValue(Constants.FIREBASE_TOKEN, s);
    }
}
