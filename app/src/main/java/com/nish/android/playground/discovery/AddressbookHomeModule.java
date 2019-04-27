package com.nish.android.playground.discovery;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.convert.AnnotationStrategy;
import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.strategy.Strategy;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

@Module
public class AddressbookHomeModule {

    @Provides
    @Singleton
    AddressBookHomeService provideAddressBookHomeService(OkHttpClient okHttpClient) {
        Strategy strategy = new AnnotationStrategy();
        Serializer serializer = new Persister(strategy);
        return new Retrofit.Builder()
                .addConverterFactory(SimpleXmlConverterFactory.createNonStrict(serializer))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(AddressbookConfig.BASE_URL)
                .client(okHttpClient)
                .build().create(AddressBookHomeService.class);
    }
}
