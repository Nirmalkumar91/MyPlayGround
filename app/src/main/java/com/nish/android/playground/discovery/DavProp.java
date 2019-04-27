package com.nish.android.playground.discovery;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.convert.Convert;

public class DavProp {

    @Element(name = "addressbook-home-set", required = false)
    @Namespace(prefix = "card")
    DavAddressbookHome addressbookHome;

    @Element(name = "displayname", required = false)
    @Namespace(prefix = "d")
    String displayName;

    @Element(name = "resourcetype", required = false)
    @Namespace(prefix = "d")
    DavResourceType resourceType;

    public DavAddressbookHome getAddressbookHome() {
        return addressbookHome;
    }

    public String getDisplayName() {
        return displayName;
    }

    public DavResourceType getResourceType() {
        return resourceType;
    }
}
