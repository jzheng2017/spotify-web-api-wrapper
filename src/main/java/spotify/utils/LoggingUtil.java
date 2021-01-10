package spotify.utils;

import org.slf4j.Logger;
import retrofit2.Call;

/**
 * This class contains utility functions for use in logging.
 *
 * @author Jiankai Zheng (jk.zheng@hotmail.com)
 * @since 1.5.0
 */

public class LoggingUtil {

    /**
     * Logs HTTP calls to the specified logger object.
     * 
     * @param <T>       the type of HTTP call being made
     * @param logger    the logger object to log the appropriate responses to
     * @param httpCall  the authorization code flow service to use in the POST
     */
    public static <T> void logHttpCall(final Logger logger, final Call<T> httpCall) {
        logger.debug("{} / {}", httpCall.request().method(), httpCall.request().url());
    }
}
