package com.nish.android.playground.oauth;

import com.nish.android.playground.AppConstants;
import com.nish.android.playground.network.SchedulerTransformer;

import javax.inject.Inject;

import io.reactivex.Observable;

public class OAuthTokenProvider {

    private OAuthService oAuthService;
    private SchedulerTransformer schedulerTransformer;

    @Inject
    public OAuthTokenProvider(OAuthService oAuthService, SchedulerTransformer schedulerTransformer) {
        this.oAuthService = oAuthService;
        this.schedulerTransformer = schedulerTransformer;
    }

    public Observable<OAuthToken> getAccessToken(String oAuthCode) {
        return oAuthService
                .requestToken(oAuthCode, AppConstants.CLIENT_ID, AppConstants.REDIRECT_URI, AppConstants.GRANT_TYPE_AUTHORIZATION_CODE)
                .compose(schedulerTransformer.getSchedulerTransformer());
    }

    public Observable<OAuthToken> refreshAccessToken(String refreshToken) {
        return oAuthService
                .refreshToken(refreshToken, AppConstants.CLIENT_ID, AppConstants.GRANT_TYPE_REFRESH_TOKEN)
                .compose(schedulerTransformer.getSchedulerTransformer());
    }

    public Observable<String> revokeToken() {
        return null;
    }
}
