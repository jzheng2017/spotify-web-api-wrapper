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

public class AuthorizationRequestTokens {
    private final Retrofit httpClient;

    public AuthorizationRequestTokens() {
        this.httpClient = RetrofitClientFactory.getRetrofitClient(ApiUrl.ACCOUNTS_URL_HTTPS);
    }

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
