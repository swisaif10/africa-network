package com.mobiblanc.amdie.africa.network.views.dashboard.contacts;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.mobiblanc.amdie.africa.network.R;
import com.mobiblanc.amdie.africa.network.databinding.FragmentContactsBinding;

public class ContactsFragment extends Fragment {

    private FragmentContactsBinding fragmentBinding;

    public ContactsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentBinding = FragmentContactsBinding.inflate(inflater, container, false);
        return fragmentBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init();
    }

    private void init() {
        fragmentBinding.contactsRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        fragmentBinding.contactsRecycler.setAdapter(new ContactsAdapter(requireContext(), 0));
        fragmentBinding.contactsRecycler.setNestedScrollingEnabled(false);

        fragmentBinding.requestsRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        fragmentBinding.requestsRecycler.setAdapter(new ContactsAdapter(requireContext(), 1));
        fragmentBinding.requestsRecycler.setNestedScrollingEnabled(false);

        fragmentBinding.myContactsRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        fragmentBinding.myContactsRecycler.setAdapter(new ContactsAdapter(requireActivity(), 2));
        fragmentBinding.myContactsRecycler.setNestedScrollingEnabled(false);

        fragmentBinding.contactsBtn.setOnClickListener(v -> {
            fragmentBinding.contactsBtn.setTextColor(ContextCompat.getColor(requireContext(), R.color.blue4));
            fragmentBinding.mySelectionBtn.setTextColor(ContextCompat.getColor(requireContext(), R.color.blue6));
            fragmentBinding.contactsRecycler.setVisibility(View.VISIBLE);
            fragmentBinding.searchLayout.setVisibility(View.VISIBLE);
            fragmentBinding.mySelectionLayout.setVisibility(View.GONE);
        });

        fragmentBinding.mySelectionBtn.setOnClickListener(v -> {
            fragmentBinding.contactsBtn.setTextColor(ContextCompat.getColor(requireContext(), R.color.blue6));
            fragmentBinding.mySelectionBtn.setTextColor(ContextCompat.getColor(requireContext(), R.color.blue4));
            fragmentBinding.contactsRecycler.setVisibility(View.GONE);
            fragmentBinding.searchLayout.setVisibility(View.GONE);
            fragmentBinding.mySelectionLayout.setVisibility(View.VISIBLE);
        });

    }
}