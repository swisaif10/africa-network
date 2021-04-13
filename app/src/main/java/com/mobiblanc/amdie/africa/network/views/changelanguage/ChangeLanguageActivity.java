package com.mobiblanc.amdie.africa.network.views.changelanguage;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.mobiblanc.amdie.africa.network.R;
import com.mobiblanc.amdie.africa.network.databinding.ActivityChangeLanguageBinding;
import com.mobiblanc.amdie.africa.network.views.home.HomeActivity;

import it.beppi.tristatetogglebutton_library.TriStateToggleButton;

public class ChangeLanguageActivity extends AppCompatActivity {

    private ActivityChangeLanguageBinding activityBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityBinding = DataBindingUtil.setContentView(this, R.layout.activity_change_language);

        init();
    }

    private void init() {
        activityBinding.toggle.setOnToggleChanged(new TriStateToggleButton.OnToggleChanged() {
            @Override
            public void onToggle(TriStateToggleButton.ToggleStatus toggleStatus, boolean booleanToggleStatus, int toggleIntValue) {
                switch (toggleStatus) {
                    case off:
                        startActivity(new Intent(ChangeLanguageActivity.this, HomeActivity.class));
                        finish();
                        break;
                    case on:
                        startActivity(new Intent(ChangeLanguageActivity.this, HomeActivity.class));
                        finish();
                        break;
                }
            }
        });
    }
}