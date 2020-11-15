package spotify.api.authorization;

import okhttp3.Credentials;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import spotify.api.enums.GrantType;
import spotify.config.ApiUrl;
import spotify.exceptions.HttpRequestFailedException;
import spotify.exceptions.SpotifyAuthorizationFailedException;
import spotify.factories.RetrofitClientFactory;
import spotify.models.authorization.ClientCredentialsFlowTokenResponse;
import spotify.retrofit.services.ClientCredentialsFlowService;

import java.io.IOException;

/**
 * This class is used to get an access token through the Client Credentials Flow.
 * Executing this step will result in an access token.
 * This access token can only be used to access endpoints with publicly available information,
 * so therefore information about the user can not be accessed through this access token.
 * <p>
 * Authorization Code Flow is recommended to access private information, such as user information.
 *
 * @author Jiankai Zheng
 * @see AuthorizationCodeFlow
 */
public class ClientCredentialsFlow {
    private final Retrofit httpClient;
    private ClientCredentialsFlowTokenResponse clientCredentialsFlowTokenResponse;

    public ClientCredentialsFlow() {
        httpClient = RetrofitClientFactory.getRetrofitClient(ApiUrl.ACCOUNTS_URL_HTTPS);
    }

    /**
     * Handles the Client Credentials Flow. This results in an access token.
     * <p>
     * See the Spotify Authorization Guide for information.
     *
     * @param clientId     id of the client
     * @param clientSecret secret of the client
     */
    public void doClientCredentialsFlow(String clientId, String clientSecret) {
        final ClientCredentialsFlowService clientCredentialsFlowService = httpClient.create(ClientCredentialsFlowService.class);
        final String base64EncodedBasicAuth = Credentials.basic(clientId, clientSecret);

        final Call<ClientCredentialsFlowTokenResponse> httpCall = clientCredentialsFlowService.getToken(base64EncodedBasicAuth, GrantType.CLIENT_CREDENTIALS);

        try {
            final Response<ClientCredentialsFlowTokenResponse> response = httpCall.execute();

            if (response.body() == null) {
                throw new SpotifyAuthorizationFailedException("Authorizing with the given credentials has failed!");
            }

            this.clientCredentialsFlowTokenResponse = response.body();
        } catch (IOException e) {
            throw new HttpRequestFailedException(e.getMessage());
        }
    }

    /**
     * Gets an object containing values related to the access token.
     *
     * @return an object containing values like the access token
     */
    public ClientCredentialsFlowTokenResponse getClientCredentialsFlowToken() {
        return clientCredentialsFlowTokenResponse;
    }
}
