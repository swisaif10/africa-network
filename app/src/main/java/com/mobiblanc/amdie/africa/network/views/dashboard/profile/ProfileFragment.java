package com.mobiblanc.amdie.africa.network.views.dashboard.profile;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.mobiblanc.amdie.africa.network.BuildConfig;
import com.mobiblanc.amdie.africa.network.databinding.FragmentProfileBinding;
import com.mobiblanc.amdie.africa.network.datamanager.sharedpref.PreferenceManager;
import com.mobiblanc.amdie.africa.network.listeners.OnDialogButtonsClickListener;
import com.mobiblanc.amdie.africa.network.listeners.OnFormOptionsSelectedListener;
import com.mobiblanc.amdie.africa.network.models.common.Item;
import com.mobiblanc.amdie.africa.network.models.profile.details.ProfileDetailsData;
import com.mobiblanc.amdie.africa.network.models.profile.initform.Forms;
import com.mobiblanc.amdie.africa.network.models.profile.initform.InitProfileFormData;
import com.mobiblanc.amdie.africa.network.models.profile.update.ProfileDetailsForUpdateData;
import com.mobiblanc.amdie.africa.network.models.profile.update.Results;
import com.mobiblanc.amdie.africa.network.models.profile.update.UpdateProfileData;
import com.mobiblanc.amdie.africa.network.utilities.Constants;
import com.mobiblanc.amdie.africa.network.utilities.FileUtils;
import com.mobiblanc.amdie.africa.network.utilities.NumericKeyBoardTransformationMethod;
import com.mobiblanc.amdie.africa.network.utilities.Resource;
import com.mobiblanc.amdie.africa.network.utilities.Utilities;
import com.mobiblanc.amdie.africa.network.viewmodels.ProfileViewModel;
import com.mobiblanc.amdie.africa.network.views.dashboard.DashboardActivity;
import com.mobiblanc.amdie.africa.network.views.dashboard.profile.details.ProfileDetailsFragment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class ProfileFragment extends Fragment implements OnFormOptionsSelectedListener {

    private static final int REQUEST_CODE_CAMERA = 101;
    private static final int REQUEST_CODE_GALLERY = 102;

    private FragmentProfileBinding fragmentBinding;
    private PreferenceManager preferenceManager;
    private ProfileViewModel viewModel;
    private Boolean update;
    private Forms forms;
    private List<Item> selectedTopics;
    private List<Item> selectedSectors;
    private List<Item> selectedHeadquarters;
    private List<Item> selectedCurrency;
    private String selectedPictureType;
    private Uri profilePictureUri;
    private Uri companyPictureUri;
    private ActivityResultLauncher<Intent> cameraActivityResultLauncher;
    private ActivityResultLauncher<Intent> galleryActivityResultLauncher;

    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance(Boolean update) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putBoolean("update", update);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);
        viewModel.getInitProfileFormLiveData().observe(this, this::handleInitProfileFormData);
        viewModel.getUpdateProfileLiveData().observe(requireActivity(), this::handleUpdateProfileData);
        viewModel.getProfileDetailsLiveData().observe(this, this::handleGetProfileDetailsData);
        viewModel.getProfileDetailsForUpdateLiveData().observe(this, this::handleGetProfileForUpdateData);

        preferenceManager = new PreferenceManager.Builder(requireContext(), Context.MODE_PRIVATE)
                .name(BuildConfig.APPLICATION_ID)
                .build();

        if (getArguments() != null)
            update = getArguments().getBoolean("update");

        cameraActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Bundle extras = result.getData().getExtras();
                        Bitmap imageBitmap;
                        imageBitmap = (Bitmap) extras.get("data");
                        profilePictureUri = getImageUri(imageBitmap);
                        switch (selectedPictureType) {
                            case "profile":
                                fragmentBinding.profilePicture.setImageBitmap(imageBitmap);
                                break;
                            case "company":
                                fragmentBinding.companyPicture.setImageBitmap(imageBitmap);
                                break;
                        }
                    }
                });

        galleryActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Uri selectedImage;
                        selectedImage = result.getData().getData();
                        companyPictureUri = selectedImage;
                        switch (selectedPictureType) {
                            case "profile":
                                fragmentBinding.profilePicture.setImageURI(selectedImage);
                                break;
                            case "company":
                                fragmentBinding.companyPicture.setImageURI(selectedImage);
                                break;
                        }
                    }
                });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentBinding = FragmentProfileBinding.inflate(inflater, container, false);
        return fragmentBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initProfileForm();
        init();
    }

    @Override
    public void onTopicsSelected(List<Item> selectedOptions, String name) {
        if (name.equalsIgnoreCase(forms.getTopics().getName())) {
            this.selectedTopics = selectedOptions;
            fragmentBinding.topics.setText(convertListToNamesString(selectedOptions));
        } else if (name.equalsIgnoreCase(forms.getSectors().getName())) {
            this.selectedSectors = selectedOptions;
            fragmentBinding.sectors.setText(convertListToNamesString(selectedOptions));
        } else if (name.equalsIgnoreCase(forms.getHeadquarters().getName())) {
            this.selectedHeadquarters = selectedOptions;
            fragmentBinding.headquarters.setText(convertListToNamesString(selectedOptions));
        } else if (name.equalsIgnoreCase(forms.getCurrency().getName())) {
            this.selectedCurrency = selectedOptions;
            fragmentBinding.currency.setText(convertListToNamesString(selectedOptions));
        }
    }

    private void init() {
        selectedTopics = new ArrayList<>();
        selectedSectors = new ArrayList<>();
        selectedHeadquarters = new ArrayList<>();
        selectedCurrency = new ArrayList<>();

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
        View.OnFocusChangeListener onFocusChangeListener = (v, hasFocus) -> {
            if (hasFocus)
                clearFocus(null);
        };

        fragmentBinding.presentation.addTextChangedListener(textWatcher);
        fragmentBinding.presentation.setOnFocusChangeListener(onFocusChangeListener);
        fragmentBinding.companySize.addTextChangedListener(textWatcher);
        fragmentBinding.companySize.setOnFocusChangeListener(onFocusChangeListener);
        fragmentBinding.companySize.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
        fragmentBinding.companySize.setTransformationMethod(new NumericKeyBoardTransformationMethod());
        fragmentBinding.revenues.addTextChangedListener(textWatcher);
        fragmentBinding.revenues.setOnFocusChangeListener(onFocusChangeListener);
        fragmentBinding.revenues.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
        fragmentBinding.revenues.setTransformationMethod(new NumericKeyBoardTransformationMethod());
        fragmentBinding.products.addTextChangedListener(textWatcher);
        fragmentBinding.products.setOnFocusChangeListener(onFocusChangeListener);

        fragmentBinding.topics.addTextChangedListener(textWatcher);
        fragmentBinding.sectors.addTextChangedListener(textWatcher);
        fragmentBinding.headquarters.addTextChangedListener(textWatcher);
        fragmentBinding.currency.addTextChangedListener(textWatcher);

        fragmentBinding.profilePictureBtn.setOnClickListener(v -> {
            selectedPictureType = "profile";
            onChangeImageClick();
        });
        fragmentBinding.companyPictureBtn.setOnClickListener(v -> {
            selectedPictureType = "company";
            onChangeImageClick();
        });

        fragmentBinding.body.setOnClickListener(v -> {
            Utilities.hideSoftKeyboard(requireContext(), requireView());
            clearFocus(null);
        });

        fragmentBinding.nextBtn.setOnClickListener(v -> updateProfile());
    }

    private void initProfileForm() {
        fragmentBinding.loader.setVisibility(View.VISIBLE);
        viewModel.initProfileForm(preferenceManager.getValue(Constants.TOKEN, ""), preferenceManager.getValue(Constants.LANGUAGE, preferenceManager.getValue(Constants.LANGUAGE, "fr")));
    }

    private void handleInitProfileFormData(Resource<InitProfileFormData> responseData) {
        switch (responseData.status) {
            case SUCCESS:
                if (update) {
                    forms = responseData.data.getForms();
                    initForm();
                    getProfileDetailsForUpdate();
                } else
                    switch (responseData.data.getHeader().getSearch()) {
                        case -1:
                            fragmentBinding.loader.setVisibility(View.GONE);
                            forms = responseData.data.getForms();
                            initForm();
                            break;
                        case 0:
                            fragmentBinding.loader.setVisibility(View.GONE);
                            fragmentBinding.placeholder.setVisibility(View.VISIBLE);
                            fragmentBinding.message.setText(responseData.data.getHeader().getMessage());
                            break;
                        case 1:
                            getProfileDetails();
                            break;
                    }
                break;
            case INVALID_TOKEN:
                fragmentBinding.loader.setVisibility(View.GONE);
                Utilities.showServerErrorDialog(requireContext(), responseData.data.getHeader().getMessage(), v -> {
                    preferenceManager.clearValue(Constants.TOKEN);
                    ((DashboardActivity) requireActivity()).tokenExpired();
                });
                break;
            case ERROR:
                fragmentBinding.loader.setVisibility(View.GONE);
                Utilities.showServerErrorDialog(getContext(), responseData.message);
                break;
        }
    }

    private void initForm() {
        fragmentBinding.topics.setHint(forms.getTopics().getWording());
        fragmentBinding.presentation.setHint(forms.getPresentation().getWording());
        fragmentBinding.sectors.setHint(forms.getSectors().getWording());
        fragmentBinding.headquarters.setHint(forms.getHeadquarters().getWording());
        fragmentBinding.companySize.setHint(forms.getCompanySize().getWording());
        fragmentBinding.products.setHint(forms.getProducts().getWording());
        fragmentBinding.revenues.setHint(forms.getRevenues().getWording());
        fragmentBinding.currency.setHint(forms.getCurrency().getWording());

        if (!update) {
            initFormInputs(fragmentBinding.topics, fragmentBinding.topicsRecycler, fragmentBinding.topicsList, forms.getTopics().getType(), forms.getTopics().getName(), forms.getTopics().getReferenceObjectValues(), forms.getTopics().getMax());
            initFormInputs(fragmentBinding.sectors, fragmentBinding.sectorsRecycler, fragmentBinding.sectorsList, forms.getSectors().getType(), forms.getSectors().getName(), forms.getSectors().getReferenceObjectValues(), forms.getSectors().getMax());
            initFormInputs(fragmentBinding.headquarters, fragmentBinding.headquartersRecycler, fragmentBinding.headquartersList, forms.getHeadquarters().getType(), forms.getHeadquarters().getName(), forms.getHeadquarters().getReferenceObjectValues(), forms.getHeadquarters().getMax());
            initFormInputs(fragmentBinding.currency, fragmentBinding.currencyRecycler, fragmentBinding.currencyList, forms.getCurrency().getType(), forms.getCurrency().getName(), forms.getCurrency().getReferenceObjectValues(), forms.getCurrency().getMax());
        }

        if (!update)
            fragmentBinding.updateView.setVisibility(View.VISIBLE);
    }

    private void initFormInputs(TextView widget, RecyclerView recyclerView, NestedScrollView scrollView, String type, String name, List<Item> items, int max) {
        switch (type) {
            case "select":
                recyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
                recyclerView.setAdapter(new FormSelectOptionsAdapter(name, items, max, this));
                recyclerView.setNestedScrollingEnabled(false);

                widget.setOnFocusChangeListener((v, hasFocus) -> {
                    if (hasFocus) {
                        clearFocus(widget);
                        Utilities.hideSoftKeyboard(requireContext(), requireView());
                        scrollView.setVisibility(View.VISIBLE);
                    }
                });

                widget.setOnClickListener(v -> {
                    if (scrollView.getVisibility() == View.VISIBLE) {
                        widget.clearFocus();
                        scrollView.setVisibility(View.GONE);
                        scrollView.fullScroll(View.SCROLL_INDICATOR_TOP);
                    }
                });
                break;
            case "text":
                widget.setInputType(InputType.TYPE_CLASS_TEXT);
                break;
            case "number":
                widget.setInputType(InputType.TYPE_CLASS_NUMBER);
                break;
        }
    }

    private void clearFocus(TextView widget) {
        if (fragmentBinding.topics != widget) {
            fragmentBinding.topics.clearFocus();
            fragmentBinding.topicsList.setVisibility(View.GONE);
            //fragmentBinding.topicsList.fullScroll(View.SCROLL_INDICATOR_TOP);
        }
        if (fragmentBinding.sectors != widget) {
            fragmentBinding.sectors.clearFocus();
            fragmentBinding.sectorsList.setVisibility(View.GONE);
        }
        if (fragmentBinding.headquarters != widget) {
            fragmentBinding.headquarters.clearFocus();
            fragmentBinding.headquartersList.setVisibility(View.GONE);
        }
        if (fragmentBinding.currency != widget) {
            fragmentBinding.currency.clearFocus();
            fragmentBinding.currencyList.setVisibility(View.GONE);
        }
    }

    private Boolean checkForm() {
        return selectedTopics.size() > 0 &&
                selectedSectors.size() > 0 &&
                selectedHeadquarters.size() > 0;
    }

    private void onChangeImageClick() {
        Dexter.withActivity(getActivity())
                .withPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            showImagePickerOptions();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }

    private void showImagePickerOptions() {
        Utilities.showUpdatePictureOptionsDialog(getContext(), new OnDialogButtonsClickListener() {
            @Override
            public void firstChoice() {
                try {
                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    cameraActivityResultLauncher.launch(takePictureIntent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void secondChoice() {
                try {
                    Intent choosePhotoIntent = new Intent(Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    galleryActivityResultLauncher.launch(choosePhotoIntent);
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Uri getImageUri(Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(requireActivity().getContentResolver(), inImage, "img", null);
        return Uri.parse(path);
    }

    private void updateProfile() {
        fragmentBinding.loader.setVisibility(View.VISIBLE);

        viewModel.updateProfile(RequestBody.create(MultipartBody.FORM, (preferenceManager.getValue(Constants.TOKEN, ""))),
                uriToMultipartBody(profilePictureUri, "pictureProfil"),
                uriToMultipartBody(companyPictureUri, "pictureEntreprise"),
                RequestBody.create(MultipartBody.FORM, preferenceManager.getValue(Constants.LANGUAGE, "fr")),
                RequestBody.create(MultipartBody.FORM, "mobile"),
                RequestBody.create(MultipartBody.FORM, fragmentBinding.presentation.getText().toString()),
                RequestBody.create(MultipartBody.FORM, convertListToIdsString(selectedHeadquarters)),
                RequestBody.create(MultipartBody.FORM, convertListToIdsString(selectedSectors)),
                RequestBody.create(MultipartBody.FORM, fragmentBinding.revenues.getText().toString()),
                RequestBody.create(MultipartBody.FORM, fragmentBinding.companySize.getText().toString()),
                RequestBody.create(MultipartBody.FORM, convertListToIdsString(selectedTopics)),
                RequestBody.create(MultipartBody.FORM, convertListToIdsString(selectedCurrency)),
                RequestBody.create(MultipartBody.FORM, fragmentBinding.products.getText().toString()));
    }

    private String convertListToNamesString(List<Item> items) {
        StringBuilder text = new StringBuilder();
        for (Item item : items) {
            text.append(item.getName()).append(", ");
        }
        if (text.length() > 0)
            text.delete(text.length() - 2, text.length());
        return text.toString();
    }

    private String convertListToIdsString(List<Item> items) {
        StringBuilder text = new StringBuilder();
        for (Item item : items) {
            text.append(item.getId()).append(", ");
        }
        if (text.length() > 0)
            text.delete(text.length() - 2, text.length());
        return text.toString();
    }

    private MultipartBody.Part uriToMultipartBody(Uri fileUri, String name) {
        try {
            File file = FileUtils.getFile(getActivity(), fileUri);
            String mimeType = URLConnection.guessContentTypeFromName(file.getName());
            MediaType mediaType = MediaType.parse(mimeType);
            RequestBody requestFile = RequestBody.create(mediaType, file);
            return MultipartBody.Part.createFormData(name, file.getName(), requestFile);
        } catch (Exception e) {
            RequestBody attachmentEmpty = RequestBody.create(MediaType.parse("text/plain"), "");
            return MultipartBody.Part.createFormData(name, "", attachmentEmpty);
        }
    }

    private void handleUpdateProfileData(Resource<UpdateProfileData> responseData) {
        fragmentBinding.loader.setVisibility(View.GONE);
        switch (responseData.status) {
            case SUCCESS:
                if (update) {
                    Utilities.showServerErrorDialog(requireContext(), responseData.data.getHeader().getMessage(), v -> requireActivity().onBackPressed());
                } else {
                    fragmentBinding.updateView.setVisibility(View.GONE);
                    fragmentBinding.placeholder.setVisibility(View.VISIBLE);
                    fragmentBinding.message.setText(responseData.data.getHeader().getMessage());
                }
                break;
            case INVALID_TOKEN:
                Utilities.showServerErrorDialog(requireContext(), responseData.data.getHeader().getMessage(), v -> {
                    preferenceManager.clearValue(Constants.TOKEN);
                    ((DashboardActivity) requireActivity()).tokenExpired();
                });
                break;
            case ERROR:
                Utilities.showServerErrorDialog(requireContext(), responseData.message);
                break;
        }
    }

    private void getProfileDetails() {
        viewModel.getProfile(preferenceManager.getValue(Constants.TOKEN, ""), preferenceManager.getValue(Constants.LANGUAGE, "fr"));
    }

    private void handleGetProfileDetailsData(Resource<ProfileDetailsData> responseData) {
        fragmentBinding.loader.setVisibility(View.GONE);
        switch (responseData.status) {
            case SUCCESS:
                ((DashboardActivity) requireActivity()).replaceFragment(ProfileDetailsFragment.newInstance(responseData.data.getResults()), "");
                break;
            case INVALID_TOKEN:
                Utilities.showServerErrorDialog(requireContext(), responseData.data.getHeader().getMessage(), v -> {
                    preferenceManager.clearValue(Constants.TOKEN);
                    ((DashboardActivity) requireActivity()).tokenExpired();
                });
                break;
            case ERROR:
                Utilities.showServerErrorDialog(getContext(), responseData.message);
                break;
        }
    }

    private void getProfileDetailsForUpdate() {
        fragmentBinding.loader.setVisibility(View.VISIBLE);
        viewModel.getProfileForUpdate(preferenceManager.getValue(Constants.TOKEN, ""), preferenceManager.getValue(Constants.LANGUAGE, "fr"));
    }

    private void handleGetProfileForUpdateData(Resource<ProfileDetailsForUpdateData> responseData) {
        switch (responseData.status) {
            case SUCCESS:
                initProfileDetails(responseData.data.getResults());
                break;
            case INVALID_TOKEN:
                Utilities.showServerErrorDialog(requireContext(), responseData.data.getHeader().getMessage(), v -> {
                    preferenceManager.clearValue(Constants.TOKEN);
                    ((DashboardActivity) requireActivity()).tokenExpired();
                });
                break;
            case ERROR:
                Utilities.showServerErrorDialog(getContext(), responseData.message);
                break;
        }
    }

    private void initProfileDetails(Results results) {
        Glide.with(requireContext()).load(results.getProfilePicture()).into(fragmentBinding.profilePicture);
        Glide.with(requireContext()).load(results.getCompanyPicture()).into(fragmentBinding.companyPicture);

        fragmentBinding.presentation.setText(results.getPresentation());
        fragmentBinding.companySize.setText(results.getCompanySize());
        fragmentBinding.products.setText(results.getProducts());
        fragmentBinding.revenues.setText(results.getRevenues());

        initFormInputs(fragmentBinding.topics, fragmentBinding.topicsRecycler, fragmentBinding.topicsList, forms.getTopics().getType(), forms.getTopics().getName(), results.getTopics(), forms.getTopics().getMax());
        initFormInputs(fragmentBinding.sectors, fragmentBinding.sectorsRecycler, fragmentBinding.sectorsList, forms.getSectors().getType(), forms.getSectors().getName(), results.getSectors(), forms.getSectors().getMax());
        initFormInputs(fragmentBinding.headquarters, fragmentBinding.headquartersRecycler, fragmentBinding.headquartersList, forms.getHeadquarters().getType(), forms.getHeadquarters().getName(), results.getHeadquarter(), forms.getHeadquarters().getMax());
        initFormInputs(fragmentBinding.currency, fragmentBinding.currencyRecycler, fragmentBinding.currencyList, forms.getCurrency().getType(), forms.getCurrency().getName(), results.getCurrency(), forms.getCurrency().getMax());

        fragmentBinding.loader.setVisibility(View.GONE);
        fragmentBinding.updateView.setVisibility(View.VISIBLE);

        fragmentBinding.nextBtn.setEnabled(false);
    }
}