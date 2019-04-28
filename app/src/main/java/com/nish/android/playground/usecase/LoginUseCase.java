package com.nish.android.playground.usecase;

import com.nish.android.playground.common.UseCase;

public class LoginUseCase extends UseCase {

    private String authCode;
    private String error;

    public LoginUseCase(String authCode, String error) {
        this.authCode = authCode;
        this.error = error;
    }

    public String getAuthCode() {
        return authCode;
    }

    public String getError() {
        return error;
    }
}
