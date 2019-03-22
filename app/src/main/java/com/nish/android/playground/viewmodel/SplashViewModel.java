package com.nish.android.playground.viewmodel;

import com.nish.android.playground.common.BaseViewModel;
import com.nish.android.playground.common.UseCaseDataProvider;
import com.nish.android.playground.usecase.MessageUseCase;

import javax.inject.Inject;

import androidx.databinding.ObservableField;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;

public class SplashViewModel extends BaseViewModel {

    public ObservableField<String> welcomeMessage = new ObservableField<>();

    private UseCaseDataProvider useCaseDataProvider;

    @Inject
    public SplashViewModel(UseCaseDataProvider useCaseDataProvider) {
        this.useCaseDataProvider = useCaseDataProvider;
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
    }

    public void launchLanding() {
        MessageUseCase useCase = new MessageUseCase("Welcome Play ground..!");
        useCaseDataProvider.save(useCase);
    }
}
