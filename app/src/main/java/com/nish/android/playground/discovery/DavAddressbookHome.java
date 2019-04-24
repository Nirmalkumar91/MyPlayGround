package com.nish.android.playground.discovery;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;

public class DavAddressbookHome {

    @Element(name = "href")
    @Namespace(prefix = "d")
    String href;

    public String getHref() {
        return href;
    }
}
