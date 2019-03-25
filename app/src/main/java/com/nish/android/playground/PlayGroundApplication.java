package com.nish.android.playground;

import android.app.Activity;
import android.app.Application;

import com.nish.android.playground.di.component.ApplicationComponent;
import com.nish.android.playground.di.component.DaggerApplicationComponent;
import com.nish.android.playground.di.module.ApplicationModule;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

public class PlayGroundApplication extends Application implements HasActivityInjector {

    private static ApplicationComponent applicationComponent;

    @Inject
    DispatchingAndroidInjector<Activity> dispatchingActivityInjector;

    @Override
    public void onCreate() {
        super.onCreate();
        buildComponent();
        applicationComponent.inject(this);
    }

    public static ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return dispatchingActivityInjector;
    }

    private void buildComponent() {
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }
}