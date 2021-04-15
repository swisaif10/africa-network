package com.mobiblanc.amdie.africa.network.views.settings;

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

import it.beppi.tristatetogglebutton_library.TriStateToggleButton;

public class SettingsActivity extends AppCompatActivity {

    private ActivitySettingsBinding activityBinding;
    private PreferenceManager preferenceManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityBinding = DataBindingUtil.setContentView(this, R.layout.activity_settings);

        preferenceManager = new PreferenceManager.Builder(this, Context.MODE_PRIVATE)
                .name(BuildConfig.APPLICATION_ID)
                .build();

        init();
    }

    private void init() {
        activityBinding.backBtn.setOnClickListener(v ->
                onBackPressed());
        activityBinding.toggle.setToggleStatus(preferenceManager.getValue(Constants.NOTIFICATION, true));
//        activityBinding.toggle.setOnClickListener(v ->
//                if(activityBinding.toggle.getToggleStatus().equals("on")){
//                preferenceManager.putValue(Constants.NOTIFICATION, true);}
//                else {
//            preferenceManager.putValue(Constants.NOTIFICATION, true) ; }
//        });
//        activityBinding.toggle.setOnClickListener(new View() {
//            @Override
//            public void onClick(View v) {
//                if(activityBinding.toggle.getToggleStatus().equals("on")){
//                    preferenceManager.putValue(Constants.NOTIFICATION, true);}
//                else {
//                    preferenceManager.putValue(Constants.NOTIFICATION, false) ; }
//            }
//        });
        activityBinding.toggle. setOnToggleChanged(new TriStateToggleButton.OnToggleChanged() {
            @Override
            public void onToggle(TriStateToggleButton.ToggleStatus toggleStatus, boolean booleanToggleStatus, int toggleIntValue) {
                switch (toggleStatus) {
                    case off:preferenceManager.putValue(Constants.NOTIFICATION, false) ; break;
                    case mid: break;
                    case on:preferenceManager.putValue(Constants.NOTIFICATION, true); break;
                }
            }
        });

}



}