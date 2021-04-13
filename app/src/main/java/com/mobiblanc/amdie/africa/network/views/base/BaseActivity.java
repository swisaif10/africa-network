package com.mobiblanc.amdie.africa.network.views.base;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.mobiblanc.amdie.africa.network.R;

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

    public void replaceFragment(Fragment fragment) {

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .addToBackStack(null)
                .commit();
    }

    public void showFragment(Fragment fragment) {
        String backStateName = fragment.getClass().getName();

        FragmentManager manager = getSupportFragmentManager();
        boolean fragmentPopped = manager.popBackStackImmediate(backStateName, 0);

        if (!fragmentPopped) {
            manager.beginTransaction()
                    .replace(R.id.container, fragment)
                    .addToBackStack(null)
                    .commit();
        }
    }

    public void switchContent(final Fragment fragment, boolean add, boolean anim) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if (add)
            transaction.add(R.id.container, fragment);
        else
            transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(fragment.getClass().getName());
        transaction.commitAllowingStateLoss();
        //getSupportFragmentManager().executePendingTransactions();
    }
}