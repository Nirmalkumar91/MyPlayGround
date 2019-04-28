package com.nish.android.playground.di.module;

import com.nish.android.playground.activity.LandingActivity;
import com.nish.android.playground.activity.LoginActivity;
import com.nish.android.playground.activity.SplashActivity;
import com.nish.android.playground.activity.SyncActivity;
import com.nish.android.playground.activity.WebLoginActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityModule {

    @ContributesAndroidInjector
    abstract SplashActivity bindSplashActivity();

    @ContributesAndroidInjector
    abstract LoginActivity bindLoginActivity();

    @ContributesAndroidInjector
    abstract WebLoginActivity bindWebLoginActivity();

    @ContributesAndroidInjector
    abstract SyncActivity bindSyncActivity();

    @ContributesAndroidInjector
    abstract LandingActivity bindLandingActivity();
}
