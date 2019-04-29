package com.nish.android.playground.oauth;

import com.google.gson.annotations.SerializedName;

public class UserProfile {

    @SerializedName("name")
    private String name;

    @SerializedName("given_name")
    private String givenName;

    @SerializedName("family_name")
    private String familyName;

    @SerializedName("email")
    private String email;

    @SerializedName("picture")
    private String picture;

    @SerializedName("exp")
    private String expiryTime;

    public String getName() {
        return name;
    }

    public String getGivenName() {
        return givenName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public String getEmail() {
        return email;
    }

    public String getPicture() {
        return picture;
    }

    public String getExpiryTime() {
        return expiryTime;
    }
}
