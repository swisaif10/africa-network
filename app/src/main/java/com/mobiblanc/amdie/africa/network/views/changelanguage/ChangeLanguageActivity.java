package com.mobiblanc.amdie.africa.network.views.changelanguage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.databinding.DataBindingUtil;

import com.mobiblanc.amdie.africa.network.BuildConfig;
import com.mobiblanc.amdie.africa.network.R;
import com.mobiblanc.amdie.africa.network.databinding.ActivityChangeLanguageBinding;
import com.mobiblanc.amdie.africa.network.datamanager.sharedpref.PreferenceManager;
import com.mobiblanc.amdie.africa.network.utilities.Constants;
import com.mobiblanc.amdie.africa.network.views.base.BaseActivity;
import com.mobiblanc.amdie.africa.network.views.home.HomeActivity;

public class ChangeLanguageActivity extends BaseActivity {

    private ActivityChangeLanguageBinding activityBinding;
    private PreferenceManager preferenceManager;
    private String lang;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityBinding = DataBindingUtil.setContentView(this, R.layout.activity_change_language);

        preferenceManager = new PreferenceManager.Builder(this, Context.MODE_PRIVATE)
                .name(BuildConfig.APPLICATION_ID)
                .build();

        init();
    }

    private void init() {
        preferenceManager.putValue(Constants.FIRST_LAUNCH, false);

        lang = "fr";

        handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(this::moveToNextActivity, 3000);

        activityBinding.toggle.setOnToggleChanged((toggleStatus, booleanToggleStatus, toggleIntValue) -> {
            switch (toggleStatus) {
                case off:
                    lang = "fr";
                    break;
                case on:
                    lang = "en";
                    break;
            }
            new Handler(Looper.getMainLooper()).postDelayed(this::moveToNextActivity, 200);
        });

        activityBinding.frenchBtn.setOnClickListener(v -> {
            activityBinding.toggle.setToggleOff();
            lang = "fr";
            new Handler(Looper.getMainLooper()).postDelayed(this::moveToNextActivity, 200);

        });
        activityBinding.englishBtn.setOnClickListener(v -> {
            activityBinding.toggle.setToggleOn();
            lang = "en";
            new Handler(Looper.getMainLooper()).postDelayed(this::moveToNextActivity, 200);
        });
    }

    private void moveToNextActivity() {
        handler.removeCallbacksAndMessages(null);
        preferenceManager.putValue(Constants.LANGUAGE, lang);
        startActivity(new Intent(ChangeLanguageActivity.this, HomeActivity.class));
        finish();
    }
}