package com.nish.android.playground.discovery;

import com.nish.android.playground.common.SharedPrefUtil;
import com.nish.android.playground.network.SchedulerTransformer;

import javax.inject.Inject;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class AddressbookHomeProvider {

    private AddressBookHomeService addressBookHomeService;
    private SharedPrefUtil sharedPrefUtil;
    private AddressbookConfig addressbookConfig;
    private SchedulerTransformer schedulerTransformer;

    @Inject
    public AddressbookHomeProvider(AddressBookHomeService addressBookHomeService, SharedPrefUtil sharedPrefUtil, AddressbookConfig addressbookConfig, SchedulerTransformer schedulerTransformer) {
        this.addressBookHomeService = addressBookHomeService;
        this.sharedPrefUtil = sharedPrefUtil;
        this.addressbookConfig = addressbookConfig;
        this.schedulerTransformer = schedulerTransformer;
    }

    public Observable<DavMultiStatus> getAddressbookHome() {
        RequestBody body = RequestBody.create(MediaType.parse("application/xml"),
                addressbookConfig.getRequestBody(Properties.RESOURCE_TYPE, Properties.ADDRESSBOOK_HOME_SET));
        return addressBookHomeService.getAddressbookHomeSet(AddressbookConfig.getAddressbookHomeSetPath(sharedPrefUtil.getUserEmail()),
                        body, "Bearer " + sharedPrefUtil.getOAuthToken())
                .compose(schedulerTransformer.getSchedulerTransformer());
    }

    public Observable<DavMultiStatus> getAddressbook() {
        RequestBody body =
                RequestBody.create(MediaType.parse("application/xml"),
                        addressbookConfig.getRequestBody(Properties.RESOURCE_TYPE, Properties.DISPLAY_NAME));
        return addressBookHomeService
                .getAddressbook(addressbookConfig.getAddressbookUrl(),
                        body, "Bearer " + sharedPrefUtil.getOAuthToken(), "1")
                .compose(schedulerTransformer.getSchedulerTransformer());
    }
}
