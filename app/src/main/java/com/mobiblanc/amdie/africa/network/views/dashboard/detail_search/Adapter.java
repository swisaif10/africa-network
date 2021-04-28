package com.mobiblanc.amdie.africa.network.views.dashboard.detail_search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mobiblanc.amdie.africa.network.R;
import com.mobiblanc.amdie.africa.network.models.search.profile.TopicsItem;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    Context context;
    private List<TopicsItem> list;

    public Adapter(List<TopicsItem> list) {
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        this.context = parent.getContext();
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.textView.setText(list.get(position).getName());
        Glide.with(context).load(list.get(position).getIcon()).into(holder.imageView);


    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            this.textView = (TextView) itemView.findViewById(R.id.id_txt);
            this.imageView = (ImageView) itemView.findViewById(R.id.img);
        }
    }
}