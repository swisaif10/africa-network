package com.mobiblanc.amdie.africa.network.views.base;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.mobiblanc.amdie.africa.network.R;
import com.mobiblanc.amdie.africa.network.views.authentication.AuthenticationActivity;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void addFragment(Fragment fragment) {

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container, fragment)
                .addToBackStack(null)
                .commit();
    }

    public void replaceFragment(Fragment fragment, String tag) {

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment, tag)
                .addToBackStack(tag)
                .commit();
    }

    public void showFragment(Fragment fragment) {
        String tag = fragment.getClass().getName();
        FragmentManager manager = getSupportFragmentManager();
        boolean fragmentPopped = manager.popBackStackImmediate(tag, 0);

        if (!fragmentPopped) {
            manager.beginTransaction()
                    .replace(R.id.container, fragment, tag)
                    .addToBackStack(tag)
                    .commitAllowingStateLoss();
        }
    }

    public void switchContent(final Fragment fragment, String tag) {
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment, tag)
                .addToBackStack(tag)
                .commitAllowingStateLoss();
    }

    public Fragment getCurrentFragment() {
        return getSupportFragmentManager().findFragmentById(R.id.container);
    }

    public Fragment getCallerFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        int count = fragmentManager.getBackStackEntryCount();
        return fragmentManager.findFragmentByTag(fragmentManager.getBackStackEntryAt(count - 2).getName());
    }

    public void removeFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().remove(fragment).commit();
    }

    public void tokenExpired() {
        startActivity(new Intent(this, AuthenticationActivity.class));
        finish();
    }

}