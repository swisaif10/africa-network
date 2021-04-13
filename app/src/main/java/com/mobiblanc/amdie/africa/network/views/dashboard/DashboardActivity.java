package com.mobiblanc.amdie.africa.network.views.dashboard;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.mobiblanc.amdie.africa.network.BuildConfig;
import com.mobiblanc.amdie.africa.network.R;
import com.mobiblanc.amdie.africa.network.Utilities.Constants;
import com.mobiblanc.amdie.africa.network.Utilities.Resource;
import com.mobiblanc.amdie.africa.network.Utilities.Utilities;
import com.mobiblanc.amdie.africa.network.databinding.ActivityDashboardBinding;
import com.mobiblanc.amdie.africa.network.datamanager.sharedpref.PreferenceManager;
import com.mobiblanc.amdie.africa.network.models.menu.MenuData;
import com.mobiblanc.amdie.africa.network.models.menu.MenuItem;
import com.mobiblanc.amdie.africa.network.models.menu.Result;
import com.mobiblanc.amdie.africa.network.viewmodels.DashboardViewModel;
import com.mobiblanc.amdie.africa.network.views.authentication.AuthenticationActivity;
import com.mobiblanc.amdie.africa.network.views.base.BaseActivity;
import com.mobiblanc.amdie.africa.network.views.dashboard.contacts.ContactsFragment;
import com.mobiblanc.amdie.africa.network.views.dashboard.feed.FeedsFragment;
import com.mobiblanc.amdie.africa.network.views.dashboard.messages.MessagesListFragment;
import com.mobiblanc.amdie.africa.network.views.dashboard.search.SearchFragment;
import com.mobiblanc.amdie.africa.network.views.settings.SettingsActivity;

import java.util.ArrayList;
import java.util.List;

public class DashboardActivity extends BaseActivity {

    private ActivityDashboardBinding activityBinding;
    private DashboardViewModel viewModel;
    private PreferenceManager preferenceManager;

    public DashboardViewModel getViewModel() {
        return viewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityBinding = DataBindingUtil.setContentView(this, R.layout.activity_dashboard);

        viewModel = ViewModelProviders.of(this).get(DashboardViewModel.class);
        viewModel.getGetMenuLiveData().observe(this, this::handleGetMenuData);

        preferenceManager = new PreferenceManager.Builder(this, Context.MODE_PRIVATE)
                .name(BuildConfig.APPLICATION_ID)
                .build();

        initNavigationView();

        getMenu();
    }

    private void getMenu() {
        viewModel.getMenu(preferenceManager.getValue(Constants.TOKEN, ""), "fr");
    }

    private void handleGetMenuData(Resource<MenuData> responseData) {
        switch (responseData.status) {
            case SUCCESS:
                for (Result result : responseData.data.getResults()) {
                    if (result.getName().equals("Menu")) {
                        initTab(result.getMenuItems());
                    }
                }
                break;
            case LOADING:
                break;
            case ERROR:
                Utilities.showErrorPopup(this, responseData.message);
                break;
        }
    }

    private void initTab(List<MenuItem> menu) {

        ArrayList<Fragment> fragments = new ArrayList<>();

        for (int i = 0; i < menu.size(); i++) {
            TabLayout.Tab tab = activityBinding.tabLayout.newTab();
            View view = tab.getCustomView() == null ? LayoutInflater.from(activityBinding.tabLayout.getContext()).inflate(R.layout.tab_item_layout, null) : tab.getCustomView();
            if (tab.getCustomView() == null) {
                tab.setCustomView(view);
            }
            TextView tabTextView = view.findViewById(R.id.title);
            tabTextView.setText(menu.get(i).getName().replaceFirst(" ", "\n"));
            ImageView tabImageView = view.findViewById(R.id.icon);
            Glide.with(this).load(menu.get(i).getIcon()).into(tabImageView);
            switch (menu.get(i).getAction()) {
                case "action_feed":
                    fragments.add(new FeedsFragment());
                    break;
                case "action_recherche":
                    fragments.add(new SearchFragment());
                case "action_relation":
                    fragments.add(new ContactsFragment());
                    break;
                case "action_message":
                    fragments.add(new MessagesListFragment());
                    break;

            }
            activityBinding.tabLayout.addTab(tab);
        }

        activityBinding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                switchContent(fragments.get(position), false, false);
                activityBinding.title.setText(menu.get(position).getName());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                //int position = tab.getPosition();
                //replaceFragment(fragments.get(position));
                //activityBinding.title.setText(names.get(position).replace("\n"," "));
            }
        });

        addFragment(new FeedsFragment());
    }

    private void initNavigationView() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.menu);

        Menu menu = navigationView.getMenu();
        menu.add(0, 0, 0, "Feed").setIcon(R.drawable.ic_news);
        menu.add(0, 1, 1, "Critères de recherche").setIcon(R.drawable.ic_crit_res);
        menu.add(0, 2, 2, "Mise en relation").setIcon(R.drawable.ic_relation);
        menu.add(0, 3, 3, "Messages").setIcon(R.drawable.ic_messages);
        menu.add(0, 4, 4, "Partager application").setIcon(R.drawable.ic_share_1);
        menu.add(0, 5, 5, "Se déconnecter").setIcon(R.drawable.ic_logout);

        navigationView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case 0:
                case 1:
                case 2:
                case 3:
                    activityBinding.tabLayout.selectTab(activityBinding.tabLayout.getTabAt(item.getItemId()));
                    activityBinding.title.setText(item.getTitle());
                    break;
                case 4:
                    break;
                case 5:
                    startActivity(new Intent(DashboardActivity.this, AuthenticationActivity.class));
                    finish();
                    break;
            }
            drawer.closeDrawer(GravityCompat.START);
            return true;
        });

        View header = navigationView.getHeaderView(0);
        ImageView settingsBtn = header.findViewById(R.id.settingsBtn);
        settingsBtn.setOnClickListener(v -> {
            drawer.closeDrawer(GravityCompat.START);
            startActivity(new Intent(DashboardActivity.this, SettingsActivity.class));
        });
    }
}