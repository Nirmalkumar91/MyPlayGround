package com.nish.android.playground.common;

import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.databinding.BindingAdapter;

public class WebBindingAdapter {

    @BindingAdapter({"setWebViewClient", "userAgent"})
    public static void setWebViewClient(WebView view, WebViewClient client, String userAgent) {
        view.setWebViewClient(client);
        WebSettings webSettings = view.getSettings();
        webSettings.setUserAgentString(userAgent);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setSupportMultipleWindows(true);
        webSettings.setLoadWithOverviewMode(true);
    }

    @BindingAdapter("loadUrl")
    public static void loadUrl(WebView view, String url) {
        view.loadUrl(url);
    }

}
