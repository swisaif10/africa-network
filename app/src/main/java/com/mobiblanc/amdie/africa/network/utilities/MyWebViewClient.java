package com.mobiblanc.amdie.africa.network.utilities;

import android.app.AlertDialog;
import android.net.http.SslError;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MyWebViewClient extends WebViewClient {

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        return false;
    }

    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setMessage("Attention");
        builder.setPositiveButton("Ok", (dialog, which) -> handler.proceed());
        builder.setNegativeButton("Cancel", (dialog, which) -> handler.cancel());
        final AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
    }
}
