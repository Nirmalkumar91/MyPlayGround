package com.nish.android.playground.oauth;

import android.text.TextUtils;

import com.nish.android.playground.common.SharedPrefUtil;

import javax.inject.Inject;

public class TokenValidator {

    private SharedPrefUtil sharedPrefUtil;

    @Inject
    public TokenValidator(SharedPrefUtil sharedPrefUtil) {
        this.sharedPrefUtil = sharedPrefUtil;
    }

    public boolean isTokenValid() {
        return !TextUtils.isEmpty(sharedPrefUtil.getOAuthToken());
    }
}
