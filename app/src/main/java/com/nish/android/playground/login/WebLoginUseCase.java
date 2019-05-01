package com.nish.android.playground.login;

import com.nish.android.playground.common.UseCase;

public class WebLoginUseCase extends UseCase {

    private String authCode;
    private String error;

    public WebLoginUseCase(String authCode, String error) {
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
