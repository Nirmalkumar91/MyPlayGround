package com.nish.android.playground.splash;

import android.text.TextUtils;
import android.util.Log;

import com.nish.android.playground.login.LoginActivity;
import com.nish.android.playground.userdb.UserProfileDatabase;
import com.nish.android.playground.sync.SyncActivity;
import com.nish.android.playground.common.BaseViewModel;
import com.nish.android.playground.common.SharedPrefUtil;
import com.nish.android.playground.common.events.StartActivityEvent;
import com.nish.android.playground.common.ViewEventBus;

import javax.inject.Inject;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;

public class SplashViewModel extends BaseViewModel {

    private ViewEventBus viewEventBus;
    private UserProfileDatabase userSessionDBProvider;
    private String email;

    @Inject
    public SplashViewModel(SharedPrefUtil sharedPrefUtil, ViewEventBus eventBus, UserProfileDatabase userSessionDBProvider) {
        this.viewEventBus = eventBus;
        this.userSessionDBProvider = userSessionDBProvider;
        this.email = sharedPrefUtil.getUserEmail();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume() {
        String token = userSessionDBProvider.getAccessToken(email);
        Log.d("********", "Access token: " + token);
        if (TextUtils.isEmpty(token)) {
            launchLoginActivity();
        } else {
            launchSyncActivity();
        }
    }

    private void launchLoginActivity() {
        StartActivityEvent event = StartActivityEvent.getEventBuilder(this)
                .setActivity(LoginActivity.class)
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
