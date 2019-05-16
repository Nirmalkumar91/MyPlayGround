package com.nish.android.playground.addressbook;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.StringDef;

public class Properties {

    @Retention(RetentionPolicy.SOURCE)
    @StringDef({RESOURCE_TYPE, DISPLAY_NAME, ADDRESSBOOK_HOME_SET})

    public @interface DavProperty { }

    public static final String RESOURCE_TYPE = "<d:resourcetype/>";
    public static final String DISPLAY_NAME = "<d:displayname/>";
    public static final String ADDRESSBOOK_HOME_SET = "<card:addressbook-home-set />";
}