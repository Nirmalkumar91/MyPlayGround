package com.nish.android.playground.login;

import com.nish.android.playground.AppConstants;

import javax.inject.Inject;

import okhttp3.HttpUrl;

public class LoginConfig {

    private static final String URL = "https://accounts.google.com/o/oauth2/v2/auth";

    @Inject
    public LoginConfig() {

    }

    public String getWebLoginUrl(String email) {
        HttpUrl webLoginUrl = HttpUrl.parse(URL)
                .newBuilder()
                .addQueryParameter("client_id", AppConstants.CLIENT_ID)
                .addQueryParameter("scope", AppConstants.API_SCOPE)
                .addQueryParameter("redirect_uri", AppConstants.REDIRECT_URI)
                .addQueryParameter("response_type", AppConstants.CODE)
                .addQueryParameter("access_type", AppConstants.ACCESS_TYPE)
                .addQueryParameter("login_hint", email)
                .build();
        return webLoginUrl.toString();
    }
}
