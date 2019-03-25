package com.nish.android.playground.viewmodel;

import com.nish.android.playground.activity.LandingActivity;
import com.nish.android.playground.common.BaseViewModel;
import com.nish.android.playground.common.events.StartActivityEvent;
import com.nish.android.playground.common.UseCaseDataProvider;
import com.nish.android.playground.common.ViewEventBus;
import com.nish.android.playground.usecase.MessageUseCase;

import javax.inject.Inject;

import androidx.databinding.ObservableField;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;

public class SplashViewModel extends BaseViewModel {

    public ObservableField<String> welcomeMessage = new ObservableField<>();

    private ViewEventBus viewEventBus;
    private UseCaseDataProvider useCaseDataProvider;

    @Inject
    public SplashViewModel(UseCaseDataProvider useCaseDataProvider, ViewEventBus eventBus) {
        this.useCaseDataProvider = useCaseDataProvider;
        this.viewEventBus = eventBus;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume() {
        subscribeOn(useCaseDataProvider.observeUseCase(MessageUseCase.class)
                .subscribe(clazz -> onSuccess(), throwable -> onError()));
    }

    private void onError() {

    }

    private void onSuccess() {
        welcomeMessage.set(useCaseDataProvider.get(MessageUseCase.class).getMessage());

        StartActivityEvent event = StartActivityEvent.getEventBuilder(this)
                .setActivity(LandingActivity.class)
                .build();

        viewEventBus.send(event);
    }

    public void launchLanding() {
        MessageUseCase useCase = new MessageUseCase("Welcome Play ground..!");
        useCaseDataProvider.save(useCase);
    }
}
