package com.mobiblanc.amdie.africa.network.views.home;

import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import com.mobiblanc.amdie.africa.network.R;
import com.mobiblanc.amdie.africa.network.databinding.ActivityHomeBinding;
import com.mobiblanc.amdie.africa.network.models.pager.HomePagerItem;
import com.mobiblanc.amdie.africa.network.views.authentication.AuthenticationActivity;
import com.mobiblanc.amdie.africa.network.views.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends BaseActivity {

    private ActivityHomeBinding activityBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityBinding = DataBindingUtil.setContentView(this, R.layout.activity_home);

        init();
    }

    private void init() {
        activityBinding.tabDots.setupWithViewPager(activityBinding.pager);
        List<HomePagerItem> itemList = new ArrayList<HomePagerItem>() {{
            add(new HomePagerItem(0, getString(R.string.home_pager_body_1)));
            add(new HomePagerItem(1, getString(R.string.home_pager_body_2)));
        }};
        activityBinding.pager.setAdapter(new HomePagerAdapter(itemList));

        activityBinding.startBtn.setOnClickListener(v -> startActivity(new Intent(HomeActivity.this, AuthenticationActivity.class)));
    }
}