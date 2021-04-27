package com.mobiblanc.amdie.africa.network.views.dashboard.detail_search;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.bumptech.glide.Glide;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.mobiblanc.amdie.africa.network.BuildConfig;
import com.mobiblanc.amdie.africa.network.R;
import com.mobiblanc.amdie.africa.network.Utilities.Constants;
import com.mobiblanc.amdie.africa.network.Utilities.Resource;
import com.mobiblanc.amdie.africa.network.Utilities.Utilities;
import com.mobiblanc.amdie.africa.network.databinding.FragmentContactDetailsBinding;
import com.mobiblanc.amdie.africa.network.databinding.FragmentDetailSearchBinding;
import com.mobiblanc.amdie.africa.network.databinding.FragmentSearchBinding;
import com.mobiblanc.amdie.africa.network.datamanager.sharedpref.PreferenceManager;
import com.mobiblanc.amdie.africa.network.models.search.init_montoring.InitMontoringData;
import com.mobiblanc.amdie.africa.network.models.search.profile.Profile;
import com.mobiblanc.amdie.africa.network.models.search.profile.TopicsItem;
import com.mobiblanc.amdie.africa.network.viewmodels.DetailSearchViewModel;
import com.mobiblanc.amdie.africa.network.viewmodels.SearchViewModel;
import com.mobiblanc.amdie.africa.network.views.dashboard.DashboardActivity;
import com.mobiblanc.amdie.africa.network.views.dashboard.search.ValidationFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailSearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailSearchFragment extends Fragment {
    DetailSearchViewModel viewModel;
    private PreferenceManager preferenceManager;
    FragmentDetailSearchBinding binding;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Dialog loading;

    public DetailSearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DetailSearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetailSearchFragment newInstance(String param1, String param2) {
        DetailSearchFragment fragment = new DetailSearchFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        loading = new Dialog(requireContext(), android.R.style.Theme_Translucent_NoTitleBar);
        View view = LayoutInflater.from(requireContext()).inflate(R.layout.loader, null, false);
        loading.setContentView(view);
        viewModel = ViewModelProviders.of(this).get(DetailSearchViewModel.class);
        viewModel.getInitProfileLiveData().observe(this, this::handleGetIniiProfileData);

        preferenceManager = new PreferenceManager.Builder(Objects.requireNonNull(getContext()), Context.MODE_PRIVATE)
                .name(BuildConfig.APPLICATION_ID)
                .build();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDetailSearchBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loading.show();
        getInitProfile();
    }

    private void getInitProfile() {
        viewModel.getInitProfile(preferenceManager.getValue(Constants.TOKEN, ""), "fr");
    }

    @SuppressLint("ClickableViewAccessibility")
    private void handleGetIniiProfileData(Resource<Profile> responseData) {
        switch (responseData.status) {
            case SUCCESS:
                Glide.with(this).load(responseData.data.getResults().getPictureEntreprise()).into(binding.icon);
                Glide.with(this).load(responseData.data.getResults().getPictureProfil()).into(binding.profilePicture);

                if (responseData.data.getResults().getDevise().get(0).getName().equals("Dollars")) {
                    binding.chiffreAffer.setText(responseData.data.getResults().getChiffredaffaire() + " $");
                } else {
                    binding.chiffreAffer.setText(responseData.data.getResults().getChiffredaffaire() + " €");
                }
                binding.country.setText(responseData.data.getResults().getProvince());
                binding.effectif.setText(responseData.data.getResults().getEffectif());
                binding.fonction.setText(responseData.data.getResults().getFonction());
                binding.nomentreprise.setText(responseData.data.getResults().getNomentreprise());
                binding.presentation.setText(responseData.data.getResults().getPresentation());
                binding.username.setText(responseData.data.getResults().getUsername());
                binding.produits.setText(responseData.data.getResults().getProduits());
                setFormat_secteurs(responseData);

                LinearLayoutManager layoutManager
                        = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);

                binding.recyclerviewTopic.setLayoutManager(layoutManager);
                List<TopicsItem> list = new ArrayList<>();
                for (int i = 0; i < responseData.data.getResults().getTopics().size(); i++) {
                    list.add(responseData.data.getResults().getTopics().get(i));
                }
                Adapter adapter = new Adapter(list);
                binding.recyclerviewTopic.setAdapter(adapter);

                break;
            case LOADING:
                break;
            case ERROR:
                Utilities.showErrorPopup(getContext(), responseData.message);
                break;
        }


        binding.view.setVisibility(View.VISIBLE);
        loading.dismiss();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setFormat_secteurs(Resource<Profile> responseData) {

        List<String> list = new ArrayList<>();
        for (int i = 0; i < responseData.data.getResults().getSecteur().size(); i++) {
            list.add(responseData.data.getResults().getSecteur().get(i).getName());
        }
        Adapter_topic adapter = new Adapter_topic(list);
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(getContext());
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setJustifyContent(JustifyContent.FLEX_START);
        binding.recyclerView.setLayoutManager(layoutManager);
        binding.recyclerView.setAdapter(adapter);


    }

}