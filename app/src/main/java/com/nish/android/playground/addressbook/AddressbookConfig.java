package com.nish.android.playground.addressbook;

import com.nish.android.playground.common.SharedPrefUtil;

import javax.inject.Inject;

public class AddressbookConfig {

    public static final String BASE_URL = "https://www.googleapis.com/";

    public static final String ADDRESSBOOK_HOME_URL = "carddav/v1/principals/{user-id}/";

    public static final String PROP_REQ_OPEN = "<d:propfind xmlns:d=\"DAV:\" xmlns:cal=\"urn:ietf:params:xml:ns:caldav\" xmlns:card=\"urn:ietf:params:xml:ns:carddav\"><d:prop>";

    public static final String PROP_REQ_CLOSE = "</d:prop></d:propfind>";

    private SharedPrefUtil sharedPrefUtil;
    @Inject
    public AddressbookConfig(SharedPrefUtil sharedPrefUtil) {

        this.sharedPrefUtil = sharedPrefUtil;
    }

    public static String getAddressbookHomeSetPath(String userEmail) {
        return ADDRESSBOOK_HOME_URL.replace("{user-id}", userEmail);
    }

    public String getRequestBody(@Properties.DavProperty String... properties) {
        StringBuilder builder = new StringBuilder(PROP_REQ_OPEN);
        for (String prop : properties) {
            builder.append(prop);
        }
        builder.append(PROP_REQ_CLOSE);

        return builder.toString();
    }

    public String getAddressbookUrl() {
        return sharedPrefUtil.getAddressbookUrl();
    }
}
