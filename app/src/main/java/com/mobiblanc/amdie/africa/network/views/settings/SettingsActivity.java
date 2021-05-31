package com.mobiblanc.amdie.africa.network.views.settings;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.view.View;

import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import com.mobiblanc.amdie.africa.network.BuildConfig;
import com.mobiblanc.amdie.africa.network.R;
import com.mobiblanc.amdie.africa.network.databinding.ActivitySettingsBinding;
import com.mobiblanc.amdie.africa.network.datamanager.sharedpref.PreferenceManager;
import com.mobiblanc.amdie.africa.network.utilities.Constants;
import com.mobiblanc.amdie.africa.network.utilities.Utilities;
import com.mobiblanc.amdie.africa.network.views.base.BaseActivity;
import com.mobiblanc.amdie.africa.network.views.dashboard.DashboardActivity;

public class SettingsActivity extends BaseActivity {

    private ActivitySettingsBinding activityBinding;
    private PreferenceManager preferenceManager;
    private String firstChoice;
    private String secondChoice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityBinding = DataBindingUtil.setContentView(this, R.layout.activity_settings);

        preferenceManager = new PreferenceManager.Builder(this, Context.MODE_PRIVATE)
                .name(BuildConfig.APPLICATION_ID)
                .build();

        init();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (areNotificationsEnabled())
            activityBinding.notifToggle.setToggleOn();
        else
            activityBinding.notifToggle.setToggleOff();
    }

    private void init() {
        activityBinding.backBtn.setOnClickListener(v -> onBackPressed());

        activityBinding.notifBtn.setOnClickListener(v -> Utilities.showNotificationPermissionDialog(SettingsActivity.this, v1 -> {
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.setData(Uri.parse("package:" + getPackageName()));
            startActivity(intent);
        }));

        if (preferenceManager.getValue(Constants.LANGUAGE, "fr").equals("fr")) {
            activityBinding.firstLanguageChoiceBtn.setText(getText(R.string.french_label));
            activityBinding.secondLanguageChoiceBtn.setText(getText(R.string.english_label));
            firstChoice = "fr";
            secondChoice = "en";
        } else {
            activityBinding.firstLanguageChoiceBtn.setText(getText(R.string.english_label));
            activityBinding.secondLanguageChoiceBtn.setText(getText(R.string.french_label));
            firstChoice = "en";
            secondChoice = "fr";
        }

        activityBinding.firstLanguageChoiceBtn.setOnClickListener(v -> {
            if (activityBinding.secondLanguageChoiceBtn.getVisibility() == View.GONE) {
                activityBinding.separator.setVisibility(View.VISIBLE);
                activityBinding.secondLanguageChoiceBtn.setVisibility(View.VISIBLE);
                activityBinding.firstLanguageChoiceBtn.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(this, R.drawable.arrow_up), null);
            } else {
                activityBinding.separator.setVisibility(View.GONE);
                activityBinding.secondLanguageChoiceBtn.setVisibility(View.GONE);
                activityBinding.firstLanguageChoiceBtn.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(this, R.drawable.ic_arrow), null);
            }
        });

        activityBinding.secondLanguageChoiceBtn.setOnClickListener(v -> {
            preferenceManager.putValue(Constants.LANGUAGE, secondChoice);
            String x = firstChoice;
            firstChoice = secondChoice;
            secondChoice = x;
            activityBinding.firstLanguageChoiceBtn.setText(activityBinding.secondLanguageChoiceBtn.getText().toString());
            activityBinding.secondLanguageChoiceBtn.setText(activityBinding.firstLanguageChoiceBtn.getText().toString());
            activityBinding.separator.setVisibility(View.GONE);
            activityBinding.secondLanguageChoiceBtn.setVisibility(View.GONE);
            activityBinding.firstLanguageChoiceBtn.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(this, R.drawable.ic_arrow), null);
            new Handler(Looper.getMainLooper()).postDelayed(this::refreshAppLang, 200);
        });
    }

    private boolean areNotificationsEnabled() {
        return NotificationManagerCompat.from(this).areNotificationsEnabled();
    }

    private void refreshAppLang() {
        startActivity(new Intent(this, DashboardActivity.class));
        finishAffinity();
    }
}