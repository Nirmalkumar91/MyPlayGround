package com.nish.android.playground.addressbook;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;

public class DavResponse {

    @Element(name = "href", required = false)
    @Namespace(prefix = "d")
    String href;

    @Element(name = "propstat", required = false)
    @Namespace(prefix = "d")
    DavPropStat propstat;

    public String getHref() {
        return href;
    }

    public DavPropStat getPropstat() {
        return propstat;
    }
}
