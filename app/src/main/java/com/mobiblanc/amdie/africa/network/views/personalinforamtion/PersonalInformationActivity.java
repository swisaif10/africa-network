package com.mobiblanc.amdie.africa.network.views.personalinforamtion;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.View;
import android.widget.ArrayAdapter;

import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.mobiblanc.amdie.africa.network.BuildConfig;
import com.mobiblanc.amdie.africa.network.R;
import com.mobiblanc.amdie.africa.network.databinding.ActivityPersonalInformationBinding;
import com.mobiblanc.amdie.africa.network.datamanager.sharedpref.PreferenceManager;
import com.mobiblanc.amdie.africa.network.models.authentication.completeregistraion.CompleteRegistrationData;
import com.mobiblanc.amdie.africa.network.models.common.Country;
import com.mobiblanc.amdie.africa.network.models.profile.countries.CountriesListData;
import com.mobiblanc.amdie.africa.network.models.profile.details.ProfileDetailsData;
import com.mobiblanc.amdie.africa.network.models.profile.details.Results;
import com.mobiblanc.amdie.africa.network.utilities.Constants;
import com.mobiblanc.amdie.africa.network.utilities.Resource;
import com.mobiblanc.amdie.africa.network.utilities.Utilities;
import com.mobiblanc.amdie.africa.network.viewmodels.PersonalInformationViewModel;
import com.mobiblanc.amdie.africa.network.views.authentication.mobileregister.MobileRegisterFragment;
import com.mobiblanc.amdie.africa.network.views.base.BaseActivity;

public class PersonalInformationActivity extends BaseActivity {

    private ActivityPersonalInformationBinding activityBinding;
    private PreferenceManager preferenceManager;
    private PersonalInformationViewModel viewModel;
    private Results results;
    private String gender = "";
    private Country country;
    private int countryId;
    private int cityId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityBinding = DataBindingUtil.setContentView(this, R.layout.activity_personal_information);

        preferenceManager = new PreferenceManager.Builder(this, Context.MODE_PRIVATE)
                .name(BuildConfig.APPLICATION_ID)
                .build();

        viewModel = ViewModelProviders.of(this).get(PersonalInformationViewModel.class);
        viewModel.getPersonalInformationLiveData().observe(this, this::handlePersonalInformationData);
        viewModel.getCountriesListLiveData().observe(this, this::handleCountriesListData);
        viewModel.getUpdatePersonalInformationLiveData().observe(this, this::handleUpdatePersonalInformationData);

        getPersonaInformation();
    }

    private void getPersonaInformation() {
        activityBinding.loader.setVisibility(View.VISIBLE);
        viewModel.getPersonaInformation(preferenceManager.getValue(Constants.TOKEN, ""), preferenceManager.getValue(Constants.LANGUAGE, "fr"));
    }

    private void handlePersonalInformationData(Resource<ProfileDetailsData> responseData) {
        switch (responseData.status) {
            case SUCCESS:
                getCountries();
                this.results = responseData.data.getResults();
                init();
                break;
            case INVALID_TOKEN:
                activityBinding.loader.setVisibility(View.GONE);
                Utilities.showServerErrorDialog(this, responseData.data.getHeader().getMessage(), v -> {
                    preferenceManager.clearValue(Constants.TOKEN);
                    showFragment(new MobileRegisterFragment());
                });
                break;
            case ERROR:
                activityBinding.loader.setVisibility(View.GONE);
                Utilities.showServerErrorDialog(this, responseData.message);
                break;
        }
    }

    private void init() {
        activityBinding.backBtn.setOnClickListener(v -> finish());
        activityBinding.container.setOnClickListener(v -> Utilities.hideSoftKeyboard(this, activityBinding.getRoot()));

        float elevation = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5,
                getResources().getDisplayMetrics());
        activityBinding.man.setOnClickListener(v -> {
            activityBinding.woman.setBackgroundResource(R.drawable.unselected_country_background);
            activityBinding.woman.setElevation(elevation);
            activityBinding.womanCheck.setImageResource(R.drawable.ic_uncheck_country);
            activityBinding.womanImage.setImageTintList(ContextCompat.getColorStateList(this, R.color.grey));
            activityBinding.womanName.setTextColor(ContextCompat.getColor(this, R.color.grey));
            activityBinding.man.setBackgroundResource(R.drawable.selected_country_background);
            activityBinding.man.setElevation(0f);
            activityBinding.manCheck.setImageResource(R.drawable.ic_check_country);
            activityBinding.manImage.setImageTintList(ContextCompat.getColorStateList(this, R.color.blue4));
            activityBinding.manName.setTextColor(ContextCompat.getColor(this, R.color.blue3));
            gender = "man";
            activityBinding.nextBtn.setEnabled(checkForm());
        });
        activityBinding.woman.setOnClickListener(v -> {
            activityBinding.man.setBackgroundResource(R.drawable.unselected_country_background);
            activityBinding.man.setElevation(elevation);
            activityBinding.manCheck.setImageResource(R.drawable.ic_uncheck_country);
            activityBinding.manImage.setImageTintList(ContextCompat.getColorStateList(this, R.color.grey));
            activityBinding.manName.setTextColor(ContextCompat.getColor(this, R.color.grey));
            activityBinding.woman.setBackgroundResource(R.drawable.selected_country_background);
            activityBinding.woman.setElevation(0f);
            activityBinding.womanCheck.setImageResource(R.drawable.ic_check_country);
            activityBinding.womanImage.setImageTintList(ContextCompat.getColorStateList(this, R.color.blue4));
            activityBinding.womanName.setTextColor(ContextCompat.getColor(this, R.color.blue3));
            gender = "woman";
            activityBinding.nextBtn.setEnabled(checkForm());
        });

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                activityBinding.nextBtn.setEnabled(checkForm());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

        activityBinding.firstName.addTextChangedListener(textWatcher);
        activityBinding.lastName.addTextChangedListener(textWatcher);
        activityBinding.city.addTextChangedListener(textWatcher);
        activityBinding.country.addTextChangedListener(textWatcher);
        activityBinding.job.addTextChangedListener(textWatcher);
        activityBinding.company.addTextChangedListener(textWatcher);
        activityBinding.email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (Utilities.isEmailValid(s.toString()))
                    activityBinding.emailError.setVisibility(View.GONE);
                else if (!s.toString().isEmpty())
                    activityBinding.emailError.setVisibility(View.VISIBLE);
            }
        });

        if (results.getGender().equalsIgnoreCase("man"))
            activityBinding.man.performClick();
        else
            activityBinding.woman.performClick();
        activityBinding.firstName.setText(results.getFirstname());
        activityBinding.lastName.setText(results.getLastname());
        activityBinding.city.setText(results.getCity());
        activityBinding.country.setText(results.getCountry());
        activityBinding.job.setText(results.getFunction());
        activityBinding.company.setText(results.getCompanyName());
        activityBinding.email.setText(results.getEmail());
        countryId = results.getCountryId();
        cityId = results.getCityId();

        activityBinding.nextBtn.setOnClickListener(v -> updatePersonalInformation());
    }

    private void getCountries() {
        viewModel.getCountries(preferenceManager.getValue(Constants.TOKEN, ""), preferenceManager.getValue(Constants.LANGUAGE, "fr"));
    }

    private void handleCountriesListData(Resource<CountriesListData> responseData) {
        activityBinding.loader.setVisibility(View.GONE);
        switch (responseData.status) {
            case SUCCESS:
                init(responseData.data);
                break;
            case INVALID_TOKEN:
                Utilities.showServerErrorDialog(this, responseData.data.getHeader().getMessage(), v -> {
                    preferenceManager.clearValue(Constants.TOKEN);
                    showFragment(new MobileRegisterFragment());
                });
                break;
            case ERROR:
                Utilities.showServerErrorDialog(this, responseData.message);
                break;
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void init(CountriesListData countriesListData) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.custom_dropdown_item_layout, countriesListData.getResults().getCountriesNames());
        activityBinding.country.setAdapter(adapter);
        activityBinding.country.setOnTouchListener((v, event) -> {
            Utilities.hideSoftKeyboard(this, activityBinding.getRoot());
            activityBinding.country.showDropDown();
            return false;
        });

        activityBinding.country.setOnItemClickListener((parent, view, position, id) -> {
            country = countriesListData.getResults().getCountries().get(position);
            countryId = country.getId();
            ArrayAdapter<String> citiesAdapter = new ArrayAdapter<>(this, R.layout.custom_dropdown_item_layout, countriesListData.getResults().getCountries().get(position).getCitiesNames());
            activityBinding.city.setText("");
            activityBinding.city.setAdapter(citiesAdapter);
            activityBinding.city.setOnTouchListener((v, event) -> {
                Utilities.hideSoftKeyboard(this, activityBinding.getRoot());
                activityBinding.city.showDropDown();
                return false;
            });
        });

        activityBinding.city.setOnItemClickListener((parent, view, position, id) -> cityId = country.getCities().get(position).getId());
    }

    private Boolean checkForm() {
        return !gender.equalsIgnoreCase("") &&
                !Utilities.isEmpty(activityBinding.firstName) &&
                !Utilities.isEmpty(activityBinding.lastName) &&
                !Utilities.isEmpty(activityBinding.country) &&
                !Utilities.isEmpty(activityBinding.city) &&
                !Utilities.isEmpty(activityBinding.job) &&
                !Utilities.isEmpty(activityBinding.company) &&
                Utilities.isEmailValid(activityBinding.email.getText().toString());
    }

    private void updatePersonalInformation() {
        activityBinding.loader.setVisibility(View.VISIBLE);
        viewModel.updatePersonalInformation(preferenceManager.getValue(Constants.TOKEN, ""), gender,
                activityBinding.lastName.getText().toString(), activityBinding.company.getText().toString(),
                activityBinding.job.getText().toString(), activityBinding.email.getText().toString(),
                activityBinding.firstName.getText().toString(), countryId, cityId, results.getMonitoring(),
                preferenceManager.getValue(Constants.FIREBASE_TOKEN, ""), "", results.getPhoneNumber());
    }

    private void handleUpdatePersonalInformationData(Resource<CompleteRegistrationData> responseData) {
        activityBinding.loader.setVisibility(View.GONE);
        switch (responseData.status) {
            case SUCCESS:
                Utilities.showServerErrorDialog(this, responseData.data.getHeader().getMessage(), v -> finish());
                break;
            case INVALID_TOKEN:
                Utilities.showServerErrorDialog(this, responseData.data.getHeader().getMessage(), v -> {
                    preferenceManager.clearValue(Constants.TOKEN);
                    showFragment(new MobileRegisterFragment());
                });
                break;
            case ERROR:
                Utilities.showServerErrorDialog(this, responseData.message);
                break;
        }
    }
}