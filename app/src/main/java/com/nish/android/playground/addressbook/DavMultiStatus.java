package com.nish.android.playground.addressbook;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.NamespaceList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "d:multistatus")
@NamespaceList({
        @Namespace(prefix="d", reference="DAV:"),
        @Namespace(prefix="card", reference="urn:ietf:params:xml:ns:carddav"),
        @Namespace(prefix="cal", reference="urn:ietf:params:xml:ns:caldav")
})
public class DavMultiStatus {

    @ElementList(entry = "response", inline = true, required = false)
    @Namespace(prefix = "d")
    List<DavResponse> responseList;

    public List<DavResponse> getResponses() {
        return responseList;
    }
}
