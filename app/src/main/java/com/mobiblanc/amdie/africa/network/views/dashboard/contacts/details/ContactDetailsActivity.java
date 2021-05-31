package com.mobiblanc.amdie.africa.network.views.dashboard.contacts.details;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.mobiblanc.amdie.africa.network.R;
import com.mobiblanc.amdie.africa.network.databinding.ActivityContactDetailsBinding;
import com.mobiblanc.amdie.africa.network.models.contacts.list.Contact;
import com.mobiblanc.amdie.africa.network.views.dashboard.profile.details.ProfileDetailsAdapter;

import java.text.MessageFormat;

public class ContactDetailsActivity extends AppCompatActivity {

    private ActivityContactDetailsBinding activityBinding;
    private Contact contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityBinding = DataBindingUtil.setContentView(this, R.layout.activity_contact_details);

        if (getIntent().hasExtra("contact"))
            contact = (Contact) getIntent().getSerializableExtra("contact");
    }

    @Override
    protected void onResume() {
        super.onResume();
        init();
    }

    private void init() {
        activityBinding.backBtn.setOnClickListener(v -> finish());

        Glide.with(this).load(contact.getCompanyPicture()).placeholder(R.drawable.avatar_office).into(activityBinding.companyPicture);
        if (contact.getGender().equalsIgnoreCase("man"))
            Glide.with(this).load(contact.getProfilePicture()).placeholder(R.drawable.men_avatar).into(activityBinding.profilePicture);
        else
            Glide.with(this).load(contact.getProfilePicture()).placeholder(R.drawable.women_avatar).into(activityBinding.profilePicture);

        if (!contact.getCurrency().isEmpty())
            if (contact.getCurrency().get(0).getName().equals("Dollars")) {
                activityBinding.revenues.setText(MessageFormat.format("{0} $", contact.getRevenue()));
            } else {
                activityBinding.revenues.setText(MessageFormat.format("{0} €", contact.getRevenue()));
            }
        else
            activityBinding.revenuesLayout.setVisibility(View.INVISIBLE);
        if (contact.getCountry().equalsIgnoreCase(""))
            activityBinding.countryLayout.setVisibility(View.INVISIBLE);
        else
            activityBinding.country.setText(contact.getCountry());
        if (contact.getCompanySize().equalsIgnoreCase(""))
            activityBinding.companySizeLayout.setVisibility(View.INVISIBLE);
        else
            activityBinding.companySize.setText(contact.getCompanySize());
        activityBinding.function.setText(contact.getFunction());
        activityBinding.companyName.setText(contact.getCompanyName());
        if (contact.getPresentation().equalsIgnoreCase("")) {
            activityBinding.presentation.setVisibility(View.GONE);
            activityBinding.presentationSeparator.setVisibility(View.GONE);
        } else
            activityBinding.presentation.setText(String.format("«\n%s", contact.getPresentation()));
        activityBinding.username.setText(contact.getUsername());
        if (contact.getProducts().equalsIgnoreCase(""))
            activityBinding.productsLayout.setVisibility(View.GONE);
        else
            activityBinding.products.setText(contact.getProducts());

        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(this);
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setJustifyContent(JustifyContent.FLEX_START);
        activityBinding.sectorsRecycler.setLayoutManager(layoutManager);
        activityBinding.sectorsRecycler.setAdapter(new ProfileDetailsAdapter(this, contact.getSectors(), "sectors"));

        activityBinding.topicsRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        activityBinding.topicsRecycler.setAdapter(new ProfileDetailsAdapter(this, contact.getTopics(), "topics"));

        activityBinding.body.setVisibility(View.VISIBLE);
    }
}