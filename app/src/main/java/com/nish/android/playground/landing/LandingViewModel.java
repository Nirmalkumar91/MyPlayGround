package com.nish.android.playground.landing;

import com.nish.android.playground.common.BaseViewModel;
import com.nish.android.playground.common.SharedPrefUtil;
import com.nish.android.playground.common.ViewEventBus;
import com.nish.android.playground.userdb.UserProfileDatabase;

import javax.inject.Inject;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;

public class LandingViewModel extends BaseViewModel {

    private SharedPrefUtil sharedPrefUtil;
    private UserProfileDatabase userSessionDBProvider;
    private ViewEventBus viewEventBus;

    @Inject
    public LandingViewModel(SharedPrefUtil sharedPrefUtil, UserProfileDatabase userSessionDBProvider, ViewEventBus viewEventBus) {
        this.sharedPrefUtil = sharedPrefUtil;
        this.userSessionDBProvider = userSessionDBProvider;
        this.viewEventBus = viewEventBus;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume() {

    }

    public void logout() {
        userSessionDBProvider.clearUserProfile(sharedPrefUtil.getUserEmail());
    }
}
