package com.nish.android.playground.discovery;

public class AddressbookConfig {

    public static final String BASE_URL = "https://www.googleapis.com/";

    public static final String ADDRESSBOOK_HOME_URL = "carddav/v1/principals/";

    public static final String ADDRESSBOOK_HOME_REQ = "<d:propfind xmlns:d=\"DAV:\" xmlns:cal=\"urn:ietf:params:xml:ns:caldav\" xmlns:card=\"urn:ietf:params:xml:ns:carddav\"><d:prop><card:addressbook-home-set /></d:prop></d:propfind>";
}
