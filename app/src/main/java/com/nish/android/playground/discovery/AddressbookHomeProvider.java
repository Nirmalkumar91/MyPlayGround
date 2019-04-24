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
    private SchedulerTransformer schedulerTransformer;

    @Inject
    public AddressbookHomeProvider(AddressBookHomeService addressBookHomeService, SharedPrefUtil sharedPrefUtil, SchedulerTransformer schedulerTransformer) {
        this.addressBookHomeService = addressBookHomeService;
        this.sharedPrefUtil = sharedPrefUtil;
        this.schedulerTransformer = schedulerTransformer;
    }

    public Observable<DavMultiStatus> getAddressbookHome() {
        RequestBody body =
                RequestBody.create(MediaType.parse("application/xml"), AddressbookConfig.ADDRESSBOOK_HOME_REQ);
        return addressBookHomeService
                .getAddressbookHomeSet(AddressbookConfig.ADDRESSBOOK_HOME_URL + sharedPrefUtil.getUserEmail(),
                        body, "Bearer " + sharedPrefUtil.getOAuthToken(), "application/xml")
                .compose(schedulerTransformer.getSchedulerTransformer());
    }
}
