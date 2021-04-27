package com.mobiblanc.amdie.africa.network.views.dashboard.contacts;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mobiblanc.amdie.africa.network.BuildConfig;
import com.mobiblanc.amdie.africa.network.R;
import com.mobiblanc.amdie.africa.network.Utilities.Constants;
import com.mobiblanc.amdie.africa.network.Utilities.Resource;
import com.mobiblanc.amdie.africa.network.Utilities.SwipeHelper;
import com.mobiblanc.amdie.africa.network.Utilities.Utilities;
import com.mobiblanc.amdie.africa.network.databinding.FragmentContactsBinding;
import com.mobiblanc.amdie.africa.network.datamanager.sharedpref.PreferenceManager;
import com.mobiblanc.amdie.africa.network.listeners.OnContactSelectedListener;
import com.mobiblanc.amdie.africa.network.models.contacts.favourite.AddFavouriteData;
import com.mobiblanc.amdie.africa.network.models.contacts.list.Contact;
import com.mobiblanc.amdie.africa.network.models.contacts.list.ContactsListData;
import com.mobiblanc.amdie.africa.network.viewmodels.ContactsViewModel;
import com.mobiblanc.amdie.africa.network.views.chat.ChatActivity;
import com.mobiblanc.amdie.africa.network.views.dashboard.DashboardActivity;

import java.util.List;

public class ContactsFragment extends Fragment implements OnContactSelectedListener {

    private FragmentContactsBinding fragmentBinding;
    private ContactsViewModel viewModel;
    private PreferenceManager preferenceManager;
    private ContactsAdapter favouritesAdapter;
    private List<Contact> contacts;
    private int selectedId;

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
        getContactsList("");
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

    private void init() {
        fragmentBinding.contactsBtn.setOnClickListener(v -> {
            Utilities.hideSoftKeyboard(requireContext(), requireView());
            fragmentBinding.contactsBtn.setTextColor(ContextCompat.getColor(requireContext(), R.color.blue4));
            fragmentBinding.mySelectionBtn.setTextColor(ContextCompat.getColor(requireContext(), R.color.blue6));
            fragmentBinding.suggestionsBtn.setTextColor(ContextCompat.getColor(requireContext(), R.color.blue6));
            fragmentBinding.contactsRecycler.setVisibility(View.VISIBLE);
            fragmentBinding.searchLayout.setVisibility(View.VISIBLE);
            fragmentBinding.favouritesRecycler.setVisibility(View.GONE);
            getContactsList("");
        });

        fragmentBinding.mySelectionBtn.setOnClickListener(v -> {
            Utilities.hideSoftKeyboard(requireContext(), requireView());
            fragmentBinding.contactsBtn.setTextColor(ContextCompat.getColor(requireContext(), R.color.blue6));
            fragmentBinding.suggestionsBtn.setTextColor(ContextCompat.getColor(requireContext(), R.color.blue6));
            fragmentBinding.mySelectionBtn.setTextColor(ContextCompat.getColor(requireContext(), R.color.blue4));
            fragmentBinding.contactsRecycler.setVisibility(View.GONE);
            fragmentBinding.searchLayout.setVisibility(View.GONE);
            fragmentBinding.favouritesRecycler.setVisibility(View.VISIBLE);
            getFavouritesList();
        });

        fragmentBinding.suggestionsBtn.setOnClickListener(v -> {
            Utilities.hideSoftKeyboard(requireContext(), requireView());
            fragmentBinding.contactsBtn.setTextColor(ContextCompat.getColor(requireContext(), R.color.blue6));
            fragmentBinding.mySelectionBtn.setTextColor(ContextCompat.getColor(requireContext(), R.color.blue6));
            fragmentBinding.suggestionsBtn.setTextColor(ContextCompat.getColor(requireContext(), R.color.blue4));
            fragmentBinding.contactsRecycler.setVisibility(View.GONE);
            fragmentBinding.searchLayout.setVisibility(View.GONE);
            fragmentBinding.favouritesRecycler.setVisibility(View.GONE);
            getSuggestionsList();
        });

        fragmentBinding.searchBtn.setOnClickListener(v -> {
            if (!fragmentBinding.searchInput.getText().toString().equalsIgnoreCase("")) {
                Utilities.hideSoftKeyboard(requireContext(), requireView());
                getContactsList(fragmentBinding.searchInput.getText().toString());
            }
        });

        fragmentBinding.body.setOnScrollChangeListener((View.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> Utilities.hideSoftKeyboard(requireContext(), requireView()));
    }

    private void getContactsList(String searchValue) {
        fragmentBinding.loader.setVisibility(View.VISIBLE);
        viewModel.getContactsList(preferenceManager.getValue(Constants.TOKEN, ""), 1, searchValue, "fr");
    }

    private void handleContactsListData(Resource<ContactsListData> responseData) {
        fragmentBinding.loader.setVisibility(View.GONE);
        switch (responseData.status) {
            case SUCCESS:
                switch (responseData.data.getHeader().getSearch()) {
                    case "-1":
                        fragmentBinding.image.setVisibility(View.VISIBLE);
                        fragmentBinding.message.setVisibility(View.VISIBLE);
                        fragmentBinding.editProfileBtn.setVisibility(View.VISIBLE);
                        fragmentBinding.message.setText(responseData.data.getHeader().getMessage());
                        break;
                    case "0":
                        fragmentBinding.image.setVisibility(View.VISIBLE);
                        fragmentBinding.message.setVisibility(View.VISIBLE);
                        fragmentBinding.message.setText(responseData.data.getHeader().getMessage());
                        break;
                    case "1":
                        initContactsList(responseData.data.getResults());
                        break;
                }
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

    private void initContactsList(List<Contact> contacts) {
        this.contacts = contacts;
        fragmentBinding.body.setVisibility(View.VISIBLE);

        fragmentBinding.contactsRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        fragmentBinding.contactsRecycler.setAdapter(new ContactsAdapter(requireContext(), contacts, this, 0));
        fragmentBinding.contactsRecycler.setNestedScrollingEnabled(false);
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

    private void getFavouritesList() {
        fragmentBinding.loader.setVisibility(View.VISIBLE);
        viewModel.getFavouritesList(preferenceManager.getValue(Constants.TOKEN, ""), "fr");
    }

    private void handleFavouritesListData(Resource<ContactsListData> responseData) {
        fragmentBinding.loader.setVisibility(View.GONE);
        switch (responseData.status) {
            case SUCCESS:
                initFavouritesList(responseData.data.getResults());
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

    private void initFavouritesList(List<Contact> contacts) {
        fragmentBinding.favouritesRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        favouritesAdapter = new ContactsAdapter(requireContext(), contacts, this, 1);
        fragmentBinding.favouritesRecycler.setAdapter(favouritesAdapter);
        fragmentBinding.favouritesRecycler.setNestedScrollingEnabled(false);
        enableSwipeToDelete(contacts);
    }

    private void enableSwipeToDelete(List<Contact> contacts) {

        new SwipeHelper(requireContext(), fragmentBinding.favouritesRecycler) {
            @Override
            public void instantiateUnderlayButton(RecyclerView.ViewHolder viewHolder, List<UnderlayButton> underlayButtons) {
                underlayButtons.add(new SwipeHelper.UnderlayButton(requireContext(), pos -> {
                    int id = contacts.get(pos).getId();
                    favouritesAdapter.removeItem(pos);
                    removeFavourite(id);
                }));
            }
        };
    }

    private void getSuggestionsList() {
        fragmentBinding.loader.setVisibility(View.VISIBLE);
        viewModel.getSuggestionsList(preferenceManager.getValue(Constants.TOKEN, ""), 1, "fr");
    }

    private void handleSuggestionsListData(Resource<ContactsListData> responseData) {
        fragmentBinding.loader.setVisibility(View.GONE);
        switch (responseData.status) {
            case SUCCESS:
                initSuggestionsList(responseData.data.getResults());
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

    private void initSuggestionsList(List<Contact> contacts) {
        fragmentBinding.contactsRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        fragmentBinding.contactsRecycler.setAdapter(new ContactsAdapter(requireContext(), contacts, this, 0));
        fragmentBinding.contactsRecycler.setNestedScrollingEnabled(false);
        fragmentBinding.contactsRecycler.setVisibility(View.VISIBLE);
    }
}