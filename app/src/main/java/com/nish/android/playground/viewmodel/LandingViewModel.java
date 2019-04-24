package com.nish.android.playground.viewmodel;

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

    private AddressbookHomeProvider addressbookHomeProvider;
    private SharedPrefUtil sharedPrefUtil;

    @Inject
    public LandingViewModel(AddressbookHomeProvider addressbookHomeProvider, SharedPrefUtil sharedPrefUtil) {
        this.addressbookHomeProvider = addressbookHomeProvider;
        this.sharedPrefUtil = sharedPrefUtil;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume() {
        if(!TextUtils.isEmpty(sharedPrefUtil.getUserEmail())) {
            subscribeOn(addressbookHomeProvider.getAddressbookHome().subscribe(this::onSuccess, this::onError));
        }
    }

    private void onError(Throwable throwable) {
        Log.e("*******", throwable.getMessage(), throwable);
    }

    private void onSuccess(DavMultiStatus davMultiStatus) {
        Log.e("*******", davMultiStatus.getResponse().getPropstat().getStatus());
        Log.e("*******", davMultiStatus.getResponse().getPropstat().getProp().getAddressbookHome().getHref());


    }

    public void logout() {
        sharedPrefUtil.clearAuthCode();
        sharedPrefUtil.clearAuthToken();
    }
}
