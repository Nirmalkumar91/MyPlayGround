package com.nish.android.playground.addressbook;

import com.nish.android.playground.common.SharedPrefUtil;
import com.nish.android.playground.network.SchedulerTransformer;
import com.nish.android.playground.userdb.UserProfileDatabase;

import javax.inject.Inject;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class AddressbookHomeProvider {

    private AddressBookHomeService addressBookHomeService;
    private SharedPrefUtil sharedPrefUtil;
    private AddressbookConfig addressbookConfig;
    private SchedulerTransformer schedulerTransformer;
    private UserProfileDatabase userProfileDatabase;

    @Inject
    public AddressbookHomeProvider(AddressBookHomeService addressBookHomeService, SharedPrefUtil sharedPrefUtil,
                                   AddressbookConfig addressbookConfig, SchedulerTransformer schedulerTransformer,
                                   UserProfileDatabase userProfileDatabase) {
        this.addressBookHomeService = addressBookHomeService;
        this.sharedPrefUtil = sharedPrefUtil;
        this.addressbookConfig = addressbookConfig;
        this.schedulerTransformer = schedulerTransformer;
        this.userProfileDatabase = userProfileDatabase;
    }

    public Observable<DavMultiStatus> getAddressbookHome() {
        String email = sharedPrefUtil.getUserEmail();
        RequestBody body = RequestBody.create(MediaType.parse("application/xml"),
                addressbookConfig.getRequestBody(Properties.RESOURCE_TYPE, Properties.ADDRESSBOOK_HOME_SET));
        return addressBookHomeService.getAddressbookHomeSet(AddressbookConfig.getAddressbookHomeSetPath(email),
                        body, "Bearer " + userProfileDatabase.getAccessToken(email))
                .compose(schedulerTransformer.getSchedulerTransformer());
    }

    public Observable<DavMultiStatus> getAddressbook() {
        String email = sharedPrefUtil.getUserEmail();
        RequestBody body =
                RequestBody.create(MediaType.parse("application/xml"),
                        addressbookConfig.getRequestBody(Properties.RESOURCE_TYPE, Properties.DISPLAY_NAME));
        return addressBookHomeService
                .getAddressbook(addressbookConfig.getAddressbookUrl(),
                        body, "Bearer " + userProfileDatabase.getAccessToken(email), "1")
                .compose(schedulerTransformer.getSchedulerTransformer());
    }
}
