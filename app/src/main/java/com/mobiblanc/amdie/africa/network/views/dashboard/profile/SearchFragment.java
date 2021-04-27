package com.mobiblanc.amdie.africa.network.views.dashboard.profile;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.mobiblanc.amdie.africa.network.Utilities.Utilities;
import com.mobiblanc.amdie.africa.network.databinding.FragmentSearchBinding;

import java.io.File;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;

public class SearchFragment extends Fragment {
    private File file = null;
    private static final String[] CAMERA_PERMISSION = new String[]{Manifest.permission.CAMERA};
    private static final int CAMERA_REQUEST_CODE = 10;
    private  static final int REQUEST_IMAGE_CAPTURE=20;
    private  static final int REQUEST_IMAGE_CAPTURE_PROFILE=1;
    private  static final int REQUEST_IMAGE_CAPTURE_ENTRPRISE=2;
    private FragmentSearchBinding fragmentBinding;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentBinding = FragmentSearchBinding.inflate(inflater, container, false);
        return fragmentBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init();
    }

    private void init() {

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                fragmentBinding.nextBtn.setEnabled(checkForm());
            }
        };

        fragmentBinding.presentation.addTextChangedListener(textWatcher);
        fragmentBinding.topics.addTextChangedListener(textWatcher);
        fragmentBinding.sector.addTextChangedListener(textWatcher);
        fragmentBinding.headquarters.addTextChangedListener(textWatcher);
        fragmentBinding.squad.addTextChangedListener(textWatcher);
        fragmentBinding.turnover.addTextChangedListener(textWatcher);
        fragmentBinding.products.addTextChangedListener(textWatcher);

        fragmentBinding.btnPhotoEntreprise.setOnClickListener(v -> dispatchTakePictureIntent(REQUEST_IMAGE_CAPTURE_ENTRPRISE));
        fragmentBinding.btnPhotoProfile.setOnClickListener(v ->dispatchTakePictureIntent(REQUEST_IMAGE_CAPTURE_PROFILE));

    }

    private void dispatchTakePictureIntent( int i) {
        if (ContextCompat.checkSelfPermission(Objects.requireNonNull(getContext()), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            try {
                startActivityForResult(takePictureIntent, i);
            } catch (ActivityNotFoundException e) {
                // display error state to the user
            }
        } else {
            ActivityCompat.requestPermissions(Objects.requireNonNull(getActivity()), CAMERA_PERMISSION, CAMERA_REQUEST_CODE);
        }



    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE_ENTRPRISE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
         fragmentBinding.imgEntreprise.setImageBitmap(imageBitmap);
        }
        if (requestCode == REQUEST_IMAGE_CAPTURE_PROFILE&& resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            fragmentBinding.imgProfil.setImageBitmap(imageBitmap);
        }
    }





    private Boolean checkForm() {
        return !Utilities.isEmpty(fragmentBinding.presentation) &&
                !Utilities.isEmpty(fragmentBinding.topics) &&
                !Utilities.isEmpty(fragmentBinding.sector) &&
                !Utilities.isEmpty(fragmentBinding.headquarters) &&
                !Utilities.isEmpty(fragmentBinding.squad) &&
                !Utilities.isEmpty(fragmentBinding.turnover) &&
                !Utilities.isEmpty(fragmentBinding.products);
    }


}