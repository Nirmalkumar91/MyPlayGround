package com.nish.android.playground.splash;

import android.util.Log;

import com.nish.android.playground.sync.SyncActivity;
import com.nish.android.playground.login.WebLoginActivity;
import com.nish.android.playground.common.BaseViewModel;
import com.nish.android.playground.common.SharedPrefUtil;
import com.nish.android.playground.common.events.StartActivityEvent;
import com.nish.android.playground.common.ViewEventBus;
import com.nish.android.playground.oauth.TokenValidator;

import javax.inject.Inject;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;

public class SplashViewModel extends BaseViewModel {

    private ViewEventBus viewEventBus;
    private SharedPrefUtil sharedPrefUtil;
    private TokenValidator tokenValidator;

    @Inject
    public SplashViewModel(SharedPrefUtil sharedPrefUtil, ViewEventBus eventBus, TokenValidator tokenValidator) {
        this.sharedPrefUtil = sharedPrefUtil;
        this.viewEventBus = eventBus;
        this.tokenValidator = tokenValidator;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume() {
        if(tokenValidator.isTokenValid()) {
            Log.d("********", "Access token: " + sharedPrefUtil.getOAuthToken());
            launchSyncActivity();
        } else {
            launchLoginActivity();
        }
    }

    private void launchLoginActivity() {
        StartActivityEvent event = StartActivityEvent.getEventBuilder(this)
                .setActivity(WebLoginActivity.class)
                .setFinishActivity(true)
                .build();

        viewEventBus.send(event);
    }

    private void launchSyncActivity() {
        StartActivityEvent event = StartActivityEvent.getEventBuilder(this)
                .setActivity(SyncActivity.class)
                .setFinishActivity(true)
                .build();

        viewEventBus.send(event);
    }
}
