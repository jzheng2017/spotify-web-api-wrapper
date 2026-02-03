package spotify.utils;

import org.slf4j.Logger;
import retrofit2.Call;

/**
 * Utility class for logging HTTP requests and other application events.
 */



public class LoggingUtil {

    /**
     * Logs the HTTP method and URL of a Retrofit call at debug level.
     *
     * @param logger the logger instance to use for logging
     * @param httpCall the Retrofit call to log information about
     * @param <T> the type of the response body
     */
    public static <T> void logHttpCall(final Logger logger, final Call<T> httpCall) {
        logger.debug("{} / {}", httpCall.request().method(), httpCall.request().url());
    }
}



