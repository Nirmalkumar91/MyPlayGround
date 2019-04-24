package com.nish.android.playground.discovery;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;

public class DavResponse {

    @Element(name = "href")
    @Namespace(prefix = "d")
    String href;

    @Element(name = "propstat")
    @Namespace(prefix = "d")
    DavPropStat propstat;

    public String getHref() {
        return href;
    }

    public DavPropStat getPropstat() {
        return propstat;
    }
}
