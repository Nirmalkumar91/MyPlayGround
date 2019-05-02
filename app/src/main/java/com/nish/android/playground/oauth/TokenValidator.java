package com.nish.android.playground.oauth;

import com.nish.android.playground.repository.UserProfileEntity;

import javax.inject.Inject;

public class TokenValidator {

    @Inject
    public TokenValidator() {

    }

    public boolean isTokenValid(UserProfileEntity profile) {
        return false;
    }
}
