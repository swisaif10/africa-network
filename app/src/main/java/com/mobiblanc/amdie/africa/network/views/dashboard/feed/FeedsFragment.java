package com.mobiblanc.amdie.africa.network.views.dashboard.feed;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.mobiblanc.amdie.africa.network.BuildConfig;
import com.mobiblanc.amdie.africa.network.Utilities.Constants;
import com.mobiblanc.amdie.africa.network.Utilities.Resource;
import com.mobiblanc.amdie.africa.network.Utilities.Utilities;
import com.mobiblanc.amdie.africa.network.databinding.FragmentNewsBinding;
import com.mobiblanc.amdie.africa.network.datamanager.sharedpref.PreferenceManager;
import com.mobiblanc.amdie.africa.network.models.feed.Feed;
import com.mobiblanc.amdie.africa.network.models.feed.GetFeedData;
import com.mobiblanc.amdie.africa.network.views.dashboard.DashboardActivity;

import java.util.List;

public class FeedsFragment extends Fragment {

    private FragmentNewsBinding fragmentBinding;
    private PreferenceManager preferenceManager;

    public FeedsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((DashboardActivity) requireActivity()).getViewModel().getGetFeedsLiveData().observe(requireActivity(), this::handleGetFeedsData);

        preferenceManager = new PreferenceManager.Builder(requireContext(), Context.MODE_PRIVATE)
                .name(BuildConfig.APPLICATION_ID)
                .build();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentBinding = FragmentNewsBinding.inflate(inflater, container, false);
        return fragmentBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getFeeds("", "", "");
    }

    private void init(List<Feed> feeds) {
        if (fragmentBinding!=null){
        fragmentBinding.feedsRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        fragmentBinding.feedsRecycler.setAdapter(new FeedsAdapter(requireContext(), feeds));
        fragmentBinding.feedsRecycler.setNestedScrollingEnabled(false);
    }
    }

    private void getFeeds(String sectors, String type, String date) {
        ((DashboardActivity) requireActivity()).getViewModel().getFeeds(preferenceManager.getValue(Constants.TOKEN, ""), sectors, type, date, "fr");
    }

    private void handleGetFeedsData(Resource<GetFeedData> responseData) {
        switch (responseData.status) {
            case SUCCESS:
                init(responseData.data.getResults().getFeeds());
                break;
            case LOADING:
                break;
            case ERROR:
                Utilities.showErrorPopup(requireContext(), responseData.message);
                break;
        }
    }
}