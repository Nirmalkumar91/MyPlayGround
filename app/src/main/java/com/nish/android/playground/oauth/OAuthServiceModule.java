package com.nish.android.playground.oauth;

import com.google.gson.Gson;

import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class OAuthServiceModule {

    @Provides
    @Singleton
    OAuthService provideOAuthService(Gson gson, OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(OAuthConfig.BASE_URL)
                .client(okHttpClient)
                .build().create(OAuthService.class);
    }
}
