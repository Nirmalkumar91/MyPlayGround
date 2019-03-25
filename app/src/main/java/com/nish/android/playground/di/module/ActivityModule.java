package com.nish.android.playground.di.module;

import com.nish.android.playground.activity.LandingActivity;
import com.nish.android.playground.activity.SplashActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityModule {

    @ContributesAndroidInjector
    abstract SplashActivity bindSplashActivity();

    @ContributesAndroidInjector
    abstract LandingActivity bindLandingActivity();
}
