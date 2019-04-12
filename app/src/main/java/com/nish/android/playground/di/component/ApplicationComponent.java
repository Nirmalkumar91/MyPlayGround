package com.nish.android.playground.di.component;

import com.nish.android.playground.PlayGroundApplication;
import com.nish.android.playground.di.module.ActivityModule;
import com.nish.android.playground.di.module.ApplicationModule;
import com.nish.android.playground.oauth.OAuthServiceModule;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {
        AndroidSupportInjectionModule.class,
        ActivityModule.class,
        ApplicationModule.class,
        OAuthServiceModule.class})
public interface ApplicationComponent extends AndroidInjector<PlayGroundApplication> {

}
