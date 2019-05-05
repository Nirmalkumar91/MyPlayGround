package com.nish.android.playground.di.module;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    private Application application;

    public ApplicationModule(Application application) {
        this.application = application;
    }

    @Provides
    Context provideContext() {
        return application;
    }

    @Provides
    Application provideApplication() {
        return application;
    }

    @Provides
    SharedPreferences provideSharedPreferences() {
        return application.getSharedPreferences("nish-preference", Context.MODE_PRIVATE);
    }

    @Provides
    PackageManager providePackageManager() {
        return application.getPackageManager();
    }
}
