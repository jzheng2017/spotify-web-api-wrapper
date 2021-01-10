package spotify.utils;


import com.google.gson.Gson;
import okhttp3.ResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import retrofit2.Response;
import spotify.api.enums.HttpStatusCode;
import spotify.exceptions.SpotifyActionFailedException;
import spotify.models.errors.SpotifyError;

/**
 * This class contains utility functions for checking responses received from HTTP calls.
 *
 * @author Jiankai Zheng (jk.zheng@hotmail.com)
 * @since 1.0.0
 */

public class ResponseChecker {
    private final static Logger logger = LoggerFactory.getLogger(ResponseChecker.class);

    /**
     * Checks the received response from an HTTP call for the correct status code and whether
     * the response body contains an error. If
     * 
     * @param <T>   the type of response object to check
     * @param response  the response body to be checked
     * @param expectedStatusCode    the status code that is expected to be received
     */
    public static <T> void throwIfRequestHasNotBeenFulfilledCorrectly(final Response<T> response, final HttpStatusCode expectedStatusCode) {
        checkHttpStatusCode(response, expectedStatusCode.toInt());
        checkErrorBody(response.errorBody());
    }

    /**
     * Checks the received HTTP status code with the expected HTTP status code and logs a
     * warning if they do not match.
     * 
     * @param <T> the type of reponse object to check 
     * @param response  the received status code
     * @param expectedStatusCode    the status code that is expected to be received
     */
    private static <T> void checkHttpStatusCode(Response<T> response, int expectedStatusCode) {
        final int actualHttpStatusCode = response.code();

        if (actualHttpStatusCode != expectedStatusCode) {
            logger.warn("HTTP request has not returned the expected status code. " +
                            "This may mean that the request has not been handled correctly. " +
                            "Expected status code: {}. Actual status code: {}.",
                    expectedStatusCode,
                    actualHttpStatusCode); // it should throw an exception but spotify returns inconsistent http status codes
        }
    }

    /**
     * Checks the response body of a HTTP call for an error. If no eror is found, returns from
     * the function. Otherwise, converts the error to a {@link SpotifyError} object if able or throws
     * an exception if not.
     * 
     * @param errorBody the HTTP response body to be checked for errors
     */
    private static void checkErrorBody(final ResponseBody errorBody) {
        if (errorBody == null) {
            return;
        }

        Gson gson = new Gson();

        logger.trace("Spotify API has returned an error body.");
        SpotifyError spotifyError = gson.fromJson(errorBody.charStream(), SpotifyError.class);

        if (spotifyError == null) {
            final String errorMessage = "HTTP request to Spotify's server has not been fulfilled correctly. Reason is unknown.";
            logger.error(errorMessage);
            logger.warn("Converting error body to SpotifyError object has failed for some reason.");
            throw new SpotifyActionFailedException(errorMessage);
        }

        final String message = spotifyError.getError().getMessage();
        final int statusCode = spotifyError.getError().getStatus();
        final boolean hasFailed = statusCode > 300;

        if (hasFailed) {
            logger.error(
                    "HTTP request to Spotify's server has not been fulfilled correctly. Spotify has returned status code {} with the message: \"{}\".",
                    statusCode,
                    message
            );
            throw new SpotifyActionFailedException(message);
        }
    }
}
