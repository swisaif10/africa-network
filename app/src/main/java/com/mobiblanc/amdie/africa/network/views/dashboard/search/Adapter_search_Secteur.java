package com.mobiblanc.amdie.africa.network.views.dashboard.search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobiblanc.amdie.africa.network.databinding.AdapterCheckboxBinding;
import com.mobiblanc.amdie.africa.network.models.search.init_montoring.ObjetReferenceValuesItem;
import com.mobiblanc.amdie.africa.network.models.search.init_montoring.Secteur;


public class Adapter_search_Secteur extends RecyclerView.Adapter<Adapter_search_Secteur.ViewHolder> {

    private final Context context;
    private final Secteur data;
    private final CheckedLisner checkedLisner;


    public Adapter_search_Secteur(Context context, Secteur data , CheckedLisner checkedLisner ) {
        this.context = context;
        this.data = data;

        this.checkedLisner = checkedLisner;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(AdapterCheckboxBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(data.getObjetReferenceValues().get(position));
    }

    @Override
    public int getItemCount() {
        return data.getObjetReferenceValues().size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        AdapterCheckboxBinding itemBinding;

        ViewHolder(AdapterCheckboxBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding = itemBinding;
        }

        private void bind(ObjetReferenceValuesItem objetReferenceValuesItem) {
              itemBinding.checkBox.setText(objetReferenceValuesItem.getName());
              itemBinding.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                  objetReferenceValuesItem.setChecked(isChecked);
                  if (nbr_check() <data.getMax()){
                      enable_check(true);
                  }else {
                      enable_check(false);
                  }

              });

            itemBinding.checkBox.setEnabled(objetReferenceValuesItem.isEnable());

        }
    }
    void enable_check(Boolean enable){
        for (int i=0 ;i< data.getObjetReferenceValues().size();i++){
            if (!data.getObjetReferenceValues().get(i).isChecked())
                data.getObjetReferenceValues().get(i).setEnable(enable);
        }
        notifyDataSetChanged();
        checkedLisner.Checked_Secteur(nbr_check()>0,getList_id_String(),getList_name_String());
    }
    int nbr_check(){
        int nn=0;
        for (int i=0 ;i< data.getObjetReferenceValues().size();i++){
            if (data.getObjetReferenceValues().get(i).isChecked())
               nn++;
        }
         return nn;
    }
    String getList_id_String(){
        String s="";
        for (int i=0 ;i< data.getObjetReferenceValues().size();i++){
            if (data.getObjetReferenceValues().get(i).isChecked())
                s=s+  data.getObjetReferenceValues().get(i).getId()+",";
        }
        return s;
    }
    String getList_name_String(){
        String s="";
        for (int i=0 ;i< data.getObjetReferenceValues().size();i++){
            if (data.getObjetReferenceValues().get(i).isChecked())
                s=s+  data.getObjetReferenceValues().get(i).getName()+", ";
        }
        return s;
    }
}