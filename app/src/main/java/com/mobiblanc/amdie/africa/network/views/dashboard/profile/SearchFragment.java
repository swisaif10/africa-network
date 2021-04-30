package com.mobiblanc.amdie.africa.network.views.dashboard.profile;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.mobiblanc.amdie.africa.network.BuildConfig;
import com.mobiblanc.amdie.africa.network.R;
import com.mobiblanc.amdie.africa.network.databinding.FragmentSearchBinding;
import com.mobiblanc.amdie.africa.network.datamanager.sharedpref.PreferenceManager;
import com.mobiblanc.amdie.africa.network.listeners.OnDialogButtonsClickListener;
import com.mobiblanc.amdie.africa.network.models.search.init_montoring.InitMontoringData;
import com.mobiblanc.amdie.africa.network.models.search.update_mentore.UpdateMentoreData;
import com.mobiblanc.amdie.africa.network.utilities.Constants;
import com.mobiblanc.amdie.africa.network.utilities.FileUtils;
import com.mobiblanc.amdie.africa.network.utilities.Resource;
import com.mobiblanc.amdie.africa.network.utilities.Utilities;
import com.mobiblanc.amdie.africa.network.viewmodels.SearchViewModel;
import com.mobiblanc.amdie.africa.network.views.dashboard.DashboardActivity;
import com.mobiblanc.amdie.africa.network.views.dashboard.detail_search.DetailSearchFragment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static android.app.Activity.RESULT_OK;


public class SearchFragment extends Fragment implements CheckedLisner {

    private FragmentSearchBinding fragmentBinding;
    private PreferenceManager preferenceManager;
    private SearchViewModel viewModel;
    public static final int REQUEST_IMAGE = 100;
    private static final String[] CAMERA_PERMISSION = new String[]{Manifest.permission.CAMERA};
    private static final int CAMERA_REQUEST_CODE = 10;
    private static final int REQUEST_IMAGE_CAPTURE = 20;
    private static final int REQUEST_IMAGE_CAPTURE_PROFILE = 1;
    private static final int REQUEST_IMAGE_CAPTURE_PROFILE_CAMERA = 2;
    private static final int REQUEST_IMAGE_CAPTURE_PROFILE_GALL = 3;
    private static final int REQUEST_IMAGE_CAPTURE_ENTRPRISE = 20;
    private static final int REQUEST_IMAGE_CAPTURE_ENTRPRISE_CAMERA = 21;
    private static final int REQUEST_IMAGE_CAPTURE_ENTRPRISE_GALL = 22;
    String topicS = "", presentationS = "", siegeS = "", secteurS = "", chiffredaffaireS = "", effectifS = "", deviseS = "", produitS = "";
    Uri profile_img, entreprise_img;
    Boolean checked_topics = false, checked_secteur = false, checked_siege = false, checked_devise = false, checked_produits = false, checked_effectif = false;
    private File file = null;
    private String path_profile, path_entreprise;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(SearchViewModel.class);
        viewModel.getInitMontoringLiveData().observe(this, this::handleGetIniiMontorData);

        preferenceManager = new PreferenceManager.Builder(Objects.requireNonNull(getContext()), Context.MODE_PRIVATE)
                .name(BuildConfig.APPLICATION_ID)
                .build();
        viewModel.getUpdateMentoreLiveData().observe(requireActivity(), this::handleUpdateMentoreData);
        Log.e("token ", preferenceManager.getValue(Constants.TOKEN, ""));
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentBinding = FragmentSearchBinding.inflate(inflater, container, false);
        return fragmentBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getInitMontoring();
        init();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            switch (requestCode) {
                case REQUEST_IMAGE_CAPTURE_PROFILE_CAMERA:
                    Bitmap imageBitmap = (Bitmap) extras.get("data");
                    fragmentBinding.imgProfil.setImageBitmap(imageBitmap);
                    profile_img = getImageUri(imageBitmap);
                    ;//convert(imageBitmap);
                    try {
                        path_profile = savebitmap(imageBitmap).getAbsolutePath();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case REQUEST_IMAGE_CAPTURE_PROFILE_GALL:
                    Uri selectedImage = data.getData();
                    path_profile = selectedImage.getEncodedPath();
                    fragmentBinding.imgProfil.setImageURI(selectedImage);
                    profile_img = selectedImage;//uriToBase64(selectedImage);
                    break;
                case REQUEST_IMAGE_CAPTURE_ENTRPRISE_CAMERA:
                    Bitmap imageBitmape = (Bitmap) extras.get("data");
                    fragmentBinding.imgEntreprise.setImageBitmap(imageBitmape);
                    try {
                        path_entreprise = savebitmap(imageBitmape).getAbsolutePath();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    entreprise_img = getImageUri(imageBitmape); //convert(imageBitmape);
                    break;
                case REQUEST_IMAGE_CAPTURE_ENTRPRISE_GALL:
                    Uri selectedImagee = data.getData();
                    path_entreprise = selectedImagee.getEncodedPath();
                    fragmentBinding.imgEntreprise.setImageURI(selectedImagee);
                    entreprise_img = selectedImagee;// uriToBase64(selectedImagee);
                    break;
            }

        }
        fragmentBinding.nextBtn.setEnabled(checkForm());
    }

    private void init() {

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

        fragmentBinding.presentation.addTextChangedListener(textWatcher);
        fragmentBinding.topics.addTextChangedListener(textWatcher);
        fragmentBinding.sector.addTextChangedListener(textWatcher);
        fragmentBinding.headquarters.addTextChangedListener(textWatcher);
        fragmentBinding.squad.addTextChangedListener(textWatcher);
        fragmentBinding.turnover.addTextChangedListener(textWatcher);
        fragmentBinding.products.addTextChangedListener(textWatcher);
        fragmentBinding.devise.addTextChangedListener(textWatcher);

        fragmentBinding.btnPhotoEntreprise.setOnClickListener(v -> onProfileImageClick(REQUEST_IMAGE_CAPTURE_ENTRPRISE));
        fragmentBinding.profilePictureBtn.setOnClickListener(v -> onProfileImageClick(REQUEST_IMAGE_CAPTURE_PROFILE));

        fragmentBinding.nextBtn.setOnClickListener(v -> updateProfile());


    }

    private void getInitMontoring() {
        viewModel.getInitOntoring(preferenceManager.getValue(Constants.TOKEN, ""), preferenceManager.getValue(Constants.LANGUAGE, "fr"));
    }

    private void dispatchTakePictureIntent(int i) {
        if (ContextCompat.checkSelfPermission(Objects.requireNonNull(getContext()), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            try {
                startActivityForResult(takePictureIntent, i);
            } catch (ActivityNotFoundException e) {
                // display error state to the user
            }
        } else {
            ActivityCompat.requestPermissions(Objects.requireNonNull(getActivity()), CAMERA_PERMISSION, CAMERA_REQUEST_CODE);
        }


    }

    public static File savebitmap(Bitmap bmp) throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 60, bytes);
        File f = new File(Environment.getExternalStorageDirectory()
                + File.separator + "africa.jpg");
        f.createNewFile();
        FileOutputStream fo = new FileOutputStream(f);
        fo.write(bytes.toByteArray());
        fo.close();
        return f;
    }

    public static String convert(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);

        return Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
    }

    public MultipartBody.Part uriToMultipartBody(Uri fileUri, String name) {

        File file = FileUtils.getFile(getActivity(), fileUri);
        // create RequestBody instance from file
        String mimeType = URLConnection.guessContentTypeFromName(file.getName());
        // MediaType mediaType=MediaType.parse(mContext.getApplicationContext().getContentResolver().getType(fileUri));
        MediaType mediaType = MediaType.parse(mimeType);
        RequestBody requestFile = RequestBody.create(mediaType, file);
        MultipartBody.Part body = MultipartBody.Part.createFormData(name, file.getName(), requestFile);
        return body;
    }

    public Uri getImageUri(Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(getActivity().getContentResolver(), inImage, "img", null);
        return Uri.parse(path);
    }

    public String uriToBase64(Uri uri) {
        final InputStream imageStream;
        String s = "";
        try {
            imageStream = getContext().getContentResolver().openInputStream(uri);
            final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
            s = convert(selectedImage);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return s;
    }

    private Boolean checkForm() {
        if (!checked_effectif) {
            effectifS = fragmentBinding.squad.getText().toString();
        }

        return !Utilities.isEmpty(fragmentBinding.presentation) &&
                checked_topics &&
                checked_secteur &&
                checked_siege &&
                !Utilities.isEmpty(fragmentBinding.squad) &&
                !Utilities.isEmpty(fragmentBinding.turnover) &&
                !Utilities.isEmpty(fragmentBinding.products) &&
                checked_devise &&
                !Utilities.isEmpty(fragmentBinding.products) &&
                profile_img != null &&
                entreprise_img != null;

    }

    private void updateProfile() {

        RequestBody token = RequestBody.create(MultipartBody.FORM, (preferenceManager.getValue(Constants.TOKEN, "")));

        viewModel.updateMentore(token,
                uriToMultipartBody(profile_img, "pictureProfil"),
                uriToMultipartBody(entreprise_img, "pictureEntreprise"),
                RequestBody.create(MultipartBody.FORM, preferenceManager.getValue(Constants.LANGUAGE, "fr")),
                RequestBody.create(MultipartBody.FORM, "mobile"),
                RequestBody.create(MultipartBody.FORM, fragmentBinding.presentation.getText().toString()),
                RequestBody.create(MultipartBody.FORM, siegeS),
                RequestBody.create(MultipartBody.FORM, secteurS),
                RequestBody.create(MultipartBody.FORM, fragmentBinding.turnover.getText().toString()),
                RequestBody.create(MultipartBody.FORM, effectifS),
                RequestBody.create(MultipartBody.FORM, topicS),
                RequestBody.create(MultipartBody.FORM, deviseS),
                RequestBody.create(MultipartBody.FORM, produitS));
    }

    private void handleUpdateMentoreData(Resource<UpdateMentoreData> responseData) {
        switch (responseData.status) {
            case SUCCESS:
                ((DashboardActivity) requireActivity()).replaceFragment(new ValidationFragment(), "");
                break;
            case INVALID_TOKEN:
                break;
            case ERROR:
                Utilities.showErrorPopup(requireContext(), responseData.message);
                break;
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void handleGetIniiMontorData(Resource<InitMontoringData> responseData) {
        switch (responseData.status) {
            case SUCCESS:
                if (responseData.data.getHeader().getSearch() == -1) {

//                    fragmentBinding.txtPhotoEntreprise.setText(responseData.data.getForms().getPictureEntreprise().getLibelle());
//                    fragmentBinding.txtPhotoProfil.setText(responseData.data.getForms().getPictureProfil().getLibelle());

                    fragmentBinding.topics.setHint(responseData.data.getForms().getTopics().getLibelle());
                    fragmentBinding.presentation.setHint(responseData.data.getForms().getPresentation().getLibelle());
                    fragmentBinding.sector.setHint(responseData.data.getForms().getSecteur().getLibelle());
                    fragmentBinding.headquarters.setHint(responseData.data.getForms().getSiege().getLibelle());
                    fragmentBinding.squad.setHint(responseData.data.getForms().getEffectif().getLibelle());
                    fragmentBinding.products.setHint(responseData.data.getForms().getProduits().getLibelle());
                    fragmentBinding.turnover.setHint(responseData.data.getForms().getChiffredaffaire().getLibelle());


                    setFormat_topics(responseData);
                    setFormat_presentation(responseData, fragmentBinding.presentation);
                    setFormat_sector(responseData);
                    setFormat_siege(responseData);
                    setFormat_effectif(responseData, fragmentBinding.squad);
                    setFormat_products(responseData);
                    setFormat_Chiffredaffaire(responseData, fragmentBinding.turnover);
                    setFormat_devise(responseData);
                    fragmentBinding.updateView.setVisibility(View.VISIBLE);


                } else if (responseData.data.getHeader().getSearch() == 0) {
                    ((DashboardActivity) getActivity()).replaceFragment(new ValidationFragment(), "");
                } else if (responseData.data.getHeader().getSearch() == 1) {
                    ((DashboardActivity) getActivity()).replaceFragment(new DetailSearchFragment(), "");
                }

                break;
            case INVALID_TOKEN:
                break;
            case ERROR:
                Utilities.showErrorPopup(getContext(), responseData.message);
                break;
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setFormat_topics(Resource<InitMontoringData> responseData) {

        switch (responseData.data.getForms().getTopics().getType()) {
            case "select":
                LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false);

                fragmentBinding.recyclerviewTopic.setLayoutManager(layoutManager);

                Adapter_search_topics adapter = new Adapter_search_topics(requireContext(), responseData.data.getForms().getTopics(), this);
                fragmentBinding.recyclerviewTopic.setAdapter(adapter);
                fragmentBinding.recyclerviewTopic.setItemViewCacheSize(100);
                fragmentBinding.recyclerviewTopic.setDrawingCacheEnabled(true);
                fragmentBinding.recyclerviewTopic.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
                fragmentBinding.topics.setOnClickListener(v -> {
                    if (fragmentBinding.cardTopic.getVisibility() == View.VISIBLE) {
                        fragmentBinding.cardTopic.setVisibility(View.GONE);
                    } else {
                        Utilities.hideSoftKeyboard(requireContext(), requireView());
                        fragmentBinding.cardTopic.setVisibility(View.VISIBLE);
                    }
                });

                break;
            case "text":
                fragmentBinding.topics.setInputType(InputType.TYPE_CLASS_TEXT);
                break;
            case "number":
                fragmentBinding.topics.setInputType(InputType.TYPE_CLASS_NUMBER);
                break;

        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setFormat_presentation(Resource<InitMontoringData> responseData, TextView appCompatAutoCompleteTextView) {

        if (!responseData.data.getForms().getPresentation().getType().equals("select")) {
            fragmentBinding.presentation.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    presentationS = s.toString();
                }
            });
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setFormat_sector(Resource<InitMontoringData> responseData) {

        switch (responseData.data.getForms().getSecteur().getType()) {
            case "select":

                LinearLayoutManager layoutManager
                        = new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false);

                fragmentBinding.recyclerviewSecteur.setLayoutManager(layoutManager);

                Adapter_search_Secteur adapter = new Adapter_search_Secteur(requireContext(), responseData.data.getForms().getSecteur(), this);
                fragmentBinding.recyclerviewSecteur.setAdapter(adapter);
                fragmentBinding.recyclerviewSecteur.setItemViewCacheSize(100);
                fragmentBinding.recyclerviewSecteur.setDrawingCacheEnabled(true);
                fragmentBinding.recyclerviewSecteur.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

                fragmentBinding.sector.setOnClickListener(v -> {
                    if (fragmentBinding.cardSecteur.getVisibility() == View.VISIBLE) {
                        fragmentBinding.cardSecteur.setVisibility(View.GONE);
                    } else {
                        Utilities.hideSoftKeyboard(requireContext(), requireView());
                        fragmentBinding.cardSecteur.setVisibility(View.VISIBLE);
                    }
                });


                break;
            case "text":
                fragmentBinding.sector.setInputType(InputType.TYPE_CLASS_TEXT);
                break;
            case "number":
                fragmentBinding.sector.setInputType(InputType.TYPE_CLASS_NUMBER);
                break;
        }
        if (!responseData.data.getForms().getSecteur().getType().equals("select")) {
            fragmentBinding.sector.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    secteurS = s.toString();
                }
            });
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setFormat_siege(Resource<InitMontoringData> responseData) {

        switch (responseData.data.getForms().getSiege().getType()) {
            case "select":
                LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false);

                fragmentBinding.recyclerviewSiege.setLayoutManager(layoutManager);

                Adapter_search_Siege adapter = new Adapter_search_Siege(requireContext(), responseData.data.getForms().getSiege(), this);
                fragmentBinding.recyclerviewSiege.setAdapter(adapter);
                fragmentBinding.recyclerviewSiege.setItemViewCacheSize(100);
                fragmentBinding.recyclerviewSiege.setDrawingCacheEnabled(true);
                fragmentBinding.recyclerviewSiege.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
                fragmentBinding.headquarters.setOnClickListener(v -> {
                    if (fragmentBinding.cardSiege.getVisibility() == View.VISIBLE) {
                        fragmentBinding.cardSiege.setVisibility(View.GONE);
                    } else {
                        Utilities.hideSoftKeyboard(requireContext(), requireView());
                        fragmentBinding.cardSiege.setVisibility(View.VISIBLE);
                    }
                });


                break;
            case "text":
                fragmentBinding.headquarters.setInputType(InputType.TYPE_CLASS_TEXT);
                break;
            case "number":
                fragmentBinding.headquarters.setInputType(InputType.TYPE_CLASS_NUMBER);
                break;

        }

    }

    @SuppressLint("ClickableViewAccessibility")
    private void setFormat_effectif(Resource<InitMontoringData> responseData, androidx.appcompat.widget.AppCompatAutoCompleteTextView appCompatAutoCompleteTextView) {
        switch (responseData.data.getForms().getEffectif().getType()) {
            case "select":
                LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false);

                fragmentBinding.recyclerviewEffectif.setLayoutManager(layoutManager);

                Adapter_search_Effectif adapter = new Adapter_search_Effectif(requireContext(), responseData.data.getForms().getEffectif(), this);
                fragmentBinding.recyclerviewEffectif.setAdapter(adapter);
                fragmentBinding.recyclerviewEffectif.setItemViewCacheSize(100);
                fragmentBinding.recyclerviewEffectif.setDrawingCacheEnabled(true);
                fragmentBinding.recyclerviewEffectif.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
                fragmentBinding.squad.setOnClickListener(v -> {
                    if (fragmentBinding.cardEffectif.getVisibility() == View.VISIBLE) {
                        fragmentBinding.cardEffectif.setVisibility(View.GONE);
                    } else {
                        Utilities.hideSoftKeyboard(requireContext(), requireView());
                        fragmentBinding.cardEffectif.setVisibility(View.VISIBLE);
                    }
                });


                break;
            case "text":
                fragmentBinding.squad.setInputType(InputType.TYPE_CLASS_TEXT);
                break;
            case "number":
                fragmentBinding.squad.setInputType(InputType.TYPE_CLASS_NUMBER);
                break;

        }
        if (!responseData.data.getForms().getEffectif().getType().equals("select")) {
            fragmentBinding.squad.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    effectifS = s.toString();
                }
            });
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setFormat_products(Resource<InitMontoringData> responseData) {
        switch (responseData.data.getForms().getProduits().getType()) {
            case "select":
                LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false);

                fragmentBinding.recyclerviewProduits.setLayoutManager(layoutManager);

                Adapter_search_produits adapter = new Adapter_search_produits(requireContext(), responseData.data.getForms().getProduits(), this);
                fragmentBinding.recyclerviewProduits.setAdapter(adapter);
                fragmentBinding.recyclerviewProduits.setItemViewCacheSize(100);
                fragmentBinding.recyclerviewProduits.setDrawingCacheEnabled(true);
                fragmentBinding.recyclerviewProduits.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
                fragmentBinding.products.setOnClickListener(v -> {
                    if (fragmentBinding.cardProduits.getVisibility() == View.VISIBLE) {
                        fragmentBinding.cardProduits.setVisibility(View.GONE);
                    } else {
                        Utilities.hideSoftKeyboard(requireContext(), requireView());
                        fragmentBinding.cardProduits.setVisibility(View.VISIBLE);
                    }
                });


                break;
            case "text":
                fragmentBinding.products.setInputType(InputType.TYPE_CLASS_TEXT);
                break;
            case "number":
                fragmentBinding.products.setInputType(InputType.TYPE_CLASS_NUMBER);
                break;

        }
        if (!responseData.data.getForms().getProduits().getType().equals("select")) {
            fragmentBinding.products.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    produitS = s.toString();
                }
            });
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setFormat_Chiffredaffaire(Resource<InitMontoringData> responseData, androidx.appcompat.widget.AppCompatAutoCompleteTextView appCompatAutoCompleteTextView) {
        switch (responseData.data.getForms().getChiffredaffaire().getType()) {
            case "select":
                List<String> list = new ArrayList<>();
                for (int i = 0; i < responseData.data.getForms().getChiffredaffaire().getObjetReferenceValues().size(); i++) {
                    list.add(responseData.data.getForms().getChiffredaffaire().getObjetReferenceValues().get(i).getName());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), R.layout.custom_dropdown_item_layout, list);
                appCompatAutoCompleteTextView.setAdapter(adapter);
                appCompatAutoCompleteTextView.setCursorVisible(false);
                appCompatAutoCompleteTextView.setFocusableInTouchMode(false);
//                if (responseData.data.getForms().getChiffredaffaire().getMax()!=1)
//                appCompatAutoCompleteTextView .setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
                appCompatAutoCompleteTextView.setOnTouchListener((v, event) -> {
                    appCompatAutoCompleteTextView.showDropDown();
                    Utilities.hideSoftKeyboard(requireContext(), requireView());
                    return false;
                });
                appCompatAutoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        chiffredaffaireS = responseData.data.getForms().getChiffredaffaire().getObjetReferenceValues().get(position).getId() + "";
                    }
                });
                break;
            case "text":
                fragmentBinding.turnover.setInputType(InputType.TYPE_CLASS_TEXT);
                break;
            case "number":
                fragmentBinding.turnover.setInputType(InputType.TYPE_CLASS_NUMBER);
                break;

        }
        if (!responseData.data.getForms().getChiffredaffaire().getType().equals("select")) {
            fragmentBinding.turnover.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    chiffredaffaireS = s.toString();
                }
            });
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setFormat_devise(Resource<InitMontoringData> responseData) {

        switch (responseData.data.getForms().getDevise().getType()) {
            case "select":
                LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false);

                fragmentBinding.recyclerviewDevise.setLayoutManager(layoutManager);

                Adapter_search_Devise adapter = new Adapter_search_Devise(requireContext(), responseData.data.getForms().getDevise(), this);
                fragmentBinding.recyclerviewDevise.setAdapter(adapter);

                fragmentBinding.devise.setOnClickListener(v -> {
                    if (fragmentBinding.cardDevise.getVisibility() == View.VISIBLE) {
                        fragmentBinding.cardDevise.setVisibility(View.GONE);
                    } else {
                        Utilities.hideSoftKeyboard(requireContext(), requireView());
                        fragmentBinding.cardDevise.setVisibility(View.VISIBLE);
                        fragmentBinding.cardDevise.requestFocus();

                    }
                });

                break;
            case "text":
                fragmentBinding.devise.setInputType(InputType.TYPE_CLASS_TEXT);
                break;
            case "number":
                fragmentBinding.devise.setInputType(InputType.TYPE_CLASS_NUMBER);
                break;

        }

    }

    void onProfileImageClick(int ss) {
        Dexter.withActivity(getActivity())
                .withPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            showImagePickerOptions(ss);
                        } else {

                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }

    private void showImagePickerOptions(int ss) {
        Utilities.showPhotoDialog(getContext(), new OnDialogButtonsClickListener() {
            @Override
            public void firstChoice() {
                dispatchTakePictureIntent(ss + 1);
            }

            @Override
            public void secondChoice() {
                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto, ss + 2);
            }
        });

    }

    @Override
    public void Checked_Topics(Boolean checked, String list_id, String list_name) {
        checked_topics = checked;
        topicS = list_id;
        fragmentBinding.topics.setText(list_name);

    }

    @Override
    public void Checked_Secteur(Boolean checked, String list_id, String list_name) {
        checked_secteur = checked;
        secteurS = list_id;
        fragmentBinding.sector.setText(list_name);
    }

    @Override
    public void Checked_Siege(Boolean checked, String list_id, String list_name) {
        checked_siege = checked;
        siegeS = list_id;
        fragmentBinding.headquarters.setText(list_name);
    }

    @Override
    public void Checked_Devise(Boolean checked, String list_id, String list_name) {
        checked_devise = checked;
        deviseS = list_id;
        fragmentBinding.devise.setText(list_name);
    }

    @Override
    public void Checked_Produits(Boolean checked, String list_id, String list_name) {
        checked_produits = checked;
        produitS = list_id;
        fragmentBinding.products.setText(list_name);
    }

    @Override
    public void Checked_Effectif(Boolean checked, String list_id, String list_name) {
        checked_effectif = checked;
        effectifS = list_id;
        fragmentBinding.squad.setText(list_name);
    }
}