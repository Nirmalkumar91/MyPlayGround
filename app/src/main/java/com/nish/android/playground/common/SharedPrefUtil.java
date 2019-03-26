package com.nish.android.playground.common;

import android.content.SharedPreferences;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SharedPrefUtil {
    private final String PREF_OAUTH_TOKEN = "pref_oauth_token";
    private SharedPreferences sharedPreferences;

    @Inject
    public SharedPrefUtil(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public String getOAuthToken() {
        return getString(PREF_OAUTH_TOKEN);
    }

    public void setOAuthToken(String token) {
        putString(PREF_OAUTH_TOKEN, token);
    }

    public void clearAuthToken() {
        putString(PREF_OAUTH_TOKEN, null);
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
