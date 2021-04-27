package com.mobiblanc.amdie.africa.network.views.home;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.mobiblanc.amdie.africa.network.R;
import com.mobiblanc.amdie.africa.network.databinding.ActivityHomeBinding;
import com.mobiblanc.amdie.africa.network.views.authentication.AuthenticationActivity;

public class HomeActivity extends AppCompatActivity {

    private ActivityHomeBinding activityBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityBinding = DataBindingUtil.setContentView(this, R.layout.activity_home);

        init();
    }

    private void init() {
        activityBinding.tabDots.setupWithViewPager(activityBinding.pager);
        activityBinding.pager.setAdapter(new HomePagerAdapter());

        activityBinding.startBtn.setOnClickListener(v -> startActivity(new Intent(HomeActivity.this, AuthenticationActivity.class)));
    }
}