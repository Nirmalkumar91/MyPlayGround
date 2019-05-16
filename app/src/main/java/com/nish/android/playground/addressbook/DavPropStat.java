package com.nish.android.playground.addressbook;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;

public class DavPropStat {

    @Element(name = "status")
    @Namespace(prefix = "d")
    String status;

    @Element(name = "prop")
    @Namespace(prefix = "d")
    DavProp prop;

    public String getStatus() {
        return status;
    }

    public DavProp getProp() {
        return prop;
    }
}
