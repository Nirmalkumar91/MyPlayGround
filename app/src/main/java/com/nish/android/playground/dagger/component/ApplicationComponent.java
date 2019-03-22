package com.nish.android.playground.dagger.component;

import com.nish.android.playground.PlayGroundApplication;
import com.nish.android.playground.common.UseCaseDataProvider;
import com.nish.android.playground.dagger.module.ActivityModule;
import com.nish.android.playground.dagger.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {
        AndroidSupportInjectionModule.class,
        ActivityModule.class,
        ApplicationModule.class})
public interface ApplicationComponent extends AndroidInjector<PlayGroundApplication> {

    UseCaseDataProvider getUseCaseDataProvider();
}
