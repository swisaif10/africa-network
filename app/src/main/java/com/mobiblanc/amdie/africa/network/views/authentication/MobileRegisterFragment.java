package com.mobiblanc.amdie.africa.network.views.authentication;

import android.annotation.SuppressLint;
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

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mobiblanc.amdie.africa.network.R;
import com.mobiblanc.amdie.africa.network.Utilities.NumericKeyBoardTransformationMethod;
import com.mobiblanc.amdie.africa.network.Utilities.Resource;
import com.mobiblanc.amdie.africa.network.Utilities.Utilities;
import com.mobiblanc.amdie.africa.network.databinding.FragmentMobileRegisterBinding;
import com.mobiblanc.amdie.africa.network.models.authentication.SendSMSData;
import com.mobiblanc.amdie.africa.network.models.countries.Country;
import com.mobiblanc.amdie.africa.network.views.authentication.mobileregister.CountriesAdapter;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class MobileRegisterFragment extends Fragment {

    private static FragmentMobileRegisterBinding fragmentBinding;
    private Boolean request = false;

    public MobileRegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

    @Override
    public void onResume() {
        super.onResume();
        ((AuthenticationActivity) requireActivity()).getViewModel().getSendSMSLiveData().observe(requireActivity(), this::handleSendSMSData);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void init() {
        CountriesAdapter adapter = new CountriesAdapter(requireContext(), R.layout.custom_country_dropdown_item_layout, R.id.code, readFromJson(), requireActivity());
        fragmentBinding.country.setAdapter(adapter);
        fragmentBinding.country.setOnTouchListener((v, event) -> {
            Utilities.hideSoftKeyboard(requireContext(), requireView());
            fragmentBinding.country.showDropDown();
            return false;
        });

        fragmentBinding.country.setOnItemClickListener((parent, view, position, id) -> {

        });


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
                if (s.length() > 0) {
                    fragmentBinding.phoneNumber.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(requireContext(), R.drawable.ic_check), null);
                    fragmentBinding.nextBtn.setEnabled(true);
                } else {
                    fragmentBinding.phoneNumber.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(requireContext(), R.drawable.ic_uncheck), null);
                    fragmentBinding.nextBtn.setEnabled(false);
                }
            }
        });

        fragmentBinding.backBtn.setOnClickListener(v -> requireActivity().onBackPressed());
        fragmentBinding.nextBtn.setOnClickListener(v -> sendSMS());
    }

    private void sendSMS() {
        fragmentBinding.loader.setVisibility(View.VISIBLE);
        ((AuthenticationActivity) requireActivity()).getViewModel().sendSMS(fragmentBinding.phoneNumber.getText().toString(), "fr", Utilities.getUID(requireContext()));
        request = true;
    }

    private void handleSendSMSData(Resource<SendSMSData> responseData) {
        fragmentBinding.loader.setVisibility(View.GONE);
        if (request)
            switch (responseData.status) {
                case SUCCESS:
                    ((AuthenticationActivity) requireActivity()).replaceFragment(SMSConfirmationFragment.newInstance(fragmentBinding.phoneNumber.getText().toString()), "");
                    break;
                case INVALID_TOKEN:
                    break;
                case ERROR:
                    Utilities.showErrorPopup(requireContext(), responseData.message);
                    break;
            }
    }

    private ArrayList<Country> readFromJson() {
        ArrayList<Country> countries = new ArrayList<>();
        try {
            StringBuilder sb = new StringBuilder();
            InputStream is = requireActivity().getAssets().open("countries.json");
            BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            String str;
            while ((str = br.readLine()) != null) {
                sb.append(str);
            }
            br.close();
            ObjectMapper mapper = new ObjectMapper();
            countries = mapper.readValue(sb.toString(), new TypeReference<List<Country>>() {
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return countries;
    }
}