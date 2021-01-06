package spotify.utils;

import org.slf4j.Logger;
import retrofit2.Call;

/**
 * 
 */
public class LoggingUtil {

    /**
     * 
     * @param <T>
     * @param logger
     * @param httpCall
     */
    public static <T> void logHttpCall(final Logger logger, final Call<T> httpCall) {
        logger.debug("{} / {}", httpCall.request().method(), httpCall.request().url());
    }
}
