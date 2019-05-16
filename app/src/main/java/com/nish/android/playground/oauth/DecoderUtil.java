package com.nish.android.playground.oauth;

import android.util.Base64;
import android.util.Log;

import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;

import javax.inject.Inject;

public class DecoderUtil {

    private Gson gson;

    @Inject
    public DecoderUtil(Gson gson) {
        this.gson = gson;
    }

    public UserProfile decodeIdToken(String token) throws UnsupportedEncodingException {
        String[] data = token.split("\\.");
        //String header = getJson(data[0]);
        String body = getJson(data[1]);
        //String signature = getJson(data[2]);

        return gson.fromJson(body, UserProfile.class);
    }

    private static String getJson(String strEncoded) throws UnsupportedEncodingException {
        byte[] decodedBytes = Base64.decode(strEncoded, Base64.URL_SAFE);
        return new String(decodedBytes, "UTF-8");
    }
}
