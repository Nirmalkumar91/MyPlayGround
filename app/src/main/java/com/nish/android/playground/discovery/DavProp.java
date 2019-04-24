package com.nish.android.playground.discovery;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;

public class DavProp {

    @Element(name = "addressbook-home-set")
    @Namespace(prefix = "card")
    DavAddressbookHome addressbookHome;

    public DavAddressbookHome getAddressbookHome() {
        return addressbookHome;
    }
}
