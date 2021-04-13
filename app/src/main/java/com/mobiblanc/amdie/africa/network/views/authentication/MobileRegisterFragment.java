package com.mobiblanc.amdie.africa.network.views.authentication;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.mobiblanc.amdie.africa.network.R;
import com.mobiblanc.amdie.africa.network.Utilities.NumericKeyBoardTransformationMethod;
import com.mobiblanc.amdie.africa.network.Utilities.Resource;
import com.mobiblanc.amdie.africa.network.Utilities.Utilities;
import com.mobiblanc.amdie.africa.network.databinding.FragmentMobileRegisterBinding;
import com.mobiblanc.amdie.africa.network.models.authentication.SendSMSData;

public class MobileRegisterFragment extends Fragment {

    private FragmentMobileRegisterBinding fragmentBinding;

    public MobileRegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((AuthenticationActivity) requireActivity()).getViewModel().getSendSMSLiveData().observe(requireActivity(), this::handleSendSMSData);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentBinding = FragmentMobileRegisterBinding.inflate(inflater, container, false);
        return fragmentBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init();
    }

    private void init() {
        fragmentBinding.container.setOnClickListener(v -> Utilities.hideSoftKeyboard(requireContext(), requireView()));
        fragmentBinding.phoneNumber.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
        fragmentBinding.phoneNumber.setTransformationMethod(new NumericKeyBoardTransformationMethod());

        fragmentBinding.phoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 10) {
                    fragmentBinding.phoneNumber.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(requireContext(), R.drawable.flag), null, ContextCompat.getDrawable(requireContext(), R.drawable.ic_check), null);
                    fragmentBinding.nextBtn.setEnabled(true);
                } else {
                    fragmentBinding.phoneNumber.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(requireContext(), R.drawable.flag), null, ContextCompat.getDrawable(requireContext(), R.drawable.ic_uncheck), null);
                    fragmentBinding.nextBtn.setEnabled(false);
                }
            }
        });

        fragmentBinding.backBtn.setOnClickListener(v -> requireActivity().onBackPressed());
        fragmentBinding.nextBtn.setOnClickListener(v -> sendSMS());
    }

    private void sendSMS() {
        ((AuthenticationActivity) requireActivity()).getViewModel().sendSMS(fragmentBinding.phoneNumber.getText().toString(), "fr", Utilities.getUID(requireContext()));
    }

    private void handleSendSMSData(Resource<SendSMSData> responseData) {
        switch (responseData.status) {
            case SUCCESS:
                ((AuthenticationActivity) requireActivity()).replaceFragment(SMSConfirmationFragment.newInstance(fragmentBinding.phoneNumber.getText().toString()));
                break;
            case LOADING:
                break;
            case ERROR:
                Utilities.showErrorPopup(requireContext(), responseData.message);
                break;
        }
    }
}