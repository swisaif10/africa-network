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
import com.mobiblanc.amdie.africa.network.R;
import com.mobiblanc.amdie.africa.network.databinding.FragmentSmsConfirmationBinding;
import com.mobiblanc.amdie.africa.network.datamanager.sharedpref.PreferenceManager;
import com.mobiblanc.amdie.africa.network.models.authentication.checksms.CheckSMSData;
import com.mobiblanc.amdie.africa.network.models.authentication.sendsms.SendSMSData;
import com.mobiblanc.amdie.africa.network.utilities.Constants;
import com.mobiblanc.amdie.africa.network.utilities.Resource;
import com.mobiblanc.amdie.africa.network.utilities.Utilities;
import com.mobiblanc.amdie.africa.network.views.dashboard.DashboardActivity;

public class SMSConfirmationFragment extends Fragment {

    private static FragmentSmsConfirmationBinding fragmentBinding;
    private String msisdn;
    private PreferenceManager preferenceManager;
    private Boolean request = false;
    private Boolean resendByEmail = false;
    private Boolean OTPByEMail = false;

    public SMSConfirmationFragment() {
        // Required empty public constructor
    }

    public static SMSConfirmationFragment newInstance(String msisdn, Boolean resend) {
        SMSConfirmationFragment fragment = new SMSConfirmationFragment();
        Bundle args = new Bundle();
        args.putString("msisdn", msisdn);
        args.putBoolean("resend", resend);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if ((getArguments() != null)) {
            msisdn = getArguments().getString("msisdn");
            resendByEmail = getArguments().getBoolean("resend");
        }

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
        ((AuthenticationActivity) requireActivity()).getViewModel().getSendSMSLiveData().observe(requireActivity(), this::handleSendSMSData);
        ((AuthenticationActivity) requireActivity()).getViewModel().getCheckOTPByEmailLiveData().observe(requireActivity(), this::handleCheckOTPData);
        ((AuthenticationActivity) requireActivity()).getViewModel().getSendOTPBYEmailLiveData().observe(requireActivity(), this::handleSendOTPData);

        preferenceManager = new PreferenceManager.Builder(requireContext(), Context.MODE_PRIVATE)
                .name(BuildConfig.APPLICATION_ID)
                .build();
    }

    private void init() {
        fragmentBinding.container.setOnClickListener(v -> Utilities.hideSoftKeyboard(requireContext(), requireView()));
        fragmentBinding.backBtn.setOnClickListener(v -> requireActivity().onBackPressed());

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

        if (resendByEmail)
            fragmentBinding.resendCodeBtn.setText(getString(R.string.resend_code_by_email_btn_label));

        fragmentBinding.nextBtn.setOnClickListener(v -> {
            Utilities.hideSoftKeyboard(requireContext(), requireView());
            if (OTPByEMail)
                checkOTP();
            else
                checkSMS();
        });

        fragmentBinding.resendCodeBtn.setOnClickListener(v -> {
            if (resendByEmail)
                sendOTP();
            else
                sendSMS();
        });
    }

    private void checkSMS() {
        fragmentBinding.loader.setVisibility(View.VISIBLE);
        ((AuthenticationActivity) requireActivity()).getViewModel().checkSMS(msisdn, fragmentBinding.code.getText().toString(), preferenceManager.getValue(Constants.FIREBASE_TOKEN, ""), preferenceManager.getValue(Constants.LANGUAGE, "fr"));
        request = true;
    }

    private void handleCheckSMSData(Resource<CheckSMSData> responseData) {
        fragmentBinding.loader.setVisibility(View.GONE);
        if (request) {
            request = false;
            switch (responseData.status) {
                case SUCCESS:
                    if (responseData.data.getResults().getMonitoring() == 0)
                        ((AuthenticationActivity) requireActivity()).replaceFragment(CompleteProfileFragment.newInstance(responseData.data.getResults().getToken(), responseData.data, false, "", msisdn), "");
                    else {
                        preferenceManager.putValue(Constants.TOKEN, responseData.data.getResults().getToken());
                        startActivity(new Intent(requireActivity(), DashboardActivity.class));
                        requireActivity().finish();
                    }
                    break;
                case INVALID_TOKEN:
                    break;
                case ERROR:
                    Utilities.showServerErrorDialog(requireContext(), responseData.message);
                    break;
            }
        }
    }

    private void sendSMS() {
        fragmentBinding.loader.setVisibility(View.VISIBLE);
        ((AuthenticationActivity) requireActivity()).getViewModel().sendSMS(msisdn, preferenceManager.getValue(Constants.LANGUAGE, "fr"), Utilities.getUID(requireContext()));
        request = true;
    }

    private void handleSendSMSData(Resource<SendSMSData> responseData) {
        fragmentBinding.loader.setVisibility(View.GONE);
        if (request) {
            request = false;
            switch (responseData.status) {
                case SUCCESS:
                    break;
                case ERROR:
                    Utilities.showServerErrorDialog(requireContext(), responseData.message);
                    break;
            }
        }
    }

    private void sendOTP() {
        fragmentBinding.loader.setVisibility(View.VISIBLE);
        OTPByEMail = true;
        ((AuthenticationActivity) requireActivity()).getViewModel().sendOTPByEmail(msisdn, preferenceManager.getValue(Constants.LANGUAGE, "fr"), Utilities.getUID(requireContext()));
        request = true;
    }

    private void handleSendOTPData(Resource<SendSMSData> responseData) {
        fragmentBinding.loader.setVisibility(View.GONE);
        if (request) {
            request = false;
            switch (responseData.status) {
                case SUCCESS:
                    break;
                case ERROR:
                    Utilities.showServerErrorDialog(requireContext(), responseData.message);
                    break;
            }
        }
    }

    private void checkOTP() {
        fragmentBinding.loader.setVisibility(View.VISIBLE);
        ((AuthenticationActivity) requireActivity()).getViewModel().checkOTPByEmail(msisdn, fragmentBinding.code.getText().toString(), preferenceManager.getValue(Constants.FIREBASE_TOKEN, ""), preferenceManager.getValue(Constants.LANGUAGE, "fr"));
        request = true;
    }

    private void handleCheckOTPData(Resource<CheckSMSData> responseData) {
        fragmentBinding.loader.setVisibility(View.GONE);
        if (request) {
            request = false;
            switch (responseData.status) {
                case SUCCESS:
                    if (responseData.data.getResults().getMonitoring() == 0)
                        ((AuthenticationActivity) requireActivity()).replaceFragment(CompleteProfileFragment.newInstance(responseData.data.getResults().getToken(), responseData.data, false, "", msisdn), "");
                    else {
                        preferenceManager.putValue(Constants.TOKEN, responseData.data.getResults().getToken());
                        startActivity(new Intent(requireActivity(), DashboardActivity.class));
                        requireActivity().finish();
                    }
                    break;
                case INVALID_TOKEN:
                    break;
                case ERROR:
                    Utilities.showServerErrorDialog(requireContext(), responseData.message);
                    break;
            }
        }
    }
}