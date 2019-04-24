package com.nish.android.playground.discovery;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.NamespaceList;
import org.simpleframework.xml.Root;

@Root(name = "d:multistatus")
@NamespaceList({
        @Namespace(prefix="d", reference="DAV:"),
        @Namespace(prefix="card", reference="urn:ietf:params:xml:ns:carddav"),
        @Namespace(prefix="cal", reference="urn:ietf:params:xml:ns:caldav")
})
public class DavMultiStatus {

    @Element(name = "response")
    DavResponse response;

    public DavResponse getResponse() {
        return response;
    }
}
