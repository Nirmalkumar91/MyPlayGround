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
            subscribeOn(addressbookHomeProvider.getAddressbookHome().subscribe(this::onAddressbookHomeSetSuccess, this::onAddressbookHomeSetError));
        }
    }

    private void onAddressbookHomeSetError(Throwable throwable) {
        Log.e("*******", throwable.getMessage(), throwable);
    }

    private void onAddressbookHomeSetSuccess(DavMultiStatus davMultiStatus) {
        Log.e("*******", davMultiStatus.getResponses().get(0).getPropstat().getStatus());
        Log.e("*******", davMultiStatus.getResponses().get(0).getPropstat().getProp().getAddressbookHome().getHref());

        sharedPrefUtil.setAddressbookUrl(davMultiStatus.getResponses().get(0).getPropstat().getProp().getAddressbookHome().getHref());
        subscribeOn(addressbookHomeProvider.getAddressbook().subscribe(this::onAddressbookSuccess, this::onAddressbookHomeSetError));
    }

    private void onAddressbookSuccess(DavMultiStatus davMultiStatus) {
        Log.e("*******", davMultiStatus.getResponses().get(0).getHref());
        Log.e("*******", davMultiStatus.getResponses().get(0).getPropstat().getStatus());
        Log.e("*******", davMultiStatus.getResponses().get(0).getPropstat().getProp().getDisplayName());
        Log.e("*******", "is Coll :" + davMultiStatus.getResponses().get(0).getPropstat().getProp().getResourceType().getCollection());
        Log.e("*******", "is AB :" + davMultiStatus.getResponses().get(0).getPropstat().getProp().getResourceType().getAddressbook());
        Log.e("*******", "is Prin :" + davMultiStatus.getResponses().get(0).getPropstat().getProp().getResourceType().getPrincipal());

        Log.e("*******", davMultiStatus.getResponses().get(1).getHref());
        Log.e("*******", davMultiStatus.getResponses().get(1).getPropstat().getStatus());
        Log.e("*******", davMultiStatus.getResponses().get(1).getPropstat().getProp().getDisplayName());
        Log.e("*******", "is Coll :" + davMultiStatus.getResponses().get(1).getPropstat().getProp().getResourceType().getCollection());
        Log.e("*******", "is AB :" + davMultiStatus.getResponses().get(1).getPropstat().getProp().getResourceType().getAddressbook());
        Log.e("*******", "is Prin :" + davMultiStatus.getResponses().get(1).getPropstat().getProp().getResourceType().getPrincipal());

    }

    public void logout() {
        sharedPrefUtil.clearAuthCode();
        sharedPrefUtil.clearAuthToken();
    }
}
