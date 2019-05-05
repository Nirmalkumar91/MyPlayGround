package com.nish.android.playground.di.component;

import com.nish.android.playground.PlayGroundApplication;
import com.nish.android.playground.di.module.ActivityModule;
import com.nish.android.playground.di.module.ApplicationModule;
import com.nish.android.playground.di.module.CommonModule;
import com.nish.android.playground.di.module.DatabaseModule;
import com.nish.android.playground.di.module.NetworkModule;
import com.nish.android.playground.discovery.AddressbookHomeModule;
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
        NetworkModule.class,
        OAuthServiceModule.class,
        AddressbookHomeModule.class,
        DatabaseModule.class,
        CommonModule.class})
public interface ApplicationComponent extends AndroidInjector<PlayGroundApplication> {

}
