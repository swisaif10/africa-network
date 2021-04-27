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
import com.mobiblanc.amdie.africa.network.R;
import com.mobiblanc.amdie.africa.network.Utilities.Constants;
import com.mobiblanc.amdie.africa.network.Utilities.Resource;
import com.mobiblanc.amdie.africa.network.Utilities.Utilities;
import com.mobiblanc.amdie.africa.network.databinding.FragmentNewsBinding;
import com.mobiblanc.amdie.africa.network.datamanager.sharedpref.PreferenceManager;
import com.mobiblanc.amdie.africa.network.listeners.OnFilterCheckedChangeListener;
import com.mobiblanc.amdie.africa.network.listeners.OnItemSelectedListener;
import com.mobiblanc.amdie.africa.network.models.feed.Feed;
import com.mobiblanc.amdie.africa.network.models.feed.GetFeedData;
import com.mobiblanc.amdie.africa.network.models.feed.Results;
import com.mobiblanc.amdie.africa.network.models.feed.Sector;
import com.mobiblanc.amdie.africa.network.models.feed.Type;
import com.mobiblanc.amdie.africa.network.models.like.LikeFeedData;
import com.mobiblanc.amdie.africa.network.views.dashboard.DashboardActivity;
import com.mobiblanc.amdie.africa.network.views.dashboard.feed.filter.SectorFilterAdapter;
import com.mobiblanc.amdie.africa.network.views.dashboard.feed.filter.TypeFilterAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class FeedsFragment extends Fragment implements OnFilterCheckedChangeListener, OnItemSelectedListener {

    private FragmentNewsBinding fragmentBinding;
    private PreferenceManager preferenceManager;
    private List<Type> selectedTypes;
    private List<Sector> selectedSectors;
    private String selectedDate;
    private Boolean initialized = false;

    public FeedsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((DashboardActivity) requireActivity()).getViewModel().getFeedsLiveData().observe(requireActivity(), this::handleGetFeedsData);
        ((DashboardActivity) requireActivity()).getViewModel().getLikeFeedLiveData().observe(requireActivity(), this::handleLikeFeedData);

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

    @Override
    public void onFilterChecked(Object object) {
        if (object instanceof Type)
            selectedTypes.add((Type) object);
        else
            selectedSectors.add((Sector) object);
    }

    @Override
    public void onFilterUnchecked(Object object) {
        if (object instanceof Type)
            selectedTypes.remove(object);
        else
            selectedSectors.remove(object);
    }

    @Override
    public void onItemSelectedListener(Object object) {
        likeFeed(((Feed) object).getId());
    }

    private void init(Results results) {
        fragmentBinding.feedsRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        fragmentBinding.feedsRecycler.setAdapter(new FeedsAdapter(requireContext(), results.getFeeds(), this));
        fragmentBinding.feedsRecycler.setNestedScrollingEnabled(false);

        if (!initialized)
            initFilter(results);

        fragmentBinding.calendar.setEventHandler(date -> {
            SimpleDateFormat spf = new SimpleDateFormat("yyyy-MM-dd");
            selectedDate = spf.format(date);
            fragmentBinding.dateFilterBtn.setText(selectedDate);
            fragmentBinding.calendar.setVisibility(View.GONE);
        });

        fragmentBinding.filterBtn.setOnClickListener(v -> {
            StringBuilder types = new StringBuilder();
            StringBuilder sectors = new StringBuilder();
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

            if (!selectedTypes.isEmpty() || !selectedSectors.isEmpty() || !selectedDate.equalsIgnoreCase(""))
                getFeeds(sectors.toString(), types.toString(), selectedDate);
        });

        fragmentBinding.container.setOnClickListener(v -> {
            fragmentBinding.typeFilterRecycler.setVisibility(View.GONE);
            fragmentBinding.sectorFilterRecycler.setVisibility(View.GONE);
            fragmentBinding.calendar.setVisibility(View.GONE);
            //fragmentBinding.typeFilterBtn.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(requireContext(), R.drawable.ic_arrow), null);
            //fragmentBinding.sectorFilterBtn.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(requireContext(), R.drawable.ic_arrow), null);
        });

        fragmentBinding.resetBtn.setOnClickListener(v -> {
            if (!selectedTypes.isEmpty() || !selectedSectors.isEmpty() || !selectedDate.equalsIgnoreCase("")) {
                initFilter(results);
                getFeeds("", "", "");
            }
        });
    }

    private void initFilter(Results results) {
        initialized = true;
        selectedTypes = new ArrayList<>();
        selectedSectors = new ArrayList<>();
        selectedDate = "";
        fragmentBinding.dateFilterBtn.setText(getString(R.string.date_hint));
        fragmentBinding.typeFilterRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        fragmentBinding.typeFilterRecycler.setAdapter(new TypeFilterAdapter(requireContext(), results.getTypes(), this));
        fragmentBinding.typeFilterBtn.setOnClickListener(v -> {
            if (fragmentBinding.typeFilterRecycler.getVisibility() == View.VISIBLE) {
                fragmentBinding.typeFilterRecycler.setVisibility(View.GONE);
                //fragmentBinding.typeFilterBtn.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(requireContext(), R.drawable.ic_arrow), null);
            } else {
                fragmentBinding.sectorFilterRecycler.setVisibility(View.GONE);
                fragmentBinding.calendar.setVisibility(View.GONE);
                fragmentBinding.typeFilterRecycler.setVisibility(View.VISIBLE);
                //fragmentBinding.typeFilterBtn.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(requireContext(), R.drawable.arrow_up), null);
                //fragmentBinding.sectorFilterBtn.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(requireContext(), R.drawable.ic_arrow), null);
            }
        });

        fragmentBinding.sectorFilterRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        fragmentBinding.sectorFilterRecycler.setAdapter(new SectorFilterAdapter(requireContext(), results.getSectors(), this));
        fragmentBinding.sectorFilterBtn.setOnClickListener(v -> {
            if (fragmentBinding.sectorFilterRecycler.getVisibility() == View.VISIBLE) {
                fragmentBinding.sectorFilterRecycler.setVisibility(View.GONE);
                //fragmentBinding.sectorFilterBtn.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(requireContext(), R.drawable.ic_arrow), null);
            } else {
                fragmentBinding.typeFilterRecycler.setVisibility(View.GONE);
                fragmentBinding.calendar.setVisibility(View.GONE);
                fragmentBinding.sectorFilterRecycler.setVisibility(View.VISIBLE);
                //fragmentBinding.sectorFilterBtn.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(requireContext(), R.drawable.arrow_up), null);
                //fragmentBinding.typeFilterBtn.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(requireContext(), R.drawable.ic_arrow), null);
            }
        });

        fragmentBinding.dateFilterBtn.setOnClickListener(v -> {
            if (fragmentBinding.calendar.getVisibility() == View.VISIBLE)
                fragmentBinding.calendar.setVisibility(View.GONE);
            else {
                fragmentBinding.typeFilterRecycler.setVisibility(View.GONE);
                fragmentBinding.sectorFilterRecycler.setVisibility(View.GONE);
                fragmentBinding.calendar.setVisibility(View.VISIBLE);
                //fragmentBinding.typeFilterBtn.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(requireContext(), R.drawable.ic_arrow), null);
                //fragmentBinding.sectorFilterBtn.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(requireContext(), R.drawable.ic_arrow), null);
            }
        });
    }

    private void getFeeds(String sectors, String type, String date) {
        fragmentBinding.loader.setVisibility(View.VISIBLE);
        ((DashboardActivity) requireActivity()).getViewModel().getFeeds(preferenceManager.getValue(Constants.TOKEN, ""), sectors, type, date, "fr");
    }

    private void handleGetFeedsData(Resource<GetFeedData> responseData) {
        fragmentBinding.loader.setVisibility(View.GONE);
        switch (responseData.status) {
            case SUCCESS:
                init(responseData.data.getResults());
                break;
            case INVALID_TOKEN:
                Utilities.showErrorPopupWithCLick(requireContext(), responseData.data.getHeader().getMessage(), v -> {
                    preferenceManager.clearAll();
                    ((DashboardActivity) requireActivity()).tokenExpired();
                });
                break;
            case ERROR:
                Utilities.showErrorPopup(requireContext(), responseData.message);
                break;
        }
    }

    private void likeFeed(int id) {
        ((DashboardActivity) requireActivity()).getViewModel().likeFeed(preferenceManager.getValue(Constants.TOKEN, ""), id);
    }

    private void handleLikeFeedData(Resource<LikeFeedData> responseData) {
        switch (responseData.status) {
            case SUCCESS:

                break;
            case INVALID_TOKEN:
                break;
            case ERROR:
                Utilities.showErrorPopup(requireContext(), responseData.message);
                break;
        }
    }
}