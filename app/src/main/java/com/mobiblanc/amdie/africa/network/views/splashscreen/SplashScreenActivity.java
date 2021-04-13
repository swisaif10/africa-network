package com.mobiblanc.amdie.africa.network.views.splashscreen;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.mobiblanc.amdie.africa.network.BuildConfig;
import com.mobiblanc.amdie.africa.network.R;
import com.mobiblanc.amdie.africa.network.Utilities.Constants;
import com.mobiblanc.amdie.africa.network.Utilities.Resource;
import com.mobiblanc.amdie.africa.network.Utilities.Utilities;
import com.mobiblanc.amdie.africa.network.datamanager.sharedpref.PreferenceManager;
import com.mobiblanc.amdie.africa.network.listeners.OnDialogButtonsClickListener;
import com.mobiblanc.amdie.africa.network.models.checkversion.CheckVersionData;
import com.mobiblanc.amdie.africa.network.viewmodels.SplashScreenViewModel;
import com.mobiblanc.amdie.africa.network.views.changelanguage.ChangeLanguageActivity;
import com.mobiblanc.amdie.africa.network.views.dashboard.DashboardActivity;

public class SplashScreenActivity extends AppCompatActivity {

    private SplashScreenViewModel viewModel;
    private PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        viewModel = ViewModelProviders.of(this).get(SplashScreenViewModel.class);
        viewModel.getCheckVersionLiveData().observe(this, this::handleCheckVersionData);

        preferenceManager = new PreferenceManager.Builder(this, Context.MODE_PRIVATE)
                .name(BuildConfig.APPLICATION_ID)
                .build();

        new Handler(Looper.getMainLooper()).postDelayed(this::checkVersion, 2000);

    }

    private void checkVersion() {
        viewModel.checkVersion();
    }

    private void handleCheckVersionData(Resource<CheckVersionData> responseData) {
        switch (responseData.status) {
            case SUCCESS:
                String appStatus = responseData.data.getHeader().getStatus();
                switch (appStatus) {
                    case "ok":
                        movToNextActivity();
                        break;
                    case "blocked":
                    case "update":
                        Utilities.showUpdateDialog(this, responseData.data.getHeader().getMessage(), appStatus, new OnDialogButtonsClickListener() {
                            @Override
                            public void firstChoice() {
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(responseData.data.getResponse().getUrl())));
                            }

                            @Override
                            public void secondChoice() {
                                movToNextActivity();
                            }
                        });
                        break;
                }
                break;
            case LOADING:
                break;
            case ERROR:
                Utilities.showErrorPopup(this, responseData.message);
                break;
        }
    }

    private void movToNextActivity() {
        Intent intent;
        if (preferenceManager.getValue(Constants.TOKEN, "").equalsIgnoreCase(""))
            intent = new Intent(SplashScreenActivity.this, ChangeLanguageActivity.class);
        else
            intent = new Intent(SplashScreenActivity.this, DashboardActivity.class);
        startActivity(intent);
        finish();
    }
}