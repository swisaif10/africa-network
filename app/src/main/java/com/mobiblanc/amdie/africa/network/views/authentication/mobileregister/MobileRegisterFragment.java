package com.mobiblanc.amdie.africa.network.views.authentication.mobileregister;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mobiblanc.amdie.africa.network.BuildConfig;
import com.mobiblanc.amdie.africa.network.R;
import com.mobiblanc.amdie.africa.network.databinding.FragmentMobileRegisterBinding;
import com.mobiblanc.amdie.africa.network.datamanager.sharedpref.PreferenceManager;
import com.mobiblanc.amdie.africa.network.models.authentication.checksms.CheckSMSData;
import com.mobiblanc.amdie.africa.network.models.authentication.sendsms.SendSMSData;
import com.mobiblanc.amdie.africa.network.models.countries.Country;
import com.mobiblanc.amdie.africa.network.utilities.Constants;
import com.mobiblanc.amdie.africa.network.utilities.NumericKeyBoardTransformationMethod;
import com.mobiblanc.amdie.africa.network.utilities.Resource;
import com.mobiblanc.amdie.africa.network.utilities.Utilities;
import com.mobiblanc.amdie.africa.network.views.authentication.AuthenticationActivity;
import com.mobiblanc.amdie.africa.network.views.authentication.CompleteProfileFragment;
import com.mobiblanc.amdie.africa.network.views.authentication.SMSConfirmationFragment;
import com.mobiblanc.amdie.africa.network.views.dashboard.DashboardActivity;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class MobileRegisterFragment extends Fragment {

    private static FragmentMobileRegisterBinding fragmentBinding;
    private PreferenceManager preferenceManager;
    private Boolean request = false;
    private String code;
    private String phoneNumber;

    public MobileRegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        preferenceManager = new PreferenceManager.Builder(requireContext(), Context.MODE_PRIVATE)
                .name(BuildConfig.APPLICATION_ID)
                .build();
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
        ((AuthenticationActivity) requireActivity()).getViewModel().getLoginWithLinkedInLiveData().observe(requireActivity(), this::handleLoginWithLinkedinData);
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
        fragmentBinding.linkedinBtn.setOnClickListener(v -> {
            fragmentBinding.webViewLayout.setVisibility(View.VISIBLE);
            fragmentBinding.webView.getSettings().setJavaScriptEnabled(true);
            fragmentBinding.webView.setWebViewClient(new WebViewClient() {

                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    super.onPageStarted(view, url, favicon);
                }

                @Override
                public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                    String url = request.getUrl().toString();
                    //http://africa.amdie.mobiblanc.com/callback_linkedin?code=
                    if (url.contains("http://africanetwork.morocconow.com/callback_linkedin?code=")) {
                        fragmentBinding.webViewLayout.setVisibility(View.GONE);
                        code = url.substring(url.indexOf("=") + 1);
                        loginWithLinkedin();
                    }
                    return super.shouldOverrideUrlLoading(view, request);
                }

                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                }
            });
            fragmentBinding.webView.loadUrl(Constants.LINKEDIN_PROD_URL);

            try {
                URI uri = new URI(Constants.LINKEDIN_PROD_URL);
                String domain = uri.getHost();
                fragmentBinding.webViewUrl.setText(domain);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        });
        fragmentBinding.closeWebViewBtn.setOnClickListener(v1 -> fragmentBinding.webViewLayout.setVisibility(View.GONE));
        fragmentBinding.nextBtn.setOnClickListener(v -> {
            Utilities.hideSoftKeyboard(requireContext(), requireView());
            sendSMS();
        });
    }

    private void sendSMS() {
        fragmentBinding.loader.setVisibility(View.VISIBLE);
        phoneNumber = fragmentBinding.country.getText().toString().replace("+", "") + " " + fragmentBinding.phoneNumber.getText().toString();
        ((AuthenticationActivity) requireActivity()).getViewModel().sendSMS(phoneNumber, preferenceManager.getValue(Constants.LANGUAGE, "fr"), Utilities.getUID(requireContext()));
        request = true;
    }

    private void handleSendSMSData(Resource<SendSMSData> responseData) {
        fragmentBinding.loader.setVisibility(View.GONE);
        if (request) {
            request = false;
            switch (responseData.status) {
                case SUCCESS:
                    ((AuthenticationActivity) requireActivity()).replaceFragment(SMSConfirmationFragment.newInstance(phoneNumber, responseData.data.getResults().getResendByEmail()), "");
                    break;
                case INVALID_TOKEN:
                    break;
                case ERROR:
                    Utilities.showServerErrorDialog(requireContext(), responseData.message);
                    break;
            }
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

    private void loginWithLinkedin() {
        fragmentBinding.loader.setVisibility(View.VISIBLE);
        ((AuthenticationActivity) requireActivity()).getViewModel().loginWithLinkedin(code, preferenceManager.getValue(Constants.LANGUAGE, "fr"));
        request = true;
    }

    private void handleLoginWithLinkedinData(Resource<CheckSMSData> responseData) {
        fragmentBinding.loader.setVisibility(View.GONE);
        if (request) {
            request = false;
            switch (responseData.status) {
                case SUCCESS:
                    if (responseData.data.getResults().getMonitoring() == 0)
                        ((AuthenticationActivity) requireActivity()).replaceFragment(CompleteProfileFragment.newInstance(responseData.data.getResults().getToken(), responseData.data, true, "", ""), "");
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