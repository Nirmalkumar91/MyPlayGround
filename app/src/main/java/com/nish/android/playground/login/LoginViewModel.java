package com.nish.android.playground.login;

import com.nish.android.playground.common.BaseViewModel;
import com.nish.android.playground.common.ViewEventBus;
import com.nish.android.playground.common.events.OpenCustomTabEvent;

import javax.inject.Inject;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;

public class LoginViewModel extends BaseViewModel {

    private ViewEventBus viewEventBus;
    private LoginConfig loginConfig;

    @Inject
    public LoginViewModel(ViewEventBus viewEventBus, LoginConfig loginConfig) {
        this.viewEventBus = viewEventBus;
        this.loginConfig = loginConfig;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume() {
        launchCustomTabLogin();
    }

    private void launchCustomTabLogin() {
        OpenCustomTabEvent customTabEvent = OpenCustomTabEvent.getEventBuilder(this)
                .setUrl(loginConfig.getWebLoginUrl(null))
                .setFinishActivity(false)
                .build();

        viewEventBus.send(customTabEvent);
    }
}
