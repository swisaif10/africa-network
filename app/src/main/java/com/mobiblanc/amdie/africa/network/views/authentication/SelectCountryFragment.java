package com.mobiblanc.amdie.africa.network.views.authentication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.mobiblanc.amdie.africa.network.BuildConfig;
import com.mobiblanc.amdie.africa.network.R;
import com.mobiblanc.amdie.africa.network.databinding.FragmentSelectCountryBinding;
import com.mobiblanc.amdie.africa.network.datamanager.sharedpref.PreferenceManager;
import com.mobiblanc.amdie.africa.network.models.authentication.completeregistraion.CompleteRegistrationData;
import com.mobiblanc.amdie.africa.network.utilities.Constants;
import com.mobiblanc.amdie.africa.network.utilities.Resource;
import com.mobiblanc.amdie.africa.network.utilities.Utilities;
import com.mobiblanc.amdie.africa.network.views.authentication.mobileregister.MobileRegisterFragment;
import com.mobiblanc.amdie.africa.network.views.dashboard.DashboardActivity;

public class SelectCountryFragment extends Fragment {

    private static FragmentSelectCountryBinding fragmentBinding;
    private PreferenceManager preferenceManager;
    private String token;
    private String gender;
    private String firstName;
    private String lastName;
    private String email;
    private String job;
    private String company;
    private String code;
    private String phoneNumber;
    private int country;
    private int city;
    private int nationality = 1;
    private Boolean request = false;

    public SelectCountryFragment() {
        // Required empty public constructor
    }

    public static SelectCountryFragment newInstance
            (String token, String gender, String lastName, String company, String job,
             String email, String firstName, int country, int city, String code, String phoneNumber) {
        SelectCountryFragment fragment = new SelectCountryFragment();
        Bundle args = new Bundle();
        args.putString("token", token);
        args.putString("gender", gender);
        args.putString("firstName", firstName);
        args.putString("lastName", lastName);
        args.putString("email", email);
        args.putString("job", job);
        args.putString("company", company);
        args.putInt("country", country);
        args.putInt("city", city);
        args.putString("code", code);
        args.putString("phoneNumber", phoneNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            token = getArguments().getString("token");
            gender = getArguments().getString("gender");
            firstName = getArguments().getString("firstName");
            lastName = getArguments().getString("lastName");
            email = getArguments().getString("email");
            job = getArguments().getString("job");
            company = getArguments().getString("company");
            country = getArguments().getInt("country");
            city = getArguments().getInt("city");
            phoneNumber = getArguments().getString("phoneNumber");
            code = getArguments().getString("code", "");
        }

        ((AuthenticationActivity) requireActivity()).getViewModel().getUpdateProfileLiveData().observe(requireActivity(), this::handleUpdateProfileData);

        preferenceManager = new PreferenceManager.Builder(requireContext(), Context.MODE_PRIVATE)
                .name(BuildConfig.APPLICATION_ID)
                .build();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentBinding = FragmentSelectCountryBinding.inflate(inflater, container, false);
        return fragmentBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init();
    }

    private void init() {
        float elevation = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5,
                getResources().getDisplayMetrics());

        fragmentBinding.morocco.setOnClickListener(v -> {
            fragmentBinding.others.setBackgroundResource(R.drawable.unselected_country_background);
            fragmentBinding.others.setElevation(elevation);
            fragmentBinding.othersCheck.setImageResource(R.drawable.ic_uncheck_country);
            fragmentBinding.othersMap.setImageResource(R.drawable.ic_uncheck_others);
            fragmentBinding.othersName.setTextColor(ContextCompat.getColor(requireContext(), R.color.grey));

            fragmentBinding.morocco.setBackgroundResource(R.drawable.selected_country_background);
            fragmentBinding.morocco.setElevation(0f);
            fragmentBinding.moroccoCheck.setImageResource(R.drawable.ic_check_country);
            fragmentBinding.moroccoMap.setImageResource(R.drawable.ic_check_morocco);
            fragmentBinding.moroccoName.setTextColor(ContextCompat.getColor(requireContext(), R.color.blue3));

            nationality = 1;
        });

        fragmentBinding.others.setOnClickListener(v -> {
            fragmentBinding.morocco.setBackgroundResource(R.drawable.unselected_country_background);
            fragmentBinding.morocco.setElevation(elevation);
            fragmentBinding.moroccoCheck.setImageResource(R.drawable.ic_uncheck_country);
            fragmentBinding.moroccoMap.setImageResource(R.drawable.ic_uncheck_morocco);
            fragmentBinding.moroccoName.setTextColor(ContextCompat.getColor(requireContext(), R.color.grey));

            fragmentBinding.others.setBackgroundResource(R.drawable.selected_country_background);
            fragmentBinding.others.setElevation(0f);
            fragmentBinding.othersCheck.setImageResource(R.drawable.ic_check_country);
            fragmentBinding.othersMap.setImageResource(R.drawable.ic_check_others);
            fragmentBinding.othersName.setTextColor(ContextCompat.getColor(requireContext(), R.color.blue3));

            nationality = 0;
        });

        fragmentBinding.nextBtn.setOnClickListener(v -> completeRegistration());
    }

    private void completeRegistration() {
        fragmentBinding.loader.setVisibility(View.VISIBLE);
        ((AuthenticationActivity) requireActivity()).getViewModel().completeRegistration(token, gender,
                lastName, company, job, email, firstName, country, city, nationality, preferenceManager.getValue(Constants.FIREBASE_TOKEN, ""), code, phoneNumber);
        request = true;
    }

    private void handleUpdateProfileData(Resource<CompleteRegistrationData> responseData) {
        fragmentBinding.loader.setVisibility(View.GONE);
        if (request)
            switch (responseData.status) {
                case SUCCESS:
                    preferenceManager.putValue(Constants.TOKEN, responseData.data.getResults().getToken());
                    Intent intent = new Intent(requireActivity(), DashboardActivity.class);
                    startActivity(intent);
                    requireActivity().finish();
                    break;
                case INVALID_TOKEN:
                    Utilities.showServerErrorDialog(requireContext(), responseData.data.getHeader().getMessage(), v -> {
                        preferenceManager.clearValue(Constants.TOKEN);
                        ((AuthenticationActivity) requireActivity()).showFragment(new MobileRegisterFragment());
                    });
                    break;
                case ERROR:
                    Utilities.showServerErrorDialog(requireContext(), responseData.message);
                    break;
            }
    }
}