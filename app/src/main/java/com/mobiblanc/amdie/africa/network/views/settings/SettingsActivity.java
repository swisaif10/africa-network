package com.mobiblanc.amdie.africa.network.views.settings;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.mobiblanc.amdie.africa.network.BuildConfig;
import com.mobiblanc.amdie.africa.network.R;
import com.mobiblanc.amdie.africa.network.Utilities.Constants;
import com.mobiblanc.amdie.africa.network.databinding.ActivitySettingsBinding;
import com.mobiblanc.amdie.africa.network.datamanager.sharedpref.PreferenceManager;


public class SettingsActivity extends AppCompatActivity {

    String lang = "fr";
    private ActivitySettingsBinding activityBinding;
    private PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityBinding = DataBindingUtil.setContentView(this, R.layout.activity_settings);

        preferenceManager = new PreferenceManager.Builder(this, Context.MODE_PRIVATE)
                .name(BuildConfig.APPLICATION_ID)
                .build();


        try {
            preferenceManager.getValueB(Constants.NOTIFICATION, true);
        } catch (Exception e) {
            preferenceManager.putValue(Constants.NOTIFICATION, true);
        }
        init();
    }

    private void init() {
        activityBinding.backBtn.setOnClickListener(v ->
                onBackPressed());
        if (preferenceManager.getValueB(Constants.NOTIFICATION, true)) {
            activityBinding.imgSw.setImageDrawable(getDrawable(R.drawable.actif));
        } else {
            activityBinding.imgSw.setImageDrawable(getDrawable(R.drawable.inactif));
        }
        activityBinding.imgSw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preferenceManager.putValue(Constants.NOTIFICATION, !preferenceManager.getValueB(Constants.NOTIFICATION, true));
                if (preferenceManager.getValueB(Constants.NOTIFICATION, true)) {
                    activityBinding.imgSw.setImageDrawable(getDrawable(R.drawable.actif));
                } else {
                    activityBinding.imgSw.setImageDrawable(getDrawable(R.drawable.inactif));
                }
            }
        });
        if (preferenceManager.getValueS(Constants.LANGUAGE, "fr").equals("fr")) {
            lang = "fr";
            activityBinding.btn1.setText(getText(R.string.fr));
            activityBinding.btn2.setText(getText(R.string.ang));
        } else {
            lang = "ang";
            activityBinding.btn2.setText(getText(R.string.fr));
            activityBinding.btn1.setText(getText(R.string.ang));
        }

        activityBinding.btn1.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onClick(View v) {
                if (activityBinding.btn2.getVisibility() == View.GONE) {
                    activityBinding.btn2.setVisibility(View.VISIBLE);
                    activityBinding.bar.setVisibility(View.VISIBLE);
                    activityBinding.btn1.setCompoundDrawablesWithIntrinsicBounds(null, null, getDrawable(R.drawable.arrow_up), null);
                    if (getText(R.string.ang).equals(activityBinding.btn1.getText().toString())) {
                        preferenceManager.putValue(Constants.LANGUAGE, "ang");
                    } else {
                        preferenceManager.putValue(Constants.LANGUAGE, "fr");
                    }
                } else {
                    activityBinding.btn2.setVisibility(View.GONE);
                    activityBinding.bar.setVisibility(View.GONE);
                    activityBinding.btn1.setCompoundDrawablesWithIntrinsicBounds(null, null, getDrawable(R.drawable.arrow_down), null);
                }
            }
        });

        activityBinding.btn2.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onClick(View v) {
                if (getText(R.string.ang).equals(activityBinding.btn2.getText().toString())) {
                    preferenceManager.putValue(Constants.LANGUAGE, "ang");
                } else {
                    preferenceManager.putValue(Constants.LANGUAGE, "fr");
                }

                String lang = activityBinding.btn1.getText().toString();
                activityBinding.btn2.setVisibility(View.GONE);
                activityBinding.bar.setVisibility(View.GONE);
                activityBinding.btn1.setCompoundDrawablesWithIntrinsicBounds(null, null, getDrawable(R.drawable.arrow_down), null);
                activityBinding.btn1.setText(activityBinding.btn2.getText().toString());
                activityBinding.btn2.setText(lang);


            }
        });

    }


}