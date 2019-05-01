package com.nish.android.playground.splash;

import android.text.TextUtils;
import android.util.Log;

import com.nish.android.playground.repository.NishRepository;
import com.nish.android.playground.repository.UserProfileEntity;
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
    private NishRepository nishRepository;

    @Inject
    public SplashViewModel(SharedPrefUtil sharedPrefUtil, ViewEventBus eventBus,
                           TokenValidator tokenValidator, NishRepository nishRepository) {
        this.sharedPrefUtil = sharedPrefUtil;
        this.viewEventBus = eventBus;
        this.tokenValidator = tokenValidator;
        this.nishRepository = nishRepository;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume() {
        String email = sharedPrefUtil.getUserEmail();
        if (TextUtils.isEmpty(email)) {
            launchLoginActivity();
        } else {
            subscribeOn(nishRepository.getUserProfile(email).subscribe(this::validateUserProfile, this::onUserProfileError));
        }
    }

    private void onUserProfileError(Throwable throwable) {
        Log.e("********", throwable.getMessage(), throwable);
        launchLoginActivity();
    }

    private void validateUserProfile(UserProfileEntity userProfile) {
        if (tokenValidator.isTokenValid(userProfile)) {
            Log.d("********", "Access token: " + userProfile.getAccessToken());
            launchSyncActivity();
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
