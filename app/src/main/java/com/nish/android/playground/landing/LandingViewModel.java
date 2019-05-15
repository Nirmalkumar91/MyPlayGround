package com.nish.android.playground.landing;

import com.nish.android.playground.common.BaseViewModel;
import com.nish.android.playground.common.SharedPrefUtil;
import com.nish.android.playground.common.ViewEventBus;
import com.nish.android.playground.repository.NishRepository;

import javax.inject.Inject;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;

public class LandingViewModel extends BaseViewModel {

    private SharedPrefUtil sharedPrefUtil;
    private NishRepository nishRepository;
    private ViewEventBus viewEventBus;

    @Inject
    public LandingViewModel(SharedPrefUtil sharedPrefUtil, NishRepository nishRepository, ViewEventBus viewEventBus) {
        this.sharedPrefUtil = sharedPrefUtil;
        this.nishRepository = nishRepository;
        this.viewEventBus = viewEventBus;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume() {

    }

    public void logout() {
        nishRepository.clearUserProfile(sharedPrefUtil.getUserEmail());
    }
}
