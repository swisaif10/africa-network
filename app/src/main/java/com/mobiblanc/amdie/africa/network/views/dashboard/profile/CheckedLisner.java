package com.mobiblanc.amdie.africa.network.views.dashboard.search;

public interface CheckedLisner {

    void Checked_Topics(Boolean checked,String list_id,String list_name);
    void Checked_Secteur(Boolean checked,String list_id,String list_name);
    void Checked_Siege(Boolean checked,String list_id,String list_name);
    void Checked_Devise(Boolean checked,String list_id,String list_name);
    void Checked_Produits(Boolean checked,String list_id,String list_name);
    void Checked_Effectif(Boolean checked,String list_id,String list_name);
}
