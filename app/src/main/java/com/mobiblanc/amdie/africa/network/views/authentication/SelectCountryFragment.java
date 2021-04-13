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
import com.mobiblanc.amdie.africa.network.Utilities.Constants;
import com.mobiblanc.amdie.africa.network.Utilities.Resource;
import com.mobiblanc.amdie.africa.network.Utilities.Utilities;
import com.mobiblanc.amdie.africa.network.databinding.FragmentSelectCountryBinding;
import com.mobiblanc.amdie.africa.network.datamanager.sharedpref.PreferenceManager;
import com.mobiblanc.amdie.africa.network.models.authentication.updateprofile.UpdateProfileData;
import com.mobiblanc.amdie.africa.network.views.dashboard.DashboardActivity;

public class SelectCountryFragment extends Fragment {

    private FragmentSelectCountryBinding fragmentBinding;
    private PreferenceManager preferenceManager;
    private String firstName;
    private String lastName;
    private String email;
    private String job;
    private String company;
    private int country;
    private int city;
    private int nationality = 1;

    public SelectCountryFragment() {
        // Required empty public constructor
    }

    public static SelectCountryFragment newInstance
            (String lastName, String company, String job,
             String email, String firstName, int country, int city) {
        SelectCountryFragment fragment = new SelectCountryFragment();
        Bundle args = new Bundle();
        args.putString("firstName", firstName);
        args.putString("lastName", lastName);
        args.putString("email", email);
        args.putString("job", job);
        args.putString("company", company);
        args.putInt("country", country);
        args.putInt("city", city);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            firstName = getArguments().getString("firstName");
            lastName = getArguments().getString("lastName");
            email = getArguments().getString("email");
            job = getArguments().getString("job");
            company = getArguments().getString("company");
            country = getArguments().getInt("country");
            city = getArguments().getInt("city");
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

        fragmentBinding.nextBtn.setOnClickListener(v -> updateProfile());
    }

    private void updateProfile() {
        ((AuthenticationActivity) requireActivity()).getViewModel().updateProfile(preferenceManager.getValue(Constants.TOKEN, ""),
                lastName, company, job, email, firstName, country, city, nationality);
    }

    private void handleUpdateProfileData(Resource<UpdateProfileData> responseData) {
        switch (responseData.status) {
            case SUCCESS:
                Intent intent = new Intent(requireActivity(), DashboardActivity.class);
                startActivity(intent);
                requireActivity().finish();
                break;
            case LOADING:
                break;
            case ERROR:
                Utilities.showErrorPopup(requireContext(), responseData.message);
                break;
        }
    }
}