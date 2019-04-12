package com.nish.android.playground.viewmodel;

import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.nish.android.playground.AppConstants;
import com.nish.android.playground.activity.LandingActivity;
import com.nish.android.playground.common.BaseViewModel;
import com.nish.android.playground.common.SharedPrefUtil;
import com.nish.android.playground.common.ViewEventBus;
import com.nish.android.playground.common.events.StartActivityEvent;
import com.nish.android.playground.oauth.OAuthToken;
import com.nish.android.playground.oauth.OAuthTokenProvider;

import javax.inject.Inject;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;
import okhttp3.HttpUrl;

public class LoginViewModel extends BaseViewModel {

    private ViewEventBus viewEventBus;
    private SharedPrefUtil sharedPrefUtil;
    private OAuthTokenProvider oAuthTokenProvider;

    @Inject
    public LoginViewModel(ViewEventBus eventBus, SharedPrefUtil sharedPrefUtil, OAuthTokenProvider oAuthTokenProvider) {
        this.viewEventBus = eventBus;
        this.sharedPrefUtil = sharedPrefUtil;
        this.oAuthTokenProvider = oAuthTokenProvider;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume() {
        if(!TextUtils.isEmpty(sharedPrefUtil.getOAuthCode())) {
            if(!TextUtils.isEmpty(sharedPrefUtil.getOAuthToken())) {
                launchLandingActivity();
            } else {
                subscribeOn(oAuthTokenProvider.getAccessToken().subscribe(this::onAuthSuccess, this::onAuthFailure));
            }
        }
    }

    public void launchGoogleLogin() {
        StartActivityEvent startActivityEvent = StartActivityEvent.getEventBuilder(this)
                .isExternalApp(true)
                .setIntent(getAuthorizationIntent())
                .build();

        viewEventBus.send(startActivityEvent);
    }

    private void onAuthFailure(Throwable throwable) {
        Log.e("LOGIN", "Login failed", throwable);
    }

    private void onAuthSuccess(OAuthToken oAuthToken) {
        sharedPrefUtil.setOAuthToken(oAuthToken.getAccessToken());
        launchLandingActivity();
    }

    private Intent getAuthorizationIntent() {
        HttpUrl authorizeUrl = HttpUrl.parse("https://accounts.google.com/o/oauth2/v2/auth") //
                .newBuilder() //
                .addQueryParameter("client_id", AppConstants.CLIENT_ID)
                .addQueryParameter("scope", AppConstants.API_SCOPE)
                .addQueryParameter("redirect_uri", AppConstants.REDIRECT_URI)
                .addQueryParameter("response_type", AppConstants.CODE)
                .build();
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(String.valueOf(authorizeUrl.url())));
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        return intent;
    }

    private void launchLandingActivity() {
        StartActivityEvent event = StartActivityEvent.getEventBuilder(this)
                .setActivity(LandingActivity.class)
                .setFinishActivity(true)
                .build();

        viewEventBus.send(event);
    }
}
