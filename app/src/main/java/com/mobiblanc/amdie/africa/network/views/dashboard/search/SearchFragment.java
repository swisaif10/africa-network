package com.mobiblanc.amdie.africa.network.views.dashboard.search;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.mobiblanc.amdie.africa.network.Utilities.Utilities;
import com.mobiblanc.amdie.africa.network.databinding.FragmentSearchBinding;

public class SearchFragment extends Fragment {

    private FragmentSearchBinding fragmentBinding;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        init();
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
    }

    private Boolean checkForm() {
        return !Utilities.isEmpty(fragmentBinding.presentation) &&
                !Utilities.isEmpty(fragmentBinding.topics) &&
                !Utilities.isEmpty(fragmentBinding.sector) &&
                !Utilities.isEmpty(fragmentBinding.headquarters) &&
                !Utilities.isEmpty(fragmentBinding.squad) &&
                !Utilities.isEmpty(fragmentBinding.turnover) &&
                !Utilities.isEmpty(fragmentBinding.products);
    }
}