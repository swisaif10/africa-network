package com.mobiblanc.amdie.africa.network.views.dashboard.profile.details;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.mobiblanc.amdie.africa.network.R;
import com.mobiblanc.amdie.africa.network.databinding.FragmentProfileDetailsBinding;
import com.mobiblanc.amdie.africa.network.models.profile.details.Results;
import com.mobiblanc.amdie.africa.network.views.dashboard.DashboardActivity;

import java.text.MessageFormat;

public class ProfileDetailsFragment extends Fragment {

    private FragmentProfileDetailsBinding fragmentBinding;
    private Results results;

    public ProfileDetailsFragment() {
        // Required empty public constructor
    }

    public static ProfileDetailsFragment newInstance(Results results) {
        ProfileDetailsFragment fragment = new ProfileDetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable("data", results);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null)
            results = (Results) getArguments().getSerializable("data");
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentBinding = FragmentProfileDetailsBinding.inflate(inflater, container, false);
        return fragmentBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    @Override
    public void onPause() {
        super.onPause();
        ((DashboardActivity) requireActivity()).showHideEditBtn(View.GONE);
    }

    private void init() {
        ((DashboardActivity) requireActivity()).showHideEditBtn(View.VISIBLE);

        Glide.with(this).load(results.getCompanyPicture()).placeholder(R.drawable.avatar_office).into(fragmentBinding.companyPicture);
        if (results.getGender().equalsIgnoreCase("man"))
            Glide.with(this).load(results.getProfilePicture()).placeholder(R.drawable.men_avatar).into(fragmentBinding.profilePicture);
        else
            Glide.with(this).load(results.getProfilePicture()).placeholder(R.drawable.women_avatar).into(fragmentBinding.profilePicture);

        if (!results.getCurrency().isEmpty())
            if (results.getCurrency().get(0).getName().equals("Dollars")) {
                fragmentBinding.revenues.setText(MessageFormat.format("{0} $", results.getRevenues()));
            } else {
                fragmentBinding.revenues.setText(MessageFormat.format("{0} €", results.getRevenues()));
            }
        else
            fragmentBinding.revenuesLayout.setVisibility(View.INVISIBLE);
        if (results.getCountry().equalsIgnoreCase(""))
            fragmentBinding.countryLayout.setVisibility(View.INVISIBLE);
        else
            fragmentBinding.country.setText(results.getCountry());
        if (results.getCompanySize().equalsIgnoreCase(""))
            fragmentBinding.companySizeLayout.setVisibility(View.INVISIBLE);
        else
            fragmentBinding.companySize.setText(results.getCompanySize());
        fragmentBinding.function.setText(results.getFunction());
        fragmentBinding.companyName.setText(results.getCompanyName());
        if (results.getPresentation().equalsIgnoreCase("")) {
            fragmentBinding.presentation.setVisibility(View.GONE);
            fragmentBinding.presentationSeparator.setVisibility(View.GONE);
        } else
            fragmentBinding.presentation.setText(String.format("«\n%s", results.getPresentation()));
        fragmentBinding.username.setText(results.getUsername());
        if (results.getProducts().equalsIgnoreCase(""))
            fragmentBinding.productsLayout.setVisibility(View.GONE);
        else
            fragmentBinding.products.setText(results.getProducts());

        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(getContext());
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setJustifyContent(JustifyContent.FLEX_START);
        fragmentBinding.sectorsRecycler.setLayoutManager(layoutManager);
        fragmentBinding.sectorsRecycler.setAdapter(new ProfileDetailsAdapter(requireContext(), results.getSectors(), "sectors"));

        fragmentBinding.topicsRecycler.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        fragmentBinding.topicsRecycler.setAdapter(new ProfileDetailsAdapter(requireContext(), results.getTopics(), "topics"));

        fragmentBinding.body.setVisibility(View.VISIBLE);
    }
}