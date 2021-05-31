package com.mobiblanc.amdie.africa.network.views.cgu;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.mobiblanc.amdie.africa.network.BuildConfig;
import com.mobiblanc.amdie.africa.network.R;
import com.mobiblanc.amdie.africa.network.databinding.ActivityCguBinding;
import com.mobiblanc.amdie.africa.network.datamanager.sharedpref.PreferenceManager;
import com.mobiblanc.amdie.africa.network.models.cgu.CGUData;
import com.mobiblanc.amdie.africa.network.models.cgu.Results;
import com.mobiblanc.amdie.africa.network.utilities.Constants;
import com.mobiblanc.amdie.africa.network.utilities.Resource;
import com.mobiblanc.amdie.africa.network.utilities.Utilities;
import com.mobiblanc.amdie.africa.network.viewmodels.CGUViewModel;
import com.mobiblanc.amdie.africa.network.views.base.BaseActivity;

public class CGUActivity extends BaseActivity {

    private ActivityCguBinding activityBinding;
    private PreferenceManager preferenceManager;
    private CGUViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityBinding = DataBindingUtil.setContentView(this, R.layout.activity_cgu);

        preferenceManager = new PreferenceManager.Builder(this, Context.MODE_PRIVATE)
                .name(BuildConfig.APPLICATION_ID)
                .build();

        viewModel = ViewModelProviders.of(this).get(CGUViewModel.class);
        viewModel.getCguLiveData().observe(this, this::handleCGUData);

        getCGU();
    }

    private void getCGU() {
        activityBinding.loader.setVisibility(View.VISIBLE);
        viewModel.getCGU(preferenceManager.getValue(Constants.TOKEN, ""), preferenceManager.getValue(Constants.LANGUAGE, "fr"));
    }

    private void handleCGUData(Resource<CGUData> responseData) {
        activityBinding.loader.setVisibility(View.GONE);
        switch (responseData.status) {
            case SUCCESS:
                init(responseData.data.getResults());
                break;
            case INVALID_TOKEN:
                Utilities.showServerErrorDialog(this, responseData.data.getHeader().getMessage(), v -> {
                    preferenceManager.clearValue(Constants.TOKEN);
                    tokenExpired();
                });
                break;
            case ERROR:
                Utilities.showServerErrorDialog(this, responseData.message);
                break;
        }
    }

    private void init(Results results) {
        activityBinding.backBtn.setOnClickListener(v -> {
            onBackPressed();
            finish();
        });
        activityBinding.title.setText(results.getMessage());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            activityBinding.body.setText(Html.fromHtml(results.getHtmlContent(), Html.FROM_HTML_MODE_LEGACY));
        } else {
            activityBinding.body.setText(Html.fromHtml(results.getHtmlContent()));
        }
    }
}