package com.nish.android.playground.common;

import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.databinding.BindingAdapter;

public class WebBindingAdapter {

    @BindingAdapter("setWebViewClient")
    public static void setWebViewClient(WebView view, WebViewClient client) {
        view.setWebViewClient(client);
        WebSettings webSettings = view.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setSupportMultipleWindows(true);
    }

    @BindingAdapter("loadUrl")
    public static void loadUrl(WebView view, String url) {
        view.loadUrl(url);
    }

    @BindingAdapter("userAgent")
    public static void setUserAgent(WebView view, String userAgent) {
        view.getSettings().setUserAgentString(userAgent);
    }
}
