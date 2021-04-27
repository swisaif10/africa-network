package com.mobiblanc.amdie.africa.network.views.authentication.mobileregister;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mobiblanc.amdie.africa.network.R;
import com.mobiblanc.amdie.africa.network.Utilities.GlideApp;
import com.mobiblanc.amdie.africa.network.models.countries.Country;

import java.util.List;

public class CountriesAdapter extends ArrayAdapter {

    private Activity activity;

    public CountriesAdapter(@NonNull Context context, int resource, int textViewResourceId, @NonNull List<Country> countries, Activity activity) {
        super(context, resource, textViewResourceId, countries);
        this.activity = activity;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.custom_country_dropdown_item_layout, parent, false);
        }

        Country country = (Country) getItem(position);
        ImageView flag = (ImageView) convertView.findViewById(R.id.flag);

        GlideApp.with(activity)
                .load(country.getFlag())
                .into(flag);

        TextView txtTitle = (TextView) convertView.findViewById(R.id.name);
        txtTitle.setText(String.format("%s (%s)", country.getTranslations().getFr(), country.getAlpha2Code()));
        TextView code = (TextView) convertView.findViewById(R.id.code);
        code.setText(String.format("+%s", country.getCallingCodes().get(0)));
        return convertView;
    }
}
