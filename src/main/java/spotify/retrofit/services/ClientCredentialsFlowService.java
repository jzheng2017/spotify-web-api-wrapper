package spotify.retrofit.services;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;
import spotify.api.enums.GrantType;
import spotify.models.authorization.ClientCredentialsFlowTokenResponse;

/**
 * Retrofit service interface for Spotify Client Credentials Flow endpoints.
 * Provides a method for obtaining an access token using client credentials,
 * which is suitable for server-to-server authentication without user authorization.
 */
public interface ClientCredentialsFlowService {

    /**
     * Obtains an access token using the Client Credentials Flow.
     * This flow is used when the application needs to access Spotify resources
     * that do not require user authorization.
     *
     * @param basicAuth the Base64-encoded client credentials in the format "Basic {base64(client_id:client_secret)}"
     * @param grantType the grant type, should be {@link GrantType#CLIENT_CREDENTIALS}
     * @return a Call object containing the access token
     */
    @POST("api/token")
    @FormUrlEncoded
    Call<ClientCredentialsFlowTokenResponse> getToken(@Header("Authorization") String basicAuth, @Field("grant_type") GrantType grantType);
}