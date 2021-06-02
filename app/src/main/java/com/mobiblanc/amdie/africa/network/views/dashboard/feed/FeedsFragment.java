package com.mobiblanc.amdie.africa.network.views.dashboard.feed;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.mobiblanc.amdie.africa.network.BuildConfig;
import com.mobiblanc.amdie.africa.network.R;
import com.mobiblanc.amdie.africa.network.databinding.FragmentFeedsBinding;
import com.mobiblanc.amdie.africa.network.datamanager.sharedpref.PreferenceManager;
import com.mobiblanc.amdie.africa.network.listeners.OnFilterCheckedChangeListener;
import com.mobiblanc.amdie.africa.network.listeners.OnItemSelectedListener;
import com.mobiblanc.amdie.africa.network.models.feed.Feed;
import com.mobiblanc.amdie.africa.network.models.feed.GetFeedData;
import com.mobiblanc.amdie.africa.network.models.feed.Results;
import com.mobiblanc.amdie.africa.network.models.feed.Sector;
import com.mobiblanc.amdie.africa.network.models.feed.Type;
import com.mobiblanc.amdie.africa.network.models.like.LikeFeedData;
import com.mobiblanc.amdie.africa.network.utilities.Constants;
import com.mobiblanc.amdie.africa.network.utilities.Resource;
import com.mobiblanc.amdie.africa.network.utilities.Utilities;
import com.mobiblanc.amdie.africa.network.viewmodels.FeedsViewModel;
import com.mobiblanc.amdie.africa.network.views.dashboard.DashboardActivity;
import com.mobiblanc.amdie.africa.network.views.dashboard.feed.filter.SectorFilterAdapter;
import com.mobiblanc.amdie.africa.network.views.dashboard.feed.filter.TypeFilterAdapter;
import com.mobiblanc.amdie.africa.network.views.webview.WebViewActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class FeedsFragment extends Fragment implements OnFilterCheckedChangeListener, OnItemSelectedListener {

    private FragmentFeedsBinding fragmentBinding;
    private FeedsViewModel viewModel;
    private PreferenceManager preferenceManager;
    private FeedsAdapter feedsAdapter;
    private List<Feed> feeds;
    private List<Type> selectedTypes;
    private List<Sector> selectedSectors;
    private StringBuilder types;
    private StringBuilder sectors;
    private String selectedDate;
    private Boolean mostLiked;
    private Boolean initialized;
    private int offset = 0;
    private int currentPage = 1;
    private boolean isLoading = true;
    private boolean isLastPage = false;
    private boolean isScroll = false;

    public FeedsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = ViewModelProviders.of(this).get(FeedsViewModel.class);
        viewModel.getFeedsLiveData().observe(requireActivity(), this::handleGetFeedsData);
        viewModel.getLikeFeedLiveData().observe(requireActivity(), this::handleLikeFeedData);

        preferenceManager = new PreferenceManager.Builder(requireContext(), Context.MODE_PRIVATE)
                .name(BuildConfig.APPLICATION_ID)
                .build();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentBinding = FragmentFeedsBinding.inflate(inflater, container, false);
        return fragmentBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        feeds = new ArrayList<>();
        feedsAdapter = new FeedsAdapter(requireContext(), feeds, this);

        fragmentBinding.feedsRecycler.setHasFixedSize(true);
        fragmentBinding.feedsRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        fragmentBinding.feedsRecycler.setAdapter(feedsAdapter);
        fragmentBinding.feedsRecycler.setNestedScrollingEnabled(false);

        fragmentBinding.scrollView.getViewTreeObserver().addOnScrollChangedListener((ViewTreeObserver.OnScrollChangedListener) () -> {
            View child = (View) fragmentBinding.scrollView.getChildAt(fragmentBinding.scrollView.getChildCount() - 1);
            int diff = (child.getBottom() - (fragmentBinding.scrollView.getHeight() + fragmentBinding.scrollView.getScrollY()));
            if (!isLoading && !isLastPage)
                if (diff == 0) {
                    isScroll = true;
                    isLoading = true;
                    currentPage++;
                    getFeeds(sectors.toString(), types.toString(), selectedDate, mostLiked);
                }
        });

        getFeeds("", "", "", false);
    }

    @Override
    public void onResume() {
        super.onResume();
        initialized = false;
    }

    @Override
    public void onPause() {
        super.onPause();
        this.offset = 0;
        this.currentPage = 1;
    }

    @Override
    public void onFilterChecked(Object object, String type) {
        if (object instanceof Type)
            selectedTypes.add((Type) object);
        else
            selectedSectors.add((Sector) object);
    }

    @Override
    public void onFilterUnchecked(Object object, String type) {
        if (object instanceof Type)
            selectedTypes.remove(object);
        else
            selectedSectors.remove(object);
    }

    @Override
    public void onItemSelectedListener(Object object, Boolean webView) {
        if (webView) {
            Intent intent = new Intent(requireContext(), WebViewActivity.class);
            intent.putExtra("url", ((Feed) object).getUrl());
            startActivity(intent);
        } else
            likeFeed(((Feed) object).getId());
    }

    private void getFeeds(String sectors, String type, String date, Boolean mostLiked) {
        if (!isScroll)
            fragmentBinding.loader.setVisibility(View.VISIBLE);
        viewModel.getFeeds(preferenceManager.getValue(Constants.TOKEN, ""), sectors, type, date, mostLiked, offset, preferenceManager.getValue(Constants.LANGUAGE, "fr"));
    }

    private void handleGetFeedsData(Resource<GetFeedData> responseData) {
        fragmentBinding.loader.setVisibility(View.GONE);
        switch (responseData.status) {
            case SUCCESS:
                init(responseData.data.getResults());
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

    @SuppressLint("SimpleDateFormat")
    private void init(Results results) {
        if (!initialized)
            initFilter(results);

        if (offset != 0) feedsAdapter.removeLoading();
        if (!isScroll)
            this.feedsAdapter.clear();
        else
            isScroll = false;
        feeds.addAll(results.getFeeds());
        feedsAdapter.notifyDataSetChanged();

        if (currentPage < results.getTotalPages())
            feedsAdapter.addLoading();
        else
            isLastPage = true;

        new Handler(Looper.getMainLooper()).postDelayed(() -> isLoading = false, 1500);

        offset = results.getOffset();

        fragmentBinding.container.setOnClickListener(v -> {
            fragmentBinding.typeFilterRecycler.setVisibility(View.GONE);
            fragmentBinding.sectorFilterRecycler.setVisibility(View.GONE);
            fragmentBinding.calendar.setVisibility(View.GONE);
            //fragmentBinding.typeFilterBtn.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(requireContext(), R.drawable.ic_arrow), null);
            //fragmentBinding.sectorFilterBtn.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(requireContext(), R.drawable.ic_arrow), null);
        });

        fragmentBinding.calendar.setEventHandler(date -> {
            SimpleDateFormat spf = new SimpleDateFormat("yyyy-MM-dd");
            selectedDate = spf.format(date);
            fragmentBinding.dateFilterBtn.setText(selectedDate);
            fragmentBinding.calendar.setVisibility(View.GONE);
        });

        fragmentBinding.filterBtn.setOnClickListener(v -> {
            offset = 0;
            getDataFromFilter();

            if (!selectedTypes.isEmpty() || !selectedSectors.isEmpty() || !selectedDate.equalsIgnoreCase(""))
                getFeeds(sectors.toString(), types.toString(), selectedDate, false);
        });

        fragmentBinding.resetBtn.setOnClickListener(v -> {
            if (!selectedTypes.isEmpty() || !selectedSectors.isEmpty() || !selectedDate.equalsIgnoreCase("")) {
                offset = 0;
                initFilter(results);
                getFeeds("", "", "", mostLiked);
            }
        });
    }

    private void initFilter(Results results) {
        initialized = true;
        selectedTypes = new ArrayList<>();
        selectedSectors = new ArrayList<>();
        types = new StringBuilder();
        sectors = new StringBuilder();
        selectedDate = "";
        mostLiked = false;
        fragmentBinding.dateFilterBtn.setText(getString(R.string.date_hint));
        fragmentBinding.typeFilterRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        fragmentBinding.typeFilterRecycler.setAdapter(new TypeFilterAdapter(results.getTypes(), this));
        fragmentBinding.typeFilterBtn.setOnClickListener(v -> {
            if (fragmentBinding.typeFilterRecycler.getVisibility() == View.VISIBLE) {
                fragmentBinding.typeFilterRecycler.setVisibility(View.GONE);
                fragmentBinding.typeFilterBtn.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(requireContext(), R.drawable.ic_arrow), null);
            } else {
                fragmentBinding.sectorFilterRecycler.setVisibility(View.GONE);
                fragmentBinding.calendar.setVisibility(View.GONE);
                fragmentBinding.typeFilterRecycler.setVisibility(View.VISIBLE);
                fragmentBinding.typeFilterBtn.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(requireContext(), R.drawable.arrow_up), null);
                fragmentBinding.sectorFilterBtn.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(requireContext(), R.drawable.ic_arrow), null);
            }
        });

        fragmentBinding.sectorFilterRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        fragmentBinding.sectorFilterRecycler.setAdapter(new SectorFilterAdapter(results.getSectors(), this));
        fragmentBinding.sectorFilterBtn.setOnClickListener(v -> {
            if (fragmentBinding.sectorFilterRecycler.getVisibility() == View.VISIBLE) {
                fragmentBinding.sectorFilterRecycler.setVisibility(View.GONE);
                fragmentBinding.sectorFilterBtn.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(requireContext(), R.drawable.ic_arrow), null);
            } else {
                fragmentBinding.typeFilterRecycler.setVisibility(View.GONE);
                fragmentBinding.calendar.setVisibility(View.GONE);
                fragmentBinding.sectorFilterRecycler.setVisibility(View.VISIBLE);
                fragmentBinding.sectorFilterBtn.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(requireContext(), R.drawable.arrow_up), null);
                fragmentBinding.typeFilterBtn.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(requireContext(), R.drawable.ic_arrow), null);
            }
        });

        fragmentBinding.dateFilterBtn.setOnClickListener(v -> {
            if (fragmentBinding.calendar.getVisibility() == View.VISIBLE)
                fragmentBinding.calendar.setVisibility(View.GONE);
            else {
                fragmentBinding.typeFilterRecycler.setVisibility(View.GONE);
                fragmentBinding.sectorFilterRecycler.setVisibility(View.GONE);
                fragmentBinding.calendar.setVisibility(View.VISIBLE);
            }
        });

        fragmentBinding.mostLikedFilterBtn.setOnClickListener(v -> {
            offset = 0;
            mostLiked = true;
            getDataFromFilter();
            getFeeds(sectors.toString(), types.toString(), selectedDate, mostLiked);
        });
    }

    private void getDataFromFilter() {
        if (!selectedTypes.isEmpty()) {
            for (Type type : selectedTypes) {
                types.append(type.getId()).append(",");
            }
            types.deleteCharAt(types.length() - 1);
        }
        if (!selectedSectors.isEmpty()) {
            for (Sector sector : selectedSectors) {
                sectors.append(sector.getId()).append(",");
            }
            sectors.deleteCharAt(sectors.length() - 1);
        }
    }

    private void likeFeed(int id) {
        viewModel.likeFeed(preferenceManager.getValue(Constants.TOKEN, ""), id);
    }

    private void handleLikeFeedData(Resource<LikeFeedData> responseData) {
        switch (responseData.status) {
            case SUCCESS:

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
}