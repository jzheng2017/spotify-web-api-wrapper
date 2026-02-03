package spotify.utils;

import org.slf4j.Logger;
import retrofit2.Call;
import retrofit2.Response;
import spotify.exceptions.HttpRequestFailedException;
import spotify.exceptions.SpotifyAuthorizationFailedException;
import spotify.models.authorization.AuthorizationCodeFlowTokenResponse;

import java.io.IOException;

/**
 * Utility class for executing HTTP requests related to Spotify API operations.
 */
public class HttpUtil {

    /**
     * Executes an HTTP call to retrieve access and refresh tokens using the Authorization Code Flow.
     *
     * @param httpCall the Retrofit call to execute for fetching tokens
     * @param logger the logger instance to use for logging request information
     * @return the response containing the access token and refresh token
     * @throws SpotifyAuthorizationFailedException if the response body is empty, indicating invalid credentials
     * @throws HttpRequestFailedException if the HTTP request fails due to an IO error
     */
    public static AuthorizationCodeFlowTokenResponse executeAuthorizationHttpCall(Call<AuthorizationCodeFlowTokenResponse> httpCall, Logger logger) {
        try {
            logger.info("Executing HTTP call to fetch an access and refresh token.");
            LoggingUtil.logHttpCall(logger, httpCall);
            final Response<AuthorizationCodeFlowTokenResponse> response = httpCall.execute();

            if (response.body() == null) {
                logger.error("Spotify has returned empty response body. This may mean the given credentials are invalid.");
                throw new SpotifyAuthorizationFailedException("Retrieving an access token and refresh token with the given credentials has failed!");
            }

            logger.info("Access and refresh token have been successfully fetched.");
            return response.body();
        } catch (IOException e) {
            logger.error("HTTP request to fetch an access and refresh token has failed.");
            throw new HttpRequestFailedException(e.getMessage());
        }
    }
}

