package com.mobiblanc.amdie.africa.network.views.authentication;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.mobiblanc.amdie.africa.network.R;
import com.mobiblanc.amdie.africa.network.Utilities.NumericKeyBoardTransformationMethod;
import com.mobiblanc.amdie.africa.network.Utilities.Utilities;
import com.mobiblanc.amdie.africa.network.databinding.FragmentCompleteProfileBinding;
import com.mobiblanc.amdie.africa.network.models.authentication.checkSMS.CheckSMSData;
import com.mobiblanc.amdie.africa.network.models.authentication.checkSMS.City;
import com.mobiblanc.amdie.africa.network.models.authentication.checkSMS.Country;

public class CompleteProfileFragment extends Fragment {

    private FragmentCompleteProfileBinding fragmentBinding;
    private CheckSMSData data;
    private Country country;
    private City city;

    public CompleteProfileFragment() {
        // Required empty public constructor
    }

    public static CompleteProfileFragment newInstance(String msisdn, CheckSMSData data) {
        CompleteProfileFragment fragment = new CompleteProfileFragment();
        Bundle args = new Bundle();
        args.putString("msisdn", msisdn);
        args.putSerializable("data", data);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null)
            data = (CheckSMSData) getArguments().getSerializable("data");
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentBinding = FragmentCompleteProfileBinding.inflate(inflater, container, false);
        return fragmentBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void init() {
        fragmentBinding.container.setOnClickListener(v -> Utilities.hideSoftKeyboard(requireContext(), requireView()));
        fragmentBinding.nextBtn.setOnClickListener(v -> ((AuthenticationActivity) requireActivity()).replaceFragment(SelectCountryFragment.newInstance(
                fragmentBinding.lastName.getText().toString(), fragmentBinding.company.getText().toString(),
                fragmentBinding.job.getText().toString(), fragmentBinding.email.getText().toString(),
                fragmentBinding.firstName.getText().toString(),
                country.getId(), city.getId())));

        fragmentBinding.phoneNumber.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
        fragmentBinding.phoneNumber.setTransformationMethod(new NumericKeyBoardTransformationMethod());

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

        fragmentBinding.firstName.addTextChangedListener(textWatcher);
        fragmentBinding.lastName.addTextChangedListener(textWatcher);
        fragmentBinding.city.addTextChangedListener(textWatcher);
        fragmentBinding.country.addTextChangedListener(textWatcher);
        fragmentBinding.job.addTextChangedListener(textWatcher);
        fragmentBinding.company.addTextChangedListener(textWatcher);
        fragmentBinding.phoneNumber.addTextChangedListener(textWatcher);

        fragmentBinding.email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (Utilities.isEmailValid(s.toString()))
                    fragmentBinding.emailError.setVisibility(View.GONE);
                else
                    fragmentBinding.emailError.setVisibility(View.VISIBLE);
            }
        });


        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), R.layout.custom_dropdown_item_layout, data.getCountriesNames());
        fragmentBinding.country.setAdapter(adapter);
        fragmentBinding.country.setOnTouchListener((v, event) -> {
            fragmentBinding.country.showDropDown();
            return false;
        });

        fragmentBinding.country.setOnItemClickListener((parent, view, position, id) -> {
            fragmentBinding.city.setVisibility(View.VISIBLE);
            country = data.getCountries().get(position);
            ArrayAdapter<String> citiesAdapter = new ArrayAdapter<>(requireContext(), R.layout.custom_dropdown_item_layout, data.getCountries().get(position).getCitiesNames());
            fragmentBinding.city.setText("");
            fragmentBinding.city.setAdapter(citiesAdapter);
            fragmentBinding.city.setOnTouchListener((v, event) -> {
                fragmentBinding.city.showDropDown();
                return false;
            });
        });

        fragmentBinding.city.setOnItemClickListener((parent, view, position, id) -> city = country.getCities().get(position));

    }

    private Boolean checkForm() {
        return !Utilities.isEmpty(fragmentBinding.firstName) &&
                !Utilities.isEmpty(fragmentBinding.lastName) &&
                !Utilities.isEmpty(fragmentBinding.country) &&
                !Utilities.isEmpty(fragmentBinding.city) &&
                !Utilities.isEmpty(fragmentBinding.job) &&
                !Utilities.isEmpty(fragmentBinding.company) &&
                Utilities.isEmailValid(fragmentBinding.email.getText().toString());
    }
}