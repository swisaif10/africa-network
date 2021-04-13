package com.mobiblanc.amdie.africa.network.views.dashboard.contacts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobiblanc.amdie.africa.network.R;
import com.mobiblanc.amdie.africa.network.databinding.ContactItemLayoutBinding;
import com.mobiblanc.amdie.africa.network.views.dashboard.DashboardActivity;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ViewHolder> {

    private final Context context;
    private final int type;

    public ContactsAdapter(Context context, int type) {
        this.context = context;
        this.type = type;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ContactItemLayoutBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind();
    }

    @Override
    public int getItemCount() {
        switch (type) {
            case 0:
                return 10;
            case 1:
                return 3;
            case 2:
                return 7;
            default:
                return 0;
        }
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        ContactItemLayoutBinding itemBinding;

        ViewHolder(ContactItemLayoutBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding = itemBinding;
        }

        private void bind() {
            switch (type) {
                case 0:
                    itemBinding.icon.setImageResource(R.drawable.ustda);
                    itemBinding.name.setText("Enoh Titilayo");
                    itemBinding.description.setText("USTDA / Nigeria");
                    itemBinding.sendMsgBtn.setVisibility(View.VISIBLE);
                    break;
                case 1:
                    itemBinding.icon.setImageResource(R.drawable.tijdane);
                    itemBinding.name.setText("Tidjane Thiam");
                    itemBinding.description.setText("Lorem ipsum");
                    itemBinding.requestLayout.setVisibility(View.VISIBLE);
                    break;
                case 2:
                    itemBinding.icon.setImageResource(R.drawable.saki);
                    itemBinding.name.setText("Saki Macozoma");
                    itemBinding.description.setText("Lorem ipsum");
                    itemBinding.msgBtn.setVisibility(View.VISIBLE);
                    itemBinding.getRoot().setOnClickListener(v -> ((DashboardActivity) context).replaceFragment(new ContactDetailsFragment()));
                    break;
            }

        }
    }
}