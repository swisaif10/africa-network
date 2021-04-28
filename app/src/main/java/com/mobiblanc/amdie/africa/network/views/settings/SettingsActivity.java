package com.mobiblanc.amdie.africa.network.views.settings;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import com.mobiblanc.amdie.africa.network.BuildConfig;
import com.mobiblanc.amdie.africa.network.R;
import com.mobiblanc.amdie.africa.network.Utilities.Constants;
import com.mobiblanc.amdie.africa.network.databinding.ActivitySettingsBinding;
import com.mobiblanc.amdie.africa.network.datamanager.sharedpref.PreferenceManager;

public class SettingsActivity extends AppCompatActivity {

    private ActivitySettingsBinding activityBinding;
    private PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityBinding = DataBindingUtil.setContentView(this, R.layout.activity_settings);

        preferenceManager = new PreferenceManager.Builder(this, Context.MODE_PRIVATE)
                .name(BuildConfig.APPLICATION_ID)
                .build();

        init();
    }

    private void init() {
        activityBinding.backBtn.setOnClickListener(v -> onBackPressed());

        if (preferenceManager.getValue(Constants.NOTIFICATION, true)) {
            activityBinding.notifToggle.setToggleOn();
        } else {
            activityBinding.notifToggle.setToggleOff();
        }

        activityBinding.notifToggle.setOnToggleChanged((toggleStatus, booleanToggleStatus, toggleIntValue) -> preferenceManager.putValue(Constants.NOTIFICATION, booleanToggleStatus));

        if (preferenceManager.getValueS(Constants.LANGUAGE, getText(R.string.french_label).toString()).equals(getText(R.string.french_label).toString())) {
            activityBinding.firstLanguageChoiceBtn.setText(getText(R.string.french_label));
            activityBinding.secondLanguageChoiceBtn.setText(getText(R.string.english_label));
        } else {
            activityBinding.firstLanguageChoiceBtn.setText(getText(R.string.english_label));
            activityBinding.secondLanguageChoiceBtn.setText(getText(R.string.french_label));
        }

        activityBinding.firstLanguageChoiceBtn.setOnClickListener(v -> {
            if (activityBinding.secondLanguageChoiceBtn.getVisibility() == View.GONE) {
                activityBinding.separator.setVisibility(View.VISIBLE);
                activityBinding.secondLanguageChoiceBtn.setVisibility(View.VISIBLE);
                activityBinding.firstLanguageChoiceBtn.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(this, R.drawable.arrow_up), null);
            } else {
                activityBinding.separator.setVisibility(View.GONE);
                activityBinding.secondLanguageChoiceBtn.setVisibility(View.GONE);
                activityBinding.firstLanguageChoiceBtn.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(this, R.drawable.arrow_down), null);
            }
        });

        activityBinding.secondLanguageChoiceBtn.setOnClickListener(v -> {
            preferenceManager.putValue(Constants.LANGUAGE, activityBinding.secondLanguageChoiceBtn.getText().toString());
            activityBinding.firstLanguageChoiceBtn.setText(activityBinding.secondLanguageChoiceBtn.getText().toString());
            activityBinding.secondLanguageChoiceBtn.setText(activityBinding.firstLanguageChoiceBtn.getText().toString());
            activityBinding.separator.setVisibility(View.GONE);
            activityBinding.secondLanguageChoiceBtn.setVisibility(View.GONE);
            activityBinding.firstLanguageChoiceBtn.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(this, R.drawable.arrow_down), null);
        });
    }
}