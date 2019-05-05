package com.nish.android.playground.di.module;

import android.content.pm.PackageManager;

import com.nish.android.playground.common.CustomTabHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class CommonModule {

    @Provides
    @Singleton
    CustomTabHelper providesCustomTabHelper(PackageManager packageManager) {
        return new CustomTabHelper(packageManager);
    }
}
