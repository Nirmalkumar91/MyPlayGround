package com.nish.android.playground.login;

import android.net.Uri;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.nish.android.playground.AppConstants;
import com.nish.android.playground.common.UseCaseDataProvider;
import com.nish.android.playground.usecase.LoginUseCase;

import javax.inject.Inject;

public class LoginWebViewClient extends WebViewClient {

    private UseCaseDataProvider useCaseDataProvider;

    @Inject
    public LoginWebViewClient(UseCaseDataProvider useCaseDataProvider) {
        this.useCaseDataProvider = useCaseDataProvider;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        Uri uri = Uri.parse(url);

        if(uri.getScheme().equalsIgnoreCase(AppConstants.REDIRECT_URI_ROOT)) {
            String code = uri.getQueryParameter(AppConstants.CODE);
            String error = uri.getQueryParameter(AppConstants.ERROR_CODE);
            useCaseDataProvider.save(new LoginUseCase(code, error));
            return true;
        }
        return false;
    }
}
