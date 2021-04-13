package com.mobiblanc.amdie.africa.network.views.dashboard.contacts;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.mobiblanc.amdie.africa.network.databinding.FragmentContactDetailsBinding;
import com.mobiblanc.amdie.africa.network.views.dashboard.DashboardActivity;

public class ContactDetailsFragment extends Fragment {

    private FragmentContactDetailsBinding fragmentBinding;

    public ContactDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentBinding = FragmentContactDetailsBinding.inflate(inflater, container, false);
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
        ((DashboardActivity) requireActivity()).getSupportActionBar().hide();
    }

    @Override
    public void onStop() {
        super.onStop();
        ((DashboardActivity) requireActivity()).getSupportActionBar().show();
    }

    private void init() {
        fragmentBinding.backBtn.setOnClickListener(v -> requireActivity().onBackPressed());
    }

}