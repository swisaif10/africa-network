package com.mobiblanc.amdie.africa.network.views.changelanguage;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.SeekBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.mobiblanc.amdie.africa.network.R;
import com.mobiblanc.amdie.africa.network.databinding.ActivityChangeLanguageBinding;
import com.mobiblanc.amdie.africa.network.views.home.HomeActivity;

import it.beppi.tristatetogglebutton_library.TriStateToggleButton;

public class ChangeLanguageActivity extends AppCompatActivity {

    private ActivityChangeLanguageBinding activityBinding;
    Boolean select=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityBinding = DataBindingUtil.setContentView(this, R.layout.activity_change_language);

        init();
    }

    private void init() {
//        activityBinding.toggle.setOnToggleChanged(new TriStateToggleButton.OnToggleChanged() {
//            @Override
//            public void onToggle(TriStateToggleButton.ToggleStatus toggleStatus, boolean booleanToggleStatus, int toggleIntValue) {
//                switch (toggleStatus) {
//                    case off:
//                        startActivity(new Intent(ChangeLanguageActivity.this, HomeActivity.class));
//                        finish();
//                        break;
//                    case on:
//                        startActivity(new Intent(ChangeLanguageActivity.this, HomeActivity.class));
//                        finish();
//                        break;
//                }
//            }
//        });

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!select){
                startActivity(new Intent(ChangeLanguageActivity.this, HomeActivity.class));
                finish();}
            }
        }, 3000);
        activityBinding.seekbarLangage.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                return true;
            }
        });

        activityBinding.englishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityBinding.seekbarLangage.setProgress(30);
                activityBinding.seekbarLangage.invalidate();
            }
        });
        activityBinding.frenchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityBinding.seekbarLangage.setProgress(0);
                activityBinding.seekbarLangage.invalidate();
            }
        });
        activityBinding.seekbarLangage.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (seekBar.getProgress()>25){
                    select=true;
                    startActivity(new Intent(ChangeLanguageActivity.this, HomeActivity.class));
                    finish();
                }
                if (seekBar.getProgress()<5){
                    select=true;
                    startActivity(new Intent(ChangeLanguageActivity.this, HomeActivity.class));
                    finish();
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
//                if (seekBar.getProgress()>25){
//                    select=true;
//                    startActivity(new Intent(ChangeLanguageActivity.this, HomeActivity.class));
//                    finish();
//                }
//                if (seekBar.getProgress()<5){
//                    select=true;
//                    startActivity(new Intent(ChangeLanguageActivity.this, HomeActivity.class));
//                    finish();
//                }
            }
        });
    }
}