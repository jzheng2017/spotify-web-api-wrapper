package spotify.retrofit.services;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;
import spotify.api.enums.GrantType;
import spotify.models.authorization.AuthorizationCodeFlowTokenResponse;

public interface AuthorizationCodeFlowService {

    @POST("api/token")
    @FormUrlEncoded
    Call<AuthorizationCodeFlowTokenResponse> getAccessAndRefreshToken(@Header("Authorization") String basicAuth,
                                                                      @Field("code") String code,
                                                                      @Field("redirect_uri") String uri,
                                                                      @Field("grant_type") GrantType grantType);

    @POST("api/token")
    @FormUrlEncoded
    Call<AuthorizationCodeFlowTokenResponse> getAccessAndRefreshTokenPKCE(@Field("client_id") String clientId,
                                                                          @Field("grant_type") GrantType grantType,
                                                                          @Field("code") String code,
                                                                          @Field("redirect_uri") String redirectUri,
                                                                          @Field("code_verifier") String codeVerifier);

    @POST("api/token")
    @FormUrlEncoded
    Call<AuthorizationCodeFlowTokenResponse> refreshAccessToken(@Header("Authorization") String basicAuth,
                                                                @Field("refresh_token") String refreshToken,
                                                                @Field("grant_type") GrantType grantType);
}
