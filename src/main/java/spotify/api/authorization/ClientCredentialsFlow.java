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
import spotify.models.authorization.ClientCredentialsFlowTokenResponse;
import spotify.retrofit.services.ClientCredentialsFlowService;
import spotify.utils.LoggingUtil;

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
    private final Logger logger = LoggerFactory.getLogger(ClientCredentialsFlow.class);
    private final ClientCredentialsFlowService clientCredentialsFlowService;

    public ClientCredentialsFlow() {
        this.clientCredentialsFlowService = RetrofitHttpServiceFactory.getClientCredentialsFlowService();
    }

    public ClientCredentialsFlow(ClientCredentialsFlowService clientCredentialsFlowService) {
        this.clientCredentialsFlowService = clientCredentialsFlowService;
    }

    /**
     * Handles the Client Credentials Flow. This results in an access token.
     * <p>
     * See the Spotify Authorization Guide for information.
     *
     * @param clientId     id of the client
     * @param clientSecret secret of the client
     * @return an object containing the access token
     */
    public ClientCredentialsFlowTokenResponse getClientCredentialToken(String clientId, String clientSecret) {
        logger.trace("Encoding client id and secret to base 64.");
        final String base64EncodedBasicAuth = Credentials.basic(clientId, clientSecret);

        logger.trace("Constructing HTTP call to fetch an access token.");
        final Call<ClientCredentialsFlowTokenResponse> httpCall = clientCredentialsFlowService.getToken(base64EncodedBasicAuth, GrantType.CLIENT_CREDENTIALS);

        try {
            logger.info("Executing HTTP call to fetch an access token.");
            LoggingUtil.logHttpCall(logger, httpCall);
            final Response<ClientCredentialsFlowTokenResponse> response = httpCall.execute();

            if (response.body() == null) {
                logger.error("Spotify has returned empty response body. This may mean the given credentials are invalid.");
                throw new SpotifyAuthorizationFailedException("Authorizing with the given credentials has failed!");
            }

            logger.info("Access token has been successfully fetched.");
            return response.body();
        } catch (IOException e) {
            logger.error("HTTP request to fetch an access token has failed.");
            throw new HttpRequestFailedException(e.getMessage());
        }
    }
}
