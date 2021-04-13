package com.mobiblanc.amdie.africa.network.views.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.mobiblanc.amdie.africa.network.databinding.HomePagerItemBinding;

public class HomePagerAdapter extends PagerAdapter {

    public HomePagerAdapter() {
    }

    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = HomePagerItemBinding.inflate(
                LayoutInflater.from(container.getContext()),
                container,
                false).getRoot();
        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public boolean isViewFromObject(View view, Object obj) {
        return view == obj;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        container.removeView(view);
    }
}

