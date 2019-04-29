package com.nish.android.playground.di.module;

import com.nish.android.playground.landing.LandingActivity;
import com.nish.android.playground.splash.SplashActivity;
import com.nish.android.playground.sync.SyncActivity;
import com.nish.android.playground.login.WebLoginActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityModule {

    @ContributesAndroidInjector
    abstract SplashActivity bindSplashActivity();

    @ContributesAndroidInjector
    abstract WebLoginActivity bindWebLoginActivity();

    @ContributesAndroidInjector
    abstract SyncActivity bindSyncActivity();

    @ContributesAndroidInjector
    abstract LandingActivity bindLandingActivity();
}
