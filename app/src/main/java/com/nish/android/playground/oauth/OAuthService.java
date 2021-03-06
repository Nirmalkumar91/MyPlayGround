/**
 * <ul>
 * <li>OAuthServerIntf</li>
 * <li>com.renaultnissan.acms.platform.oauth.githubsample.retrofit</li>
 * <li>04/01/2017</li>
 * <p>
 * <li>======================================================</li>
 * <p>
 * <li>Projet : Mathias Seguy Project</li>
 * <li>Produit par MSE.</li>
 * <p>
 * /**
 * <ul>
 * Android Tutorial, An <strong>Android2EE</strong>'s project.</br>
 * Produced by <strong>Dr. Mathias SEGUY</strong>.</br>
 * Delivered by <strong>http://android2ee.com/</strong></br>
 * Belongs to <strong>Mathias Seguy</strong></br>
 * ***************************************************************************************************************</br>
 * This code is free for any usage but can't be distribute.</br>
 * The distribution is reserved to the site <strong>http://android2ee.com</strong>.</br>
 * The intelectual property belongs to <strong>Mathias Seguy</strong>.</br>
 * <em>http://mathias-seguy.developpez.com/</em></br> </br>
 * <p>
 * *****************************************************************************************************************</br>
 * Ce code est libre de toute utilisation mais n'est pas distribuable.</br>
 * Sa distribution est reservée au site <strong>http://android2ee.com</strong>.</br>
 * Sa propriété intellectuelle appartient à <strong>Mathias Seguy</strong>.</br>
 * <em>http://mathias-seguy.developpez.com/</em></br> </br>
 * *****************************************************************************************************************</br>
 */
package com.nish.android.playground.oauth;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface OAuthService {

    @FormUrlEncoded
    @POST("oauth2/v4/token")
    Observable<OAuthToken> requestToken(
            @Field("code") String code,
            @Field("client_id") String client_id,
            @Field("redirect_uri") String redirect_uri,
            @Field("grant_type") String grant_type);

    @FormUrlEncoded
    @POST("oauth2/v4/token")
    Observable<OAuthToken> refreshToken(
            @Field("refresh_token") String refresh_token,
            @Field("client_id") String client_id,
            @Field("grant_type") String grant_type);

}
