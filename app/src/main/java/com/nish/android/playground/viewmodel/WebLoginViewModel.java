package com.nish.android.playground.viewmodel;

import com.nish.android.playground.activity.SyncActivity;
import com.nish.android.playground.common.BaseViewModel;
import com.nish.android.playground.common.SharedPrefUtil;
import com.nish.android.playground.common.UseCaseDataProvider;
import com.nish.android.playground.common.ViewEventBus;
import com.nish.android.playground.common.events.StartActivityEvent;
import com.nish.android.playground.login.LoginConfig;
import com.nish.android.playground.login.LoginWebViewClient;
import com.nish.android.playground.usecase.LoginUseCase;

import javax.inject.Inject;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;

public class WebLoginViewModel extends BaseViewModel {

    public String url;
    public String userAgent;
    public LoginWebViewClient webViewClient;

    private LoginConfig loginConfig;
    private UseCaseDataProvider useCaseDataProvider;
    private SharedPrefUtil sharedPrefUtil;
    private ViewEventBus viewEventBus;

    @Inject
    public WebLoginViewModel(LoginWebViewClient webViewClient, LoginConfig loginConfig,
                             UseCaseDataProvider useCaseDataProvider, SharedPrefUtil sharedPrefUtil,
                             ViewEventBus viewEventBus) {
        this.webViewClient = webViewClient;
        this.loginConfig = loginConfig;
        this.useCaseDataProvider = useCaseDataProvider;
        this.sharedPrefUtil = sharedPrefUtil;
        this.viewEventBus = viewEventBus;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onCreate() {
        url = loginConfig.getWebLoginUrl();
        userAgent = "com.nish.android.playground";
        subscribeOn(useCaseDataProvider.observeUseCase(LoginUseCase.class).subscribe(clazz -> onLoginComplete()));
    }

    private void onLoginComplete() {
        LoginUseCase loginUseCase = useCaseDataProvider.get(LoginUseCase.class);
        sharedPrefUtil.setOAuthCode(loginUseCase.getAuthCode());
        launchSyncActivity();
    }

    private void launchSyncActivity() {
        StartActivityEvent event = StartActivityEvent.getEventBuilder(this)
                .setActivity(SyncActivity.class)
                .setFinishActivity(true)
                .build();

        viewEventBus.send(event);
    }
}
