package spotify.api.authorization;

import okhttp3.Credentials;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import retrofit2.Call;
import retrofit2.Response;
import spotify.api.enums.GrantType;
import spotify.exceptions.HttpRequestFailedException;
import spotify.exceptions.SpotifyAuthorizationFailedException;
import spotify.factories.RetrofitHttpServiceFactory;
import spotify.models.authorization.AuthorizationCodeFlowTokenResponse;
import spotify.retrofit.services.AuthorizationCodeFlowService;
import spotify.utils.LoggingUtil;

import java.io.IOException;

/**
 * This class is used to refresh the access token.
 * <p>
 * For more information see: @see <a href="https://developer.spotify.com/documentation/general/guides/authorization-guide/#authorization-code-flow">Authorization Code Flow</a>
 *
 * @author Jiankai Zheng
 */
public class AuthorizationRefreshToken {
    private final Logger logger = LoggerFactory.getLogger(AuthorizationRefreshToken.class);
    private final AuthorizationCodeFlowService authorizationCodeFlowService;

    public AuthorizationRefreshToken() {
        this.authorizationCodeFlowService = RetrofitHttpServiceFactory.getAuthorizationCodeFlowService();
    }

    public AuthorizationRefreshToken(AuthorizationCodeFlowService authorizationCodeFlowService) {
        this.authorizationCodeFlowService = authorizationCodeFlowService;
    }

    /**
     * Makes a request to the Spotify API to refresh the access token.
     *
     * @param clientId     the client id
     * @param clientSecret the client secret
     * @param refreshToken the refresh token
     * @return Object containing the new access and refresh token
     */
    public AuthorizationCodeFlowTokenResponse refreshAccessToken(String clientId, String clientSecret, String refreshToken) {
        logger.trace("Encoding client id and secret to base 64.");
        final String base64EncodedBasicAuth = Credentials.basic(clientId, clientSecret);

        logger.trace("Constructing HTTP call to refresh the access token.");
        final Call<AuthorizationCodeFlowTokenResponse> httpCall = authorizationCodeFlowService
                .refreshAccessToken(
                        base64EncodedBasicAuth,
                        refreshToken,
                        GrantType.REFRESH_TOKEN);

        try {
            logger.info("Executing HTTP call to refresh the access token.");
            LoggingUtil.logHttpCall(logger, httpCall);
            final Response<AuthorizationCodeFlowTokenResponse> response = httpCall.execute();

            if (response.body() == null) {
                logger.error("Spotify has returned empty response body. This may mean the given credentials are invalid.");
                throw new SpotifyAuthorizationFailedException("Refreshing the access token with the given credentials has failed!");
            }

            logger.info("Access token has been successfully refreshed.");
            return response.body();
        } catch (IOException e) {
            logger.error("HTTP request to refresh the access token has failed.");
            throw new HttpRequestFailedException(e.getMessage());
        }
    }
}
