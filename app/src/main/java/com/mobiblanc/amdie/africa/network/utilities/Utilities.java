package com.mobiblanc.amdie.africa.network.utilities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.provider.Settings;
import android.text.Html;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.mobiblanc.amdie.africa.network.R;
import com.mobiblanc.amdie.africa.network.listeners.OnDialogButtonsClickListener;

public interface Utilities {
    static void hideSoftKeyboard(Context context, View view) {
        if (context == null || view == null) {
            return;
        }
        try {
            InputMethodManager inputMethodManager =
                    (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        } catch (Exception e) {
            Log.e("", e.getMessage());
        }
    }

    static Boolean isEmpty(EditText editText) {
        return editText.getText().toString().equalsIgnoreCase("");
    }

    static void showErrorPopup(Context context, String message) {

        if (context == null) {
            return;
        }

        final Dialog dialog = new Dialog(context, android.R.style.Theme_Translucent_NoTitleBar);

        View view = LayoutInflater.from(context).inflate(R.layout.server_error_dialog, null, false);
        Button ok = view.findViewById(R.id.okBtn);
        TextView msg = view.findViewById(R.id.message);
        ConstraintLayout container = view.findViewById(R.id.container);

        msg.setText(message);

        ok.setOnClickListener(v -> dialog.dismiss());
        container.setOnClickListener(v -> dialog.dismiss());
        dialog.setContentView(view);
        dialog.show();
    }

    static void showErrorPopupWithCLick(Context context, String message, View.OnClickListener onClickListener) {

        if (context == null) {
            return;
        }

        final Dialog dialog = new Dialog(context, android.R.style.Theme_Translucent_NoTitleBar);

        View view = LayoutInflater.from(context).inflate(R.layout.server_error_dialog, null, false);
        Button ok = view.findViewById(R.id.okBtn);
        TextView msg = view.findViewById(R.id.message);
        ConstraintLayout container = view.findViewById(R.id.container);

        msg.setText(message);

        ok.setOnClickListener(v -> {
            dialog.dismiss();
            onClickListener.onClick(v);
        });
        container.setOnClickListener(v -> dialog.dismiss());
        dialog.setContentView(view);
        dialog.show();
    }

    static void showUpdateDialog(Context context, String message, String status, OnDialogButtonsClickListener onDialogButtonsClickListener) {

        if (context == null) {
            return;
        }

        final Dialog dialog = new Dialog(context, android.R.style.Theme_Translucent_NoTitleBar);

        View view = LayoutInflater.from(context).inflate(R.layout.version_update_dialog, null, false);
        Button update = view.findViewById(R.id.updateBtn);
        Button cancel = view.findViewById(R.id.cancelBtn);
        TextView msg = view.findViewById(R.id.message);

        msg.setText(Html.fromHtml(message));

        if (status.equalsIgnoreCase("blocked"))
            cancel.setVisibility(View.GONE);

        update.setOnClickListener(v -> {
            dialog.dismiss();
            onDialogButtonsClickListener.firstChoice();
        });
        cancel.setOnClickListener(v -> {
            dialog.dismiss();
            onDialogButtonsClickListener.secondChoice();
        });
        dialog.setContentView(view);
        dialog.show();
    }

    static void showPhotoDialog(Context context, OnDialogButtonsClickListener onDialogButtonsClickListener) {

        if (context == null) {
            return;
        }

        final Dialog dialog = new Dialog(context, android.R.style.Theme_Translucent_NoTitleBar);

        View view = LayoutInflater.from(context).inflate(R.layout.dialog_photo, null, false);
        TextView update = view.findViewById(R.id.photolBtn);
        TextView cancel = view.findViewById(R.id.galerieBtn);

        update.setOnClickListener(v -> {
            dialog.dismiss();
            onDialogButtonsClickListener.firstChoice();
        });
        cancel.setOnClickListener(v -> {
            dialog.dismiss();
            onDialogButtonsClickListener.secondChoice();
        });
        dialog.setContentView(view);
        dialog.show();
    }

    static String getUID(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    static boolean isEmailValid(String email) {
        if (email.equals(""))
            return false;
        else
            return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
