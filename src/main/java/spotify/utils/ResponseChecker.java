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
 * Utility class for validating HTTP responses from the Spotify API.
 * Provides methods to check status codes and handle error responses.
 */
public class ResponseChecker {
    private final static Logger logger = LoggerFactory.getLogger(ResponseChecker.class);

    /**
     * Validates that an HTTP response meets the expected criteria.
     * Checks both the status code and error body of the response.
     *
     * @param response the Retrofit response to validate
     * @param expectedStatusCode the HTTP status code that indicates a successful request
     * @param <T> the type of the response body
     * @throws SpotifyActionFailedException if the response contains an error body indicating failure
     */
    public static <T> void throwIfRequestHasNotBeenFulfilledCorrectly(final Response<T> response, final HttpStatusCode expectedStatusCode) {
        checkHttpStatusCode(response, expectedStatusCode.toInt());
        checkErrorBody(response.errorBody());
    }

    /**
     * Compares the actual HTTP status code against the expected status code.
     * Logs a warning if they do not match, but does not throw an exception
     * due to inconsistent status codes returned by the Spotify API.
     *
     * @param response the Retrofit response to check
     * @param expectedStatusCode the expected HTTP status code
     * @param <T> the type of the response body
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
     * Parses and validates the error body of a response.
     * If an error is present with a status code greater than 300, throws an exception.
     *
     * @param errorBody the error body from the response, may be null
     * @throws SpotifyActionFailedException if the error body indicates a failed request
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



