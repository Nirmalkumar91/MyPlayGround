package com.nish.android.playground.landing;

import android.text.TextUtils;
import android.util.Log;

import com.nish.android.playground.common.BaseViewModel;
import com.nish.android.playground.common.SharedPrefUtil;
import com.nish.android.playground.discovery.AddressbookHomeProvider;
import com.nish.android.playground.discovery.DavMultiStatus;

import javax.inject.Inject;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;

public class LandingViewModel extends BaseViewModel {

    private SharedPrefUtil sharedPrefUtil;

    @Inject
    public LandingViewModel(SharedPrefUtil sharedPrefUtil) {
        this.sharedPrefUtil = sharedPrefUtil;
    }

    public void logout() {
        sharedPrefUtil.clearAuthCode();
        sharedPrefUtil.clearEmail();
        sharedPrefUtil.clearAuthToken();
    }
}
