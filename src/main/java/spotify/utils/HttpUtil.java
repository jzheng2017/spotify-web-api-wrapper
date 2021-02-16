package spotify.utils;

import org.slf4j.Logger;
import retrofit2.Call;
import retrofit2.Response;
import spotify.exceptions.HttpRequestFailedException;
import spotify.exceptions.SpotifyAuthorizationFailedException;
import spotify.models.authorization.AuthorizationCodeFlowTokenResponse;

import java.io.IOException;

/**
 * This class contains utility functions for use in making HTTP calls.
 *
 * @author Jiankai Zheng (jk.zheng@hotmail.com)
 * @since 1.5.0
 */

public class HttpUtil {

    /**
     * Executes an HTTP call to fetch an access and refresh token. If the response returns null,
     * throw a new exception and log the error. Otherwise, return the response body and log success.
     * 
     * @param httpCall  the authorization code flow service to use in the POST
     * @param logger    the logger object to log the appropriate responses to
     * @return  an object containing values like the access and refresh token
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
