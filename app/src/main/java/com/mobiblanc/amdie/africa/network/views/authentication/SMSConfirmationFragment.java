package com.mobiblanc.amdie.africa.network.views.authentication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.mobiblanc.amdie.africa.network.BuildConfig;
import com.mobiblanc.amdie.africa.network.Utilities.Constants;
import com.mobiblanc.amdie.africa.network.Utilities.Resource;
import com.mobiblanc.amdie.africa.network.Utilities.Utilities;
import com.mobiblanc.amdie.africa.network.databinding.FragmentSmsConfirmationBinding;
import com.mobiblanc.amdie.africa.network.datamanager.sharedpref.PreferenceManager;
import com.mobiblanc.amdie.africa.network.models.authentication.checkSMS.CheckSMSData;
import com.mobiblanc.amdie.africa.network.views.dashboard.DashboardActivity;

public class SMSConfirmationFragment extends Fragment {

    private static FragmentSmsConfirmationBinding fragmentBinding;
    private String msisdn;
    private PreferenceManager preferenceManager;
    private Boolean request = false;

    public static SMSConfirmationFragment newInstance(String msisdn) {
        SMSConfirmationFragment fragment = new SMSConfirmationFragment();
        Bundle args = new Bundle();
        args.putString("msisdn", msisdn);
        fragment.setArguments(args);
        return fragment;
    }

    public SMSConfirmationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if ((getArguments() != null))
            msisdn = getArguments().getString("msisdn");

        ((AuthenticationActivity) requireActivity()).getViewModel().getCheckSMSLiveData().observe(requireActivity(), this::handleCheckSMSData);

        preferenceManager = new PreferenceManager.Builder(requireContext(), Context.MODE_PRIVATE)
                .name(BuildConfig.APPLICATION_ID)
                .build();

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentBinding = FragmentSmsConfirmationBinding.inflate(inflater, container, false);
        return fragmentBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init();
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AuthenticationActivity) requireActivity()).getViewModel().getCheckSMSLiveData().observe(requireActivity(), this::handleCheckSMSData);

        preferenceManager = new PreferenceManager.Builder(requireContext(), Context.MODE_PRIVATE)
                .name(BuildConfig.APPLICATION_ID)
                .build();
    }

    private void init() {
        fragmentBinding.container.setOnClickListener(v -> Utilities.hideSoftKeyboard(requireContext(), requireView()));
        fragmentBinding.backBtn.setOnClickListener(v -> requireActivity().onBackPressed());
        fragmentBinding.nextBtn.setOnClickListener(v -> checkSMS());

        fragmentBinding.code.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                fragmentBinding.nextBtn.setEnabled(fragmentBinding.code.getText().length() == 4);
            }
        });
    }

    private void checkSMS() {
        fragmentBinding.loader.setVisibility(View.VISIBLE);
        ((AuthenticationActivity) requireActivity()).getViewModel().checkSMS(msisdn, fragmentBinding.code.getText().toString(), preferenceManager.getValue(Constants.FIREBASE_TOKEN, ""), "fr");
        request = true;
    }

    private void handleCheckSMSData(Resource<CheckSMSData> responseData) {
        fragmentBinding.loader.setVisibility(View.GONE);
        if (request)
            switch (responseData.status) {
                case SUCCESS:
                    preferenceManager.putValue(Constants.TOKEN, responseData.data.getResults().getToken());
                    preferenceManager.putValue(Constants.ID_USER, responseData.data.getResults().getId());
                    preferenceManager.putValue(Constants.USERNAME, responseData.data.getResults().getFirstName() + " " + responseData.data.getResults().getLastName());
                    preferenceManager.putValue(Constants.COUNTRY, responseData.data.getCountryNameById(responseData.data.getResults().getCountry()));
                    preferenceManager.putValue(Constants.PICTURE, responseData.data.getResults().getPicture());
                    if (responseData.data.getResults().getSearch() == 1 || responseData.data.getResults().getSearch() == 0) {
                        startActivity(new Intent(requireActivity(), DashboardActivity.class));
                        requireActivity().finish();
                    } else
                        ((AuthenticationActivity) requireActivity()).replaceFragment(CompleteProfileFragment.newInstance(msisdn, responseData.data), "");
                    break;
                case INVALID_TOKEN:
                    break;
                case ERROR:
                    Utilities.showErrorPopup(requireContext(), responseData.message);
                    break;
            }
    }
}