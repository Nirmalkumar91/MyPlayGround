package com.nish.android.playground.common;

import android.content.SharedPreferences;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SharedPrefUtil {
    private final static String PREF_OAUTH_CODE = "pref_oauth_code";
    private final static String PREF_OAUTH_TOKEN = "pref_oauth_token";
    private final static String PREF_USER_EMAIL = "pref_user_email";
    private final static String PREF_ADDRESSBOOK_URL = "pref_addressbook_url";

    private SharedPreferences sharedPreferences;

    @Inject
    public SharedPrefUtil(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public String getOAuthCode() {
        return getString(PREF_OAUTH_CODE);
    }

    public void setOAuthCode(String code) {
        putString(PREF_OAUTH_CODE, code);
    }

    public void clearAuthCode() {
        putString(PREF_OAUTH_CODE, null);
    }

    public void setOAuthToken(String token) {
        putString(PREF_OAUTH_TOKEN, token);
    }

    public void clearAuthToken() {
        putString(PREF_OAUTH_TOKEN, null);
    }

    public String getOAuthToken() {
        return getString(PREF_OAUTH_TOKEN);
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
