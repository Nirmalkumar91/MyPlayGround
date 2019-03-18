package com.nish.android.playground.viewmodel;

import javax.inject.Inject;

import androidx.databinding.ObservableField;

public class SplashViewModel {

    public ObservableField<String> welcomeMessage = new ObservableField<>("Welcome Play ground..!");

    @Inject
    public SplashViewModel() {}
}
