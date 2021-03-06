package com.mobiblanc.amdie.africa.network.views.dashboard.messages;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.mobiblanc.amdie.africa.network.BuildConfig;
import com.mobiblanc.amdie.africa.network.databinding.FragmentMessagesListBinding;
import com.mobiblanc.amdie.africa.network.datamanager.sharedpref.PreferenceManager;
import com.mobiblanc.amdie.africa.network.listeners.OnItemSelectedListener;
import com.mobiblanc.amdie.africa.network.models.messaging.discussions.Discussion;
import com.mobiblanc.amdie.africa.network.models.messaging.discussions.DiscussionsListData;
import com.mobiblanc.amdie.africa.network.utilities.Constants;
import com.mobiblanc.amdie.africa.network.utilities.PaginationListener;
import com.mobiblanc.amdie.africa.network.utilities.Resource;
import com.mobiblanc.amdie.africa.network.utilities.Utilities;
import com.mobiblanc.amdie.africa.network.viewmodels.MessagesViewModel;
import com.mobiblanc.amdie.africa.network.views.chat.ChatActivity;
import com.mobiblanc.amdie.africa.network.views.dashboard.DashboardActivity;

import java.util.ArrayList;
import java.util.List;

public class MessagesListFragment extends Fragment implements OnItemSelectedListener {

    private FragmentMessagesListBinding fragmentBinding;
    private MessagesViewModel viewModel;
    private PreferenceManager preferenceManager;
    private List<Discussion> discussions;
    private MessagesAdapter messagesAdapter;
    private int currentPage = 1;
    private boolean isLastPage = false;
    private int totalPage = 10;
    private boolean isLoading = false;

    public MessagesListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = ViewModelProviders.of(this).get(MessagesViewModel.class);
        viewModel.getDiscussionsListLiveData().observe(this, this::handleDiscussionsListData);

        preferenceManager = new PreferenceManager.Builder(requireContext(), Context.MODE_PRIVATE)
                .name(BuildConfig.APPLICATION_ID)
                .build();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentBinding = FragmentMessagesListBinding.inflate(inflater, container, false);
        return fragmentBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fragmentBinding.loader.setVisibility(View.VISIBLE);
        fragmentBinding.editProfileBtn.setOnClickListener(v -> ((DashboardActivity) requireActivity()).selectProfileTab());
        discussions = new ArrayList<>();
        messagesAdapter = new MessagesAdapter(requireContext(), discussions, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        fragmentBinding.messagesRecycler.setLayoutManager(layoutManager);
        fragmentBinding.messagesRecycler.setAdapter(messagesAdapter);
        fragmentBinding.messagesRecycler.addOnScrollListener(new PaginationListener(layoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage++;
                getDiscussionsList();
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getDiscussionsList();
    }

    @Override
    public void onItemSelectedListener(Object object, Boolean webView) {
        Discussion discussion = (Discussion) object;
        Intent intent = new Intent(requireActivity(), ChatActivity.class);
        intent.putExtra("id", discussion.getIdReceiver());
        intent.putExtra("username", discussion.getMessengerName());
        intent.putExtra("picture", discussion.getPicture());
        startActivity(intent);
    }

    private void getDiscussionsList() {
        viewModel.getDiscussionsList(preferenceManager.getValue(Constants.TOKEN, ""), preferenceManager.getValue(Constants.LANGUAGE, "fr"));
    }

    private void handleDiscussionsListData(Resource<DiscussionsListData> responseData) {
        fragmentBinding.loader.setVisibility(View.GONE);
        switch (responseData.status) {
            case SUCCESS:
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
                        init(responseData.data.getResults());
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

    private void init(List<Discussion> discussions) {
        if (!this.discussions.containsAll(discussions)) {
            this.discussions.clear();
            this.discussions.addAll(discussions);
            messagesAdapter.notifyDataSetChanged();
        }
        fragmentBinding.messagesRecycler.setVisibility(View.VISIBLE);
    }
}