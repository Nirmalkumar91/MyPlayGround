package com.nish.android.playground.dagger.module;

import android.app.Application;
import android.content.Context;

import com.nish.android.playground.common.UseCaseDataProvider;

import javax.inject.Named;

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
}
