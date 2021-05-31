package com.mobiblanc.amdie.africa.network.views.dashboard.contacts;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
import androidx.recyclerview.widget.RecyclerView;

import com.mobiblanc.amdie.africa.network.BuildConfig;
import com.mobiblanc.amdie.africa.network.R;
import com.mobiblanc.amdie.africa.network.databinding.FragmentContactsBinding;
import com.mobiblanc.amdie.africa.network.datamanager.sharedpref.PreferenceManager;
import com.mobiblanc.amdie.africa.network.listeners.OnContactSelectedListener;
import com.mobiblanc.amdie.africa.network.listeners.OnFilterCheckedChangeListener;
import com.mobiblanc.amdie.africa.network.models.contacts.favourite.AddFavouriteData;
import com.mobiblanc.amdie.africa.network.models.contacts.filter.ContactsFilterData;
import com.mobiblanc.amdie.africa.network.models.contacts.filter.Results;
import com.mobiblanc.amdie.africa.network.models.contacts.list.Contact;
import com.mobiblanc.amdie.africa.network.models.contacts.list.ContactsListData;
import com.mobiblanc.amdie.africa.network.utilities.Constants;
import com.mobiblanc.amdie.africa.network.utilities.Resource;
import com.mobiblanc.amdie.africa.network.utilities.SwipeHelper;
import com.mobiblanc.amdie.africa.network.utilities.Utilities;
import com.mobiblanc.amdie.africa.network.viewmodels.ContactsViewModel;
import com.mobiblanc.amdie.africa.network.views.chat.ChatActivity;
import com.mobiblanc.amdie.africa.network.views.dashboard.DashboardActivity;
import com.mobiblanc.amdie.africa.network.views.dashboard.contacts.details.ContactDetailsActivity;

import java.util.ArrayList;
import java.util.List;

public class ContactsFragment extends Fragment implements OnContactSelectedListener, OnFilterCheckedChangeListener {

    private FragmentContactsBinding fragmentBinding;
    private ContactsViewModel viewModel;
    private PreferenceManager preferenceManager;
    private List<Contact> contacts;
    private ContactsAdapter contactsAdapter;
    private List<Contact> favourites;
    private ContactsAdapter favouritesAdapter;
    private List<Contact> suggestions;
    private ContactsAdapter suggestionsAdapter;
    private int selectedId;
    private Boolean isSuggest = false;
    private int currentPage = 1;
    private boolean isLastPage = false;
    private boolean isLoading = false;
    private String searchValue = "";
    private boolean isScroll = false;

    public ContactsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = ViewModelProviders.of(this).get(ContactsViewModel.class);
        viewModel.getContactsListLiveData().observe(this, this::handleContactsListData);
        viewModel.getFavouritesListLiveData().observe(this, this::handleFavouritesListData);
        viewModel.getAddFavouriteLiveData().observe(this, this::handleAddFavouriteData);
        viewModel.getRemoveFavouriteLiveData().observe(this, this::handleRemoveFavouriteData);
        viewModel.getSuggestionsListLiveData().observe(this, this::handleSuggestionsListData);
        viewModel.getContactsFilterLiveData().observe(this, this::handleContactsFilterFormData);

        preferenceManager = new PreferenceManager.Builder(requireContext(), Context.MODE_PRIVATE)
                .name(BuildConfig.APPLICATION_ID)
                .build();
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
        getContactsList();
    }

    @Override
    public void onPause() {
        super.onPause();
        resetScroll(false);
    }

    @Override
    public void onContactSelected(Contact contact) {
        Intent intent = new Intent(requireContext(), ContactDetailsActivity.class);
        intent.putExtra("contact", contact);
        startActivity(intent);
    }

    @Override
    public void onFavouriteClick(Contact contact) {
        if (contact.isFavourite())
            addFavourite(contact.getIdClient());
        else
            removeFavourite(contact.getRequestId());
    }

    @Override
    public void onMessageClick(Contact contact) {
        Intent intent = new Intent(requireActivity(), ChatActivity.class);
        intent.putExtra("id", contact.getIdClient());
        intent.putExtra("username", contact.getUsername());
        intent.putExtra("picture", contact.getProfilePicture());
        startActivity(intent);
    }

    @Override
    public void onFilterChecked(Object object) {

    }

    @Override
    public void onFilterUnchecked(Object object) {

    }

    private void init() {
        fragmentBinding.loader.setVisibility(View.VISIBLE);

        fragmentBinding.editProfileBtn.setOnClickListener(v -> ((DashboardActivity) requireActivity()).selectProfileTab());

        fragmentBinding.contactsBtn.setOnClickListener(v -> {
            Utilities.hideSoftKeyboard(requireContext(), requireView());
            fragmentBinding.contactsBtn.setTextColor(ContextCompat.getColor(requireContext(), R.color.blue4));
            fragmentBinding.favouritesBtn.setTextColor(ContextCompat.getColor(requireContext(), R.color.blue6));
            fragmentBinding.suggestionsBtn.setTextColor(ContextCompat.getColor(requireContext(), R.color.blue6));

            fragmentBinding.contactsLayout.setVisibility(View.VISIBLE);
            fragmentBinding.favouritesRecycler.setVisibility(View.GONE);
            fragmentBinding.suggestionsRecycler.setVisibility(View.GONE);

            resetScroll(false);
            getContactsList();
        });

        fragmentBinding.favouritesBtn.setOnClickListener(v -> {
            Utilities.hideSoftKeyboard(requireContext(), requireView());
            fragmentBinding.contactsBtn.setTextColor(ContextCompat.getColor(requireContext(), R.color.blue6));
            fragmentBinding.suggestionsBtn.setTextColor(ContextCompat.getColor(requireContext(), R.color.blue6));
            fragmentBinding.favouritesBtn.setTextColor(ContextCompat.getColor(requireContext(), R.color.blue4));

            fragmentBinding.contactsLayout.setVisibility(View.GONE);
            fragmentBinding.favouritesRecycler.setVisibility(View.VISIBLE);
            fragmentBinding.suggestionsRecycler.setVisibility(View.GONE);

            resetScroll(false);
            getFavouritesList();
        });

        fragmentBinding.suggestionsBtn.setOnClickListener(v -> {
            Utilities.hideSoftKeyboard(requireContext(), requireView());
            fragmentBinding.contactsBtn.setTextColor(ContextCompat.getColor(requireContext(), R.color.blue6));
            fragmentBinding.favouritesBtn.setTextColor(ContextCompat.getColor(requireContext(), R.color.blue6));
            fragmentBinding.suggestionsBtn.setTextColor(ContextCompat.getColor(requireContext(), R.color.blue4));

            fragmentBinding.contactsLayout.setVisibility(View.GONE);
            fragmentBinding.favouritesRecycler.setVisibility(View.GONE);
            fragmentBinding.suggestionsRecycler.setVisibility(View.VISIBLE);

            resetScroll(true);
            getSuggestionsList();
        });

        fragmentBinding.searchBtn.setOnClickListener(v -> {
            searchValue = fragmentBinding.searchInput.getText().toString();
            if (!searchValue.equalsIgnoreCase("")) {
                Utilities.hideSoftKeyboard(requireContext(), requireView());
                if (isSuggest)
                    getSuggestionsList();
                else
                    getContactsList();
            }
        });

        fragmentBinding.body.setOnScrollChangeListener((View.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> Utilities.hideSoftKeyboard(requireContext(), requireView()));

        enableSwipeToDelete();

        fragmentBinding.contactsRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        fragmentBinding.favouritesRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        fragmentBinding.suggestionsRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        contacts = new ArrayList<>();
        favourites = new ArrayList<>();
        suggestions = new ArrayList<>();
        contactsAdapter = new ContactsAdapter(requireContext(), contacts, this, 0);
        favouritesAdapter = new ContactsAdapter(requireContext(), favourites, this, 1);
        suggestionsAdapter = new ContactsAdapter(requireContext(), suggestions, this, 0);
        fragmentBinding.contactsRecycler.setAdapter(contactsAdapter);
        fragmentBinding.favouritesRecycler.setAdapter(favouritesAdapter);
        fragmentBinding.suggestionsRecycler.setAdapter(suggestionsAdapter);
        fragmentBinding.contactsRecycler.setNestedScrollingEnabled(false);
        fragmentBinding.favouritesRecycler.setNestedScrollingEnabled(false);
        fragmentBinding.suggestionsRecycler.setNestedScrollingEnabled(false);

        fragmentBinding.body.getViewTreeObserver().addOnScrollChangedListener((ViewTreeObserver.OnScrollChangedListener) () -> {
            View child = (View) fragmentBinding.body.getChildAt(fragmentBinding.body.getChildCount() - 1);
            int diff = (child.getBottom() - (fragmentBinding.body.getHeight() + fragmentBinding.body.getScrollY()));
            if ((fragmentBinding.contactsRecycler.getVisibility() == View.VISIBLE || fragmentBinding.suggestionsRecycler.getVisibility() == View.VISIBLE) && !isLoading && !isLastPage)
                if (diff == 0) {
                    isScroll = true;
                    isLoading = true;
                    currentPage++;
                    if (isSuggest)
                        getSuggestionsList();
                    else
                        getContactsList();
                }
        });
    }

    private void getContactsList() {
        viewModel.getContactsList(preferenceManager.getValue(Constants.TOKEN, ""), currentPage, searchValue, preferenceManager.getValue(Constants.LANGUAGE, "fr"));
    }

    private void handleContactsListData(Resource<ContactsListData> responseData) {
        fragmentBinding.loader.setVisibility(View.GONE);
        switch (responseData.status) {
            case SUCCESS:
                getContactsFilterForm();
                switch (responseData.data.getHeader().getSearch()) {
                    case -1:
                        fragmentBinding.placeholder.setVisibility(View.VISIBLE);
                        fragmentBinding.editProfileBtn.setVisibility(View.VISIBLE);
                        fragmentBinding.message.setText(responseData.data.getHeader().getMessage());
                        break;
                    case 0:
                        fragmentBinding.placeholder.setVisibility(View.VISIBLE);
                        fragmentBinding.message.setText(responseData.data.getHeader().getMessage());
                        break;
                    case 1:
                        fragmentBinding.body.setVisibility(View.VISIBLE);
                        initContactsList(responseData.data.getResults(), responseData.data.getNbrPages());
                        break;
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

    private void initContactsList(List<Contact> contacts, int totalPages) {
        if (currentPage != 1)
            contactsAdapter.removeLoading();

        if (isScroll) {
            isScroll = false;
        } else {
            this.contacts.clear();
        }
        this.contacts.addAll(contacts);
        contactsAdapter.notifyDataSetChanged();

        if (currentPage < totalPages)
            contactsAdapter.addLoading();
        else
            isLastPage = true;
        new Handler().postDelayed(() -> isLoading = false, 1500);
    }

    private void getContactsFilterForm() {
        viewModel.getContactsFilterForm(preferenceManager.getValue(Constants.TOKEN, ""), preferenceManager.getValue(Constants.LANGUAGE, "fr"));
    }

    private void handleContactsFilterFormData(Resource<ContactsFilterData> responseData) {
        switch (responseData.status) {
            case SUCCESS:
                initFilter(responseData.data.getResults());
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

    private void initFilter(Results results) {
        fragmentBinding.countryFilterRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        fragmentBinding.countryFilterRecycler.setAdapter(new ContactsFilterAdapter(results.getCountries(), this));
        fragmentBinding.countryFilterBtn.setOnClickListener(v -> {
            if (fragmentBinding.countryFilterRecycler.getVisibility() == View.VISIBLE) {
                fragmentBinding.countryFilterRecycler.setVisibility(View.GONE);
                fragmentBinding.countryFilterBtn.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(requireContext(), R.drawable.ic_arrow), null);
            } else {
                fragmentBinding.countryFilterRecycler.setVisibility(View.VISIBLE);
                fragmentBinding.sectorFilterRecycler.setVisibility(View.GONE);
                fragmentBinding.countryFilterBtn.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(requireContext(), R.drawable.arrow_up), null);
                fragmentBinding.sectorFilterBtn.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(requireContext(), R.drawable.ic_arrow), null);
            }
        });

        fragmentBinding.sectorFilterRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        fragmentBinding.sectorFilterRecycler.setAdapter(new ContactsFilterAdapter(results.getSectors(), this));
        fragmentBinding.sectorFilterBtn.setOnClickListener(v -> {
            if (fragmentBinding.sectorFilterRecycler.getVisibility() == View.VISIBLE) {
                fragmentBinding.sectorFilterRecycler.setVisibility(View.GONE);
                fragmentBinding.sectorFilterBtn.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(requireContext(), R.drawable.ic_arrow), null);
            } else {
                fragmentBinding.sectorFilterRecycler.setVisibility(View.VISIBLE);
                fragmentBinding.countryFilterRecycler.setVisibility(View.GONE);
                fragmentBinding.sectorFilterBtn.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(requireContext(), R.drawable.arrow_up), null);
                fragmentBinding.countryFilterBtn.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(requireContext(), R.drawable.ic_arrow), null);
            }
        });
    }

    private void addFavourite(int id) {
        this.selectedId = id;
        viewModel.addFavourite(preferenceManager.getValue(Constants.TOKEN, ""), id);
    }

    private void handleAddFavouriteData(Resource<AddFavouriteData> responseData) {
        switch (responseData.status) {
            case SUCCESS:
                for (Contact contact : contacts) {
                    if (contact.getIdClient() == selectedId)
                        contact.setRequestId(responseData.data.getResults().getRequestId());
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

    private void removeFavourite(int id) {
        fragmentBinding.loader.setVisibility(View.VISIBLE);
        viewModel.removeFavourite(preferenceManager.getValue(Constants.TOKEN, ""), id);
    }

    private void handleRemoveFavouriteData(Resource<AddFavouriteData> responseData) {
        fragmentBinding.loader.setVisibility(View.GONE);
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

    private void getFavouritesList() {
        if (favourites.size() == 0)
            fragmentBinding.loader.setVisibility(View.VISIBLE);
        viewModel.getFavouritesList(preferenceManager.getValue(Constants.TOKEN, ""), preferenceManager.getValue(Constants.LANGUAGE, "fr"));
    }

    private void handleFavouritesListData(Resource<ContactsListData> responseData) {
        fragmentBinding.loader.setVisibility(View.GONE);
        switch (responseData.status) {
            case SUCCESS:
                initFavouritesList(responseData.data.getResults());
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

    private void initFavouritesList(List<Contact> contacts) {
        this.favourites.clear();
        this.favourites.addAll(contacts);
        favouritesAdapter.notifyDataSetChanged();
    }

    private void enableSwipeToDelete() {

        new SwipeHelper(requireContext(), fragmentBinding.favouritesRecycler) {
            @Override
            public void instantiateUnderlayButton(RecyclerView.ViewHolder viewHolder, List<UnderlayButton> underlayButtons) {
                underlayButtons.add(new SwipeHelper.UnderlayButton(requireContext(), pos -> {
                    int id = favourites.get(pos).getId();
                    favouritesAdapter.removeItem(pos);
                    removeFavourite(id);
                }));
            }
        };
    }

    private void getSuggestionsList() {
        if (suggestions.size() == 0)
            fragmentBinding.loader.setVisibility(View.VISIBLE);
        viewModel.getSuggestionsList(preferenceManager.getValue(Constants.TOKEN, ""), currentPage, searchValue, preferenceManager.getValue(Constants.LANGUAGE, "fr"));
    }

    private void handleSuggestionsListData(Resource<ContactsListData> responseData) {
        fragmentBinding.loader.setVisibility(View.GONE);
        switch (responseData.status) {
            case SUCCESS:
                initSuggestionsList(responseData.data.getResults(), responseData.data.getNbrPages());
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

    private void initSuggestionsList(List<Contact> contacts, int totalPages) {
        if (currentPage != 1)
            suggestionsAdapter.removeLoading();

        if (isScroll) {
            isScroll = false;
        } else {
            this.suggestions.clear();
        }
        this.suggestions.addAll(contacts);
        suggestionsAdapter.notifyDataSetChanged();

        if (currentPage < totalPages)
            suggestionsAdapter.addLoading();
        else
            isLastPage = true;
        new Handler().postDelayed(() -> isLoading = false, 1500);
    }

    private void resetScroll(Boolean isSuggest) {
        this.isSuggest = isSuggest;
        currentPage = 1;
        searchValue = "";
        isLastPage = false;
        isLoading = false;
        isScroll = false;
    }

}