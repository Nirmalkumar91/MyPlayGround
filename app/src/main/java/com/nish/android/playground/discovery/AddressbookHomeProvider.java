package com.nish.android.playground.discovery;

import com.nish.android.playground.common.SharedPrefUtil;
import com.nish.android.playground.network.SchedulerTransformer;
import com.nish.android.playground.repository.NishRepository;

import javax.inject.Inject;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class AddressbookHomeProvider {

    private AddressBookHomeService addressBookHomeService;
    private SharedPrefUtil sharedPrefUtil;
    private AddressbookConfig addressbookConfig;
    private SchedulerTransformer schedulerTransformer;
    private NishRepository nishRepository;

    @Inject
    public AddressbookHomeProvider(AddressBookHomeService addressBookHomeService, SharedPrefUtil sharedPrefUtil,
                                   AddressbookConfig addressbookConfig, SchedulerTransformer schedulerTransformer,
                                   NishRepository nishRepository) {
        this.addressBookHomeService = addressBookHomeService;
        this.sharedPrefUtil = sharedPrefUtil;
        this.addressbookConfig = addressbookConfig;
        this.schedulerTransformer = schedulerTransformer;
        this.nishRepository = nishRepository;
    }

    public Observable<DavMultiStatus> getAddressbookHome() {
        String email = sharedPrefUtil.getUserEmail();
        RequestBody body = RequestBody.create(MediaType.parse("application/xml"),
                addressbookConfig.getRequestBody(Properties.RESOURCE_TYPE, Properties.ADDRESSBOOK_HOME_SET));
        return addressBookHomeService.getAddressbookHomeSet(AddressbookConfig.getAddressbookHomeSetPath(email),
                        body, "Bearer " + nishRepository.getAccessToken(email))
                .compose(schedulerTransformer.getSchedulerTransformer());
    }

    public Observable<DavMultiStatus> getAddressbook() {
        String email = sharedPrefUtil.getUserEmail();
        RequestBody body =
                RequestBody.create(MediaType.parse("application/xml"),
                        addressbookConfig.getRequestBody(Properties.RESOURCE_TYPE, Properties.DISPLAY_NAME));
        return addressBookHomeService
                .getAddressbook(addressbookConfig.getAddressbookUrl(),
                        body, "Bearer " + nishRepository.getAccessToken(email), "1")
                .compose(schedulerTransformer.getSchedulerTransformer());
    }
}
