package com.mobiblanc.amdie.africa.network.views.dashboard.feed;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mobiblanc.amdie.africa.network.BuildConfig;
import com.mobiblanc.amdie.africa.network.R;
import com.mobiblanc.amdie.africa.network.Utilities.Constants;
import com.mobiblanc.amdie.africa.network.Utilities.Resource;
import com.mobiblanc.amdie.africa.network.Utilities.Utilities;
import com.mobiblanc.amdie.africa.network.databinding.FeedItemLayoutBinding;
import com.mobiblanc.amdie.africa.network.datamanager.retrofit.RetrofitClient;
import com.mobiblanc.amdie.africa.network.datamanager.sharedpref.PreferenceManager;
import com.mobiblanc.amdie.africa.network.models.feed.Feed;
import com.mobiblanc.amdie.africa.network.models.like.LikeModel;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FeedsAdapter extends RecyclerView.Adapter<FeedsAdapter.ViewHolder> {

    private final Context context;
    private final List<Feed> feeds;
    private PreferenceManager preferenceManager;

    public FeedsAdapter( Context context, List<Feed> feeds) {
        this.context = context;
        this.feeds = feeds;
        preferenceManager = new PreferenceManager.Builder(Objects.requireNonNull(context), Context.MODE_PRIVATE)
                .name(BuildConfig.APPLICATION_ID)
                .build();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(FeedItemLayoutBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false));

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return feeds.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        FeedItemLayoutBinding itemBinding;

        ViewHolder(FeedItemLayoutBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding = itemBinding;
        }


        @SuppressLint("UseCompatLoadingForDrawables")
        private void bind(int position) {
            Glide.with(context).load(feeds.get(position).getLogo()).fitCenter().into(itemBinding.icon);
            itemBinding.name.setText(feeds.get(position).getTitle());
            itemBinding.date.setText(feeds.get(position).getDate());
            itemBinding.description.setText(Html.fromHtml(feeds.get(position).getBody()));
            Glide.with(context).load(feeds.get(position).getImage()).fitCenter().into(itemBinding.image);
            if (feeds.get(position).getLiked()==0){
                itemBinding.imgLike.setImageDrawable(context.getDrawable(R.drawable.heart_unselected));
                itemBinding.txtLike.setTextColor(context.getColor(R.color.grey4));
            }else {
                itemBinding.imgLike.setImageDrawable(context.getDrawable(R.drawable.heart));
                itemBinding.txtLike.setTextColor(context.getColor(R.color.blue7));
            }
            itemBinding.btnLike.setOnClickListener(v -> {
                setLike( position);
            });

        }
        public void setLike(int position) {
            Call<LikeModel> call = RetrofitClient.getInstance().endpoint().setLikeFeed(preferenceManager.getValue(Constants.TOKEN, ""), feeds.get(position).getId());
            call.enqueue(new Callback<LikeModel>() {
                @Override
                public void onResponse(@NonNull Call<LikeModel> call, @NonNull Response<LikeModel> response) {
                    assert response.body() != null;
                    if (response.body().getHeader().getStatus().equalsIgnoreCase("ko")) {
                        Resource.error(response.body().getHeader().getMessage(), null);
                        Utilities.showErrorPopup(context, response.body().getHeader().getMessage());
                    }
                    else
                    {
                        Resource.error(response.body().getHeader().getMessage(), null);
                        itemBinding.imgLike.setImageDrawable(context.getDrawable(R.drawable.heart));
                        itemBinding.txtLike.setTextColor(context.getColor(R.color.blue7));
                        feeds.get(position).setLiked(1);}
                }

                @Override
                public void onFailure(@NonNull Call<LikeModel> call, @NonNull Throwable t) {
                    if (t instanceof UnknownHostException || t instanceof ConnectException || t instanceof SocketTimeoutException) {
                        Utilities.showErrorPopup(context, "Connexion réseau indisponible. Assurez-vous que votre connexion réseau est active et réessayez.");
                    } else {
                        Utilities.showErrorPopup(context, t.getMessage());
                    }
                }
            });
        }
    }


}