package spotify.retrofit.services;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;
import spotify.api.enums.GrantType;
import spotify.models.authorization.AuthorizationCodeFlowTokenResponse;

/**
 * Retrofit service interface for Spotify Authorization Code Flow endpoints.
 * Provides methods for obtaining and refreshing access tokens using the Authorization Code Flow
 * and Authorization Code Flow with PKCE.
 */
public interface AuthorizationCodeFlowService {


    /**
     * Exchanges an authorization code for access and refresh tokens using the standard Authorization Code Flow.
     *
     * @param basicAuth the Base64-encoded client credentials in the format "Basic {base64(client_id:client_secret)}"
     * @param code the authorization code received from the authorization request
     * @param uri the redirect URI that was used in the authorization request
     * @param grantType the grant type, should be {@link GrantType#AUTHORIZATION_CODE}
     * @return a Call object containing the access token and refresh token
     */
    @POST("api/token")
    @FormUrlEncoded
    Call<AuthorizationCodeFlowTokenResponse> getAccessAndRefreshToken(@Header("Authorization") String basicAuth,
                                                                      @Field("code") String code,
                                                                      @Field("redirect_uri") String uri,
                                                                      @Field("grant_type") GrantType grantType);


    /**
     * Exchanges an authorization code for access and refresh tokens using the Authorization Code Flow with PKCE.
     * This method is used for public clients that cannot securely store a client secret.
     *
     * @param clientId the client ID of the application
     * @param grantType the grant type, should be {@link GrantType#AUTHORIZATION_CODE}
     * @param code the authorization code received from the authorization request
     * @param redirectUri the redirect URI that was used in the authorization request
     * @param codeVerifier the code verifier that was used to generate the code challenge
     * @return a Call object containing the access token and refresh token
     */
    @POST("api/token")
    @FormUrlEncoded
    Call<AuthorizationCodeFlowTokenResponse> getAccessAndRefreshTokenPKCE(@Field("client_id") String clientId,
                                                                          @Field("grant_type") GrantType grantType,
                                                                          @Field("code") String code,
                                                                          @Field("redirect_uri") String redirectUri,
                                                                          @Field("code_verifier") String codeVerifier);



    /**
     * Refreshes an access token using a refresh token.
     *
     * @param basicAuth the Base64-encoded client credentials in the format "Basic {base64(client_id:client_secret)}"
     * @param refreshToken the refresh token obtained from a previous token request
     * @param grantType the grant type, should be {@link GrantType#REFRESH_TOKEN}
     * @return a Call object containing the new access token and possibly a new refresh token
     */
    @POST("api/token")
    @FormUrlEncoded
    Call<AuthorizationCodeFlowTokenResponse> refreshAccessToken(@Header("Authorization") String basicAuth,
                                                                @Field("refresh_token") String refreshToken,
                                                                @Field("grant_type") GrantType grantType);
}