package com.mobiblanc.amdie.africa.network.views.authentication;

import android.os.Bundle;

import androidx.lifecycle.ViewModelProviders;

import com.mobiblanc.amdie.africa.network.R;
import com.mobiblanc.amdie.africa.network.viewmodels.AuthenticationViewModel;
import com.mobiblanc.amdie.africa.network.views.authentication.mobileregister.MobileRegisterFragment;
import com.mobiblanc.amdie.africa.network.views.base.BaseActivity;

public class AuthenticationActivity extends BaseActivity {

    private AuthenticationViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

        viewModel = ViewModelProviders.of(this).get(AuthenticationViewModel.class);

        replaceFragment(new MobileRegisterFragment(), "");
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1)
            super.onBackPressed();
        else
            finish();
    }

    public AuthenticationViewModel getViewModel() {
        return viewModel;
    }
}