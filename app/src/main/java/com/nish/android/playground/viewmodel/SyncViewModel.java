package com.nish.android.playground.viewmodel;

import android.text.TextUtils;
import android.util.Log;

import com.nish.android.playground.activity.LandingActivity;
import com.nish.android.playground.common.BaseViewModel;
import com.nish.android.playground.common.SharedPrefUtil;
import com.nish.android.playground.common.ViewEventBus;
import com.nish.android.playground.common.events.StartActivityEvent;
import com.nish.android.playground.discovery.AddressbookHomeProvider;
import com.nish.android.playground.discovery.DavMultiStatus;
import com.nish.android.playground.oauth.OAuthToken;
import com.nish.android.playground.oauth.OAuthTokenProvider;

import javax.inject.Inject;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;

public class SyncViewModel extends BaseViewModel {

    private ViewEventBus viewEventBus;
    private SharedPrefUtil sharedPrefUtil;
    private OAuthTokenProvider oAuthTokenProvider;
    private AddressbookHomeProvider addressbookHomeProvider;

    @Inject
    public SyncViewModel(ViewEventBus eventBus, SharedPrefUtil sharedPrefUtil, OAuthTokenProvider oAuthTokenProvider, AddressbookHomeProvider addressbookHomeProvider) {
        this.viewEventBus = eventBus;
        this.sharedPrefUtil = sharedPrefUtil;
        this.oAuthTokenProvider = oAuthTokenProvider;
        this.addressbookHomeProvider = addressbookHomeProvider;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume() {
        if(!TextUtils.isEmpty(sharedPrefUtil.getOAuthCode())) {
            if(!TextUtils.isEmpty(sharedPrefUtil.getOAuthToken())) {
                syncContacts();
            } else {
                subscribeOn(oAuthTokenProvider.getAccessToken().subscribe(this::onAuthSuccess, this::onAuthFailure));
            }
        }
    }

    private void syncContacts() {
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
        subscribeOn(addressbookHomeProvider.getAddressbook().subscribe(this::onAddressbookSuccess, this::onAddressbookError));
    }

    private void onAddressbookError(Throwable throwable) {
        Log.e("*******", throwable.getMessage(), throwable);
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

    private void onAuthFailure(Throwable throwable) {
        sharedPrefUtil.clearAuthCode();
        sharedPrefUtil.clearAuthToken();
        sharedPrefUtil.clearEmail();
        Log.e("LOGIN", "Login failed", throwable);
    }

    private void onAuthSuccess(OAuthToken oAuthToken) {
        sharedPrefUtil.setOAuthToken(oAuthToken.getAccessToken());
        syncContacts();
    }

    private void launchLandingActivity() {
        StartActivityEvent event = StartActivityEvent.getEventBuilder(this)
                .setActivity(LandingActivity.class)
                .setFinishActivity(true)
                .build();

        viewEventBus.send(event);
    }
}
