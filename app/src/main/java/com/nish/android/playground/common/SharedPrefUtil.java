package com.nish.android.playground.common;

import android.content.SharedPreferences;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SharedPrefUtil {
    private final static String PREF_USER_EMAIL = "pref_user_email";
    private final static String PREF_ADDRESSBOOK_URL = "pref_addressbook_url";

    private SharedPreferences sharedPreferences;

    @Inject
    public SharedPrefUtil(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public String getUserEmail() {
        return getString(PREF_USER_EMAIL);
    }

    public void setUserEmail(String email) {
        putString(PREF_USER_EMAIL, email);
    }

    public void clearEmail() {
        putString(PREF_USER_EMAIL, null);
    }

    public String getAddressbookUrl() {
        return getString(PREF_ADDRESSBOOK_URL);
    }

    public void setAddressbookUrl(String path) {
        putString(PREF_ADDRESSBOOK_URL, path);
    }

    public void clearAddressbookUrl() {
        putString(PREF_ADDRESSBOOK_URL, null);
    }

    private void putString(String key, String value) {
        SharedPreferences.Editor prefEditor = sharedPreferences.edit();
        prefEditor.putString(key, value);
        prefEditor.apply();
    }

    private String getString(String key) {
        return sharedPreferences.getString(key, null);
    }

}
