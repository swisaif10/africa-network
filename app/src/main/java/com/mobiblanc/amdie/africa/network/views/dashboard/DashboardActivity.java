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
import com.mobiblanc.amdie.africa.network.databinding.ActivityDashboardBinding;
import com.mobiblanc.amdie.africa.network.datamanager.sharedpref.PreferenceManager;
import com.mobiblanc.amdie.africa.network.models.logout.LogoutData;
import com.mobiblanc.amdie.africa.network.models.menu.MenuData;
import com.mobiblanc.amdie.africa.network.models.menu.MenuItem;
import com.mobiblanc.amdie.africa.network.models.menu.Profile;
import com.mobiblanc.amdie.africa.network.models.menu.Result;
import com.mobiblanc.amdie.africa.network.models.share.ShareAppData;
import com.mobiblanc.amdie.africa.network.utilities.Constants;
import com.mobiblanc.amdie.africa.network.utilities.Resource;
import com.mobiblanc.amdie.africa.network.utilities.Utilities;
import com.mobiblanc.amdie.africa.network.viewmodels.DashboardViewModel;
import com.mobiblanc.amdie.africa.network.views.authentication.AuthenticationActivity;
import com.mobiblanc.amdie.africa.network.views.base.BaseActivity;
import com.mobiblanc.amdie.africa.network.views.cgu.CGUActivity;
import com.mobiblanc.amdie.africa.network.views.dashboard.contacts.ContactsFragment;
import com.mobiblanc.amdie.africa.network.views.dashboard.feed.FeedsFragment;
import com.mobiblanc.amdie.africa.network.views.dashboard.messages.MessagesListFragment;
import com.mobiblanc.amdie.africa.network.views.dashboard.profile.ProfileFragment;
import com.mobiblanc.amdie.africa.network.views.personalinforamtion.PersonalInformationActivity;
import com.mobiblanc.amdie.africa.network.views.settings.SettingsActivity;

import java.util.ArrayList;
import java.util.List;

public class DashboardActivity extends BaseActivity {

    private ActivityDashboardBinding activityBinding;
    private DashboardViewModel viewModel;
    private PreferenceManager preferenceManager;
    private List<MenuItem> menuList;
    private ArrayList<Fragment> fragments;

    public DashboardViewModel getViewModel() {
        return viewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityBinding = DataBindingUtil.setContentView(this, R.layout.activity_dashboard);

        viewModel = ViewModelProviders.of(this).get(DashboardViewModel.class);
        viewModel.getMenuLiveData().observe(this, this::handleGetMenuData);
        viewModel.getShareAppLiveData().observe(this, this::handleShareAppData);
        viewModel.getLogoutLiveData().observe(this, this::handleLogoutData);

        preferenceManager = new PreferenceManager.Builder(this, Context.MODE_PRIVATE)
                .name(BuildConfig.APPLICATION_ID)
                .build();

        getMenu();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        if (getCurrentFragment() instanceof FeedsFragment)
            finish();
        else
            activityBinding.tabLayout.getTabAt(getFragmentIndex(getCallerFragment())).select();
    }

    private void getMenu() {
        viewModel.getMenu(preferenceManager.getValue(Constants.TOKEN, ""), preferenceManager.getValue(Constants.LANGUAGE, "fr"));
    }

    private void handleGetMenuData(Resource<MenuData> responseData) {
        switch (responseData.status) {
            case SUCCESS:
                for (Result result : responseData.data.getResults()) {
                    if (result.getName().equals("Menu")) {
                        menuList = result.getMenuItems();
                        initTab();
                        initNavigationView(result.getProfile());
                    }
                }
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

    private void initTab() {
        activityBinding.editProfileBtn.setOnClickListener(v -> replaceFragment(ProfileFragment.newInstance(true), ""));

        fragments = new ArrayList<>();
        for (int i = 0; i < menuList.size(); i++) {
            TabLayout.Tab tab = activityBinding.tabLayout.newTab();
            View view = tab.getCustomView() == null ? LayoutInflater.from(activityBinding.tabLayout.getContext()).inflate(R.layout.tab_item_layout, null) : tab.getCustomView();
            if (tab.getCustomView() == null) {
                tab.setCustomView(view);
            }
            TextView tabTextView = view.findViewById(R.id.title);
            tabTextView.setText(menuList.get(i).getName().replaceFirst(" ", "\n"));
            ImageView tabImageView = view.findViewById(R.id.icon);
            Glide.with(this).load(menuList.get(i).getInactiveIcon()).into(tabImageView);
            switch (menuList.get(i).getAction()) {
                case "action_feed":
                    fragments.add(new FeedsFragment());
                    break;
                case "action_recherche":
                    fragments.add(ProfileFragment.newInstance(false));
                    break;
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
                showFragment(fragments.get(position));
                activityBinding.title.setText(menuList.get(position).getName());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                showFragment(fragments.get(position));
                activityBinding.title.setText(menuList.get(position).getName());
            }
        });
        activityBinding.tabLayout.getTabAt(0).select();
    }

    private int getFragmentIndex(Fragment fragment) {
        for (int i = 0; i < fragments.size(); i++) {
            if (fragments.get(i).getClass().equals(fragment.getClass()))
                return i;
        }
        return -1;
    }

    private void initNavigationView(Profile profile) {
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

        View headerView = navigationView.getHeaderView(0);
        TextView username = headerView.findViewById(R.id.username);
        username.setText(profile.getFullName());
        TextView country = headerView.findViewById(R.id.country);
        country.setText(profile.getCountry());
        ImageView picture = headerView.findViewById(R.id.picture);
        if (profile.getGender().equalsIgnoreCase("man"))
            Glide.with(this).load(profile.getPicture()).placeholder(R.drawable.men_avatar).into(picture);
        else
            Glide.with(this).load(profile.getPicture()).placeholder(R.drawable.women_avatar).into(picture);

        Menu menu = navigationView.getMenu();
        menu.add(0, 0, 0, getString(R.string.settings_menu_title)).setIcon(R.drawable.ic_parametre);
        menu.add(0, 1, 1, getString(R.string.cgu_menu_title)).setIcon(R.drawable.ic_docs);
        menu.add(0, 2, 2, getString(R.string.message_menu_title)).setIcon(R.drawable.ic_messages);
        menu.add(0, 3, 3, getString(R.string.personal_info_menu_title)).setIcon(R.drawable.ic_secteur);
        menu.add(0, 4, 4, getString(R.string.share_app_menu_title)).setIcon(R.drawable.ic_share_1);
        menu.add(0, 5, 5, getString(R.string.logout_menu_title)).setIcon(R.drawable.ic_logout);

        navigationView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case 0:
                    startActivity(new Intent(DashboardActivity.this, SettingsActivity.class));
                    break;
                case 1:
                    startActivity(new Intent(DashboardActivity.this, CGUActivity.class));
                    break;
                case 2:
                    for (int i = 0; i < menuList.size(); i++) {
                        if (item.getTitle().equals(menuList.get(i).getName()))
                            activityBinding.tabLayout.selectTab(activityBinding.tabLayout.getTabAt(i));
                    }
                    activityBinding.title.setText(item.getTitle());
                    break;
                case 3:
                    startActivity(new Intent(DashboardActivity.this, PersonalInformationActivity.class));
                    break;
                case 4:
                    getShareLink();
                    break;
                case 5:
                    logout();
                    break;
            }
            drawer.closeDrawer(GravityCompat.START);
            return true;
        });
    }

    private void getShareLink() {
        viewModel.getShareLink(preferenceManager.getValue(Constants.TOKEN, ""), preferenceManager.getValue(Constants.LANGUAGE, "fr"));
    }

    private void handleShareAppData(Resource<ShareAppData> responseData) {
        switch (responseData.status) {
            case SUCCESS:
                Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, responseData.data.getResults().getMessage());
                shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, responseData.data.getResults().getUrl());
                startActivity(Intent.createChooser(shareIntent, "Share via"));
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

    private void logout() {
        activityBinding.loader.setVisibility(View.VISIBLE);
        viewModel.logout(preferenceManager.getValue(Constants.TOKEN, ""), preferenceManager.getValue(Constants.LANGUAGE, "fr"));
    }

    private void handleLogoutData(Resource<LogoutData> responseData) {
        activityBinding.loader.setVisibility(View.GONE);
        switch (responseData.status) {
            case SUCCESS:
                preferenceManager.clearValue(Constants.TOKEN);
                startActivity(new Intent(DashboardActivity.this, AuthenticationActivity.class));
                finish();
                break;
            case INVALID_TOKEN:
                break;
            case ERROR:
                Utilities.showServerErrorDialog(this, responseData.message);
                break;
        }
    }

    public void selectProfileTab() {
        for (int i = 0; i < menuList.size(); i++) {
            if (menuList.get(i).getAction().equalsIgnoreCase("action_recherche")) {
                activityBinding.tabLayout.getTabAt(i).select();
                break;
            }
        }
    }

    public void showHideEditBtn(int visibility) {
        activityBinding.editProfileBtn.setVisibility(visibility);
    }
}