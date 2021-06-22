package com.mobiblanc.amdie.africa.network.views.authentication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.mobiblanc.amdie.africa.network.R;
import com.mobiblanc.amdie.africa.network.databinding.FragmentCompleteProfileBinding;
import com.mobiblanc.amdie.africa.network.models.authentication.checksms.CheckSMSData;
import com.mobiblanc.amdie.africa.network.models.common.City;
import com.mobiblanc.amdie.africa.network.models.common.Country;
import com.mobiblanc.amdie.africa.network.models.common.Item;
import com.mobiblanc.amdie.africa.network.utilities.NumericKeyBoardTransformationMethod;
import com.mobiblanc.amdie.africa.network.utilities.Utilities;
import com.mobiblanc.amdie.africa.network.views.cgu.CGUActivity;

public class CompleteProfileFragment extends Fragment {

    private FragmentCompleteProfileBinding fragmentBinding;
    private String token;
    private CheckSMSData data;
    private Boolean isLinkedin;
    private Country country;
    private City city;
    private String job;
    private String gender = "";
    private String code;
    private String phoneNumber;

    public CompleteProfileFragment() {
        // Required empty public constructor
    }

    public static CompleteProfileFragment newInstance(String token, CheckSMSData data, Boolean isLinkedin, String code, String phoneNumber) {
        CompleteProfileFragment fragment = new CompleteProfileFragment();
        Bundle args = new Bundle();
        args.putString("token", token);
        args.putSerializable("data", data);
        args.putBoolean("isLinkedin", isLinkedin);
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
            data = (CheckSMSData) getArguments().getSerializable("data");
            isLinkedin = getArguments().getBoolean("isLinkedin");
            code = getArguments().getString("code", "");
            phoneNumber = getArguments().getString("phoneNumber");
        }
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
        float elevation = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5,
                getResources().getDisplayMetrics());

        fragmentBinding.man.setOnClickListener(v -> {
            fragmentBinding.woman.setBackgroundResource(R.drawable.unselected_country_background);
            fragmentBinding.woman.setElevation(elevation);
            fragmentBinding.womanCheck.setImageResource(R.drawable.ic_uncheck_country);
            fragmentBinding.womanImage.setImageTintList(ContextCompat.getColorStateList(requireContext(), R.color.grey));
            fragmentBinding.womanName.setTextColor(ContextCompat.getColor(requireContext(), R.color.grey));

            fragmentBinding.man.setBackgroundResource(R.drawable.selected_country_background);
            fragmentBinding.man.setElevation(0f);
            fragmentBinding.manCheck.setImageResource(R.drawable.ic_check_country);
            fragmentBinding.manImage.setImageTintList(ContextCompat.getColorStateList(requireContext(), R.color.blue4));
            fragmentBinding.manName.setTextColor(ContextCompat.getColor(requireContext(), R.color.blue3));

            gender = "man";

            fragmentBinding.nextBtn.setEnabled(checkForm());
        });

        fragmentBinding.woman.setOnClickListener(v -> {
            fragmentBinding.man.setBackgroundResource(R.drawable.unselected_country_background);
            fragmentBinding.man.setElevation(elevation);
            fragmentBinding.manCheck.setImageResource(R.drawable.ic_uncheck_country);
            fragmentBinding.manImage.setImageTintList(ContextCompat.getColorStateList(requireContext(), R.color.grey));
            fragmentBinding.manName.setTextColor(ContextCompat.getColor(requireContext(), R.color.grey));

            fragmentBinding.woman.setBackgroundResource(R.drawable.selected_country_background);
            fragmentBinding.woman.setElevation(0f);
            fragmentBinding.womanCheck.setImageResource(R.drawable.ic_check_country);
            fragmentBinding.womanImage.setImageTintList(ContextCompat.getColorStateList(requireContext(), R.color.blue4));
            fragmentBinding.womanName.setTextColor(ContextCompat.getColor(requireContext(), R.color.blue3));

            gender = "woman";

            fragmentBinding.nextBtn.setEnabled(checkForm());
        });

        fragmentBinding.container.setOnClickListener(v -> Utilities.hideSoftKeyboard(requireContext(), requireView()));

        if (isLinkedin)
            fragmentBinding.phoneNumber.setVisibility(View.VISIBLE);
        fragmentBinding.phoneNumber.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
        fragmentBinding.phoneNumber.setTransformationMethod(new NumericKeyBoardTransformationMethod());

        fragmentBinding.cguBtn.setText(Html.fromHtml(requireActivity().getResources().getString(R.string.cgu_text)));
        fragmentBinding.cguCheck.setOnCheckedChangeListener((buttonView, isChecked) -> fragmentBinding.nextBtn.setEnabled(checkForm()));
        fragmentBinding.cguBtn.setOnClickListener(v -> startActivity(new Intent(requireActivity(), CGUActivity.class)));

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                fragmentBinding.nextBtn.setEnabled(checkForm());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

        fragmentBinding.firstName.addTextChangedListener(textWatcher);
        fragmentBinding.lastName.addTextChangedListener(textWatcher);
        fragmentBinding.city.addTextChangedListener(textWatcher);
        fragmentBinding.country.addTextChangedListener(textWatcher);
        fragmentBinding.othersJob.addTextChangedListener(textWatcher);
        fragmentBinding.company.addTextChangedListener(textWatcher);
        fragmentBinding.phoneNumber.addTextChangedListener(textWatcher);
        fragmentBinding.email.addTextChangedListener(textWatcher);
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
                else if (!s.toString().isEmpty())
                    fragmentBinding.emailError.setVisibility(View.VISIBLE);
            }
        });

        fragmentBinding.firstName.setText(data.getResults().getFirstName());
        fragmentBinding.lastName.setText(data.getResults().getLastName());
        fragmentBinding.email.setText(data.getResults().getEmail());

        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), R.layout.custom_dropdown_item_layout, data.getCountriesNames());
        fragmentBinding.country.setAdapter(adapter);
        fragmentBinding.country.setOnTouchListener((v, event) -> {
            Utilities.hideSoftKeyboard(requireContext(), requireView());
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
                Utilities.hideSoftKeyboard(requireContext(), requireView());
                fragmentBinding.city.showDropDown();
                return false;
            });
        });

        fragmentBinding.city.setOnItemClickListener((parent, view, position, id) -> city = country.getCities().get(position));

        ArrayAdapter<String> jobsAdapter = new ArrayAdapter<>(requireContext(), R.layout.custom_dropdown_item_layout, data.getJobsNames());
        fragmentBinding.job.setAdapter(jobsAdapter);
        fragmentBinding.job.setOnTouchListener((v, event) -> {
            Utilities.hideSoftKeyboard(requireContext(), requireView());
            fragmentBinding.job.showDropDown();
            return false;
        });

        fragmentBinding.job.setOnItemClickListener((parent, view, position, id) -> {
            Item item = data.getJobs().get(position);
            if (item.getName().equalsIgnoreCase("Autre") || item.getName().equalsIgnoreCase("Other")) {
                fragmentBinding.othersJob.setText("");
                fragmentBinding.othersJob.setVisibility(View.VISIBLE);
            } else {
                fragmentBinding.othersJob.setVisibility(View.GONE);
                fragmentBinding.othersJob.setText("");
            }
            job = String.valueOf(item.getId());
        });

        fragmentBinding.nextBtn.setOnClickListener(v -> {
            if (!Utilities.isEmpty(fragmentBinding.phoneNumber))
                phoneNumber = fragmentBinding.phoneNumber.getText().toString();
            ((AuthenticationActivity) requireActivity()).replaceFragment(SelectCountryFragment.newInstance(token,
                    gender, fragmentBinding.lastName.getText().toString(), fragmentBinding.company.getText().toString(),
                    job, fragmentBinding.email.getText().toString(),
                    fragmentBinding.firstName.getText().toString(),
                    country.getId(), city.getId(), code, phoneNumber, fragmentBinding.job.getText().toString()), "");
        });
    }

    private Boolean checkForm() {
        if (isLinkedin)
            return !gender.equalsIgnoreCase("") &&
                    !Utilities.isEmpty(fragmentBinding.phoneNumber) &&
                    !Utilities.isEmpty(fragmentBinding.firstName) &&
                    !Utilities.isEmpty(fragmentBinding.lastName) &&
                    !Utilities.isEmpty(fragmentBinding.country) &&
                    !Utilities.isEmpty(fragmentBinding.city) &&
                    (((fragmentBinding.job.getText().toString().equalsIgnoreCase("Autre")
                            || fragmentBinding.job.getText().toString().equalsIgnoreCase("Other"))
                            && !Utilities.isEmpty(fragmentBinding.othersJob)) || (!fragmentBinding.job.getText().toString().equalsIgnoreCase("Autre")
                            && !fragmentBinding.job.getText().toString().equalsIgnoreCase("Other") && !Utilities.isEmpty(fragmentBinding.job))) &&
                    !Utilities.isEmpty(fragmentBinding.company) &&
                    Utilities.isEmailValid(fragmentBinding.email.getText().toString()) &&
                    fragmentBinding.cguCheck.isChecked();
        else
            return !gender.equalsIgnoreCase("") &&
                    !Utilities.isEmpty(fragmentBinding.firstName) &&
                    !Utilities.isEmpty(fragmentBinding.lastName) &&
                    !Utilities.isEmpty(fragmentBinding.country) &&
                    !Utilities.isEmpty(fragmentBinding.city) &&
                    (((fragmentBinding.job.getText().toString().equalsIgnoreCase("Autre")
                            || fragmentBinding.job.getText().toString().equalsIgnoreCase("Other"))
                            && !Utilities.isEmpty(fragmentBinding.othersJob)) || (!fragmentBinding.job.getText().toString().equalsIgnoreCase("Autre")
                            && !fragmentBinding.job.getText().toString().equalsIgnoreCase("Other") && !Utilities.isEmpty(fragmentBinding.job))) &&
                    !Utilities.isEmpty(fragmentBinding.company) &&
                    Utilities.isEmailValid(fragmentBinding.email.getText().toString()) &&
                    fragmentBinding.cguCheck.isChecked();
    }
}