package com.nish.android.playground.dagger.component;

import com.nish.android.playground.PlayGroundApplication;
import com.nish.android.playground.dagger.module.ActivityModule;
import com.nish.android.playground.dagger.module.ApplicationModule;

import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Component(modules = {
        AndroidSupportInjectionModule.class,
        ActivityModule.class,
        ApplicationModule.class})
public interface ApplicationComponent extends AndroidInjector<PlayGroundApplication> {

}
