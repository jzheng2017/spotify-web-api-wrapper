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
import spotify.models.authorization.AuthorizationCodeFlowTokenResponse;
import spotify.retrofit.services.AuthorizationCodeFlowService;

import java.io.IOException;

/**
 * This class takes care of the second step in the Authorization Code Flow.
 * Executing this step should result in an access and refresh token.
 * <p>
 * For more information see: {@link <a href="https://developer.spotify.com/documentation/general/guides/authorization-guide/#authorization-code-flow">Authorization Code Flow</a>}
 *
 * @author Jiankai Zheng
 */
public class AuthorizationRequestTokens {
    private final Retrofit httpClient;

    public AuthorizationRequestTokens() {
        this.httpClient = RetrofitClientFactory.getRetrofitClient(ApiUrl.ACCOUNTS_URL_HTTPS);
    }

    /**
     *
     * @param clientId the id of the client
     * @param clientSecret the secret of the client
     * @param code the authorization code returned from the initial request to the Account /authorize endpoint
     * @param redirectUri the redirect uri supplied when requesting the authorization code
     * @return an object containing values like the access and refresh token
     */
    public AuthorizationCodeFlowTokenResponse getAccessAndRefreshToken(String clientId, String clientSecret, String code, String redirectUri) {
        final AuthorizationCodeFlowService authorizationCodeFlowService = httpClient.create(AuthorizationCodeFlowService.class);
        final String base64EncodedBasicAuth = Credentials.basic(clientId, clientSecret);

        final Call<AuthorizationCodeFlowTokenResponse> httpCall = authorizationCodeFlowService
                .getAccessAndRefreshToken(
                        base64EncodedBasicAuth,
                        code,
                        redirectUri,
                        GrantType.AUTHORIZATION_CODE);

        try {
            final Response<AuthorizationCodeFlowTokenResponse> response = httpCall.execute();

            if (response.body() == null) {
                throw new SpotifyAuthorizationFailedException("Authorizing with the given credentials has failed!");
            }

            return response.body();
        } catch (IOException e) {
            throw new HttpRequestFailedException(e.getMessage());
        }
    }
}
