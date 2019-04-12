package com.nish.android.playground.oauth;

import com.nish.android.playground.AppConstants;
import com.nish.android.playground.common.SharedPrefUtil;
import com.nish.android.playground.network.SchedulerTransformer;

import javax.inject.Inject;

import io.reactivex.Observable;

public class OAuthTokenProvider {

    private OAuthService oAuthService;
    private SharedPrefUtil sharedPrefUtil;
    private SchedulerTransformer schedulerTransformer;

    @Inject
    public OAuthTokenProvider(OAuthService oAuthService, SharedPrefUtil sharedPrefUtil, SchedulerTransformer schedulerTransformer) {
        this.oAuthService = oAuthService;
        this.sharedPrefUtil = sharedPrefUtil;
        this.schedulerTransformer = schedulerTransformer;
    }

    public Observable<OAuthToken> getAccessToken() {
        return oAuthService
                .requestToken(sharedPrefUtil.getOAuthCode(), AppConstants.CLIENT_ID, AppConstants.REDIRECT_URI, AppConstants.GRANT_TYPE_AUTHORIZATION_CODE)
                .compose(schedulerTransformer.getSchedulerTransformer());
    }
}
