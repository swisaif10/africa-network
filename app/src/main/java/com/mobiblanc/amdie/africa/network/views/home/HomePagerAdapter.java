package com.mobiblanc.amdie.africa.network.views.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.mobiblanc.amdie.africa.network.R;
import com.mobiblanc.amdie.africa.network.databinding.HomePagerItemBinding;
import com.mobiblanc.amdie.africa.network.models.pager.HomePagerItem;

import java.util.List;

public class HomePagerAdapter extends PagerAdapter {

    private final List<HomePagerItem> itemList;

    public HomePagerAdapter(List<HomePagerItem> itemList) {
        this.itemList = itemList;
    }

    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = HomePagerItemBinding.inflate(
                LayoutInflater.from(container.getContext()),
                container,
                false).getRoot();
        TextView body = view.findViewById(R.id.body);
        body.setText(itemList.get(position).getText());
        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object obj) {
        return view == obj;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, @NonNull Object object) {
        View view = (View) object;
        container.removeView(view);
    }
}

