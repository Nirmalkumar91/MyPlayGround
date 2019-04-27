package com.nish.android.playground.discovery;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.convert.Convert;

public class DavResourceType {

    @Element(name="collection", required = false)
    @Namespace(prefix = "d")
    @Convert(ResourceTypeConverter.class)
    String collection;

    @Element(name="principal", required = false)
    @Namespace(prefix = "d")
    @Convert(ResourceTypeConverter.class)
    String principal;

    @Element(name="addressbook", required = false)
    @Namespace(prefix = "card")
    @Convert(ResourceTypeConverter.class)
    String addressbook;

    public String getCollection() {
        return collection;
    }

    public String getPrincipal() {
        return principal;
    }

    public String getAddressbook() {
        return addressbook;
    }
}
