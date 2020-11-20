package spotify.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ValidatorUtil {
    private final static Logger logger = LoggerFactory.getLogger(ValidatorUtil.class);

    public static String marketEmptyCheck(String market) {
        // this is done because retrofit ignores null values
        // when an empty market value is passed to spotify it will give an error saying the market does not exist
        if (market.isEmpty()) {
            logger.warn("An empty market value has been passed in! The market value has now been set to NULL.");
            return null;
        }

        return market;
    }

    public static void validateFilters(int limit, int offset) {
        logger.trace("Validating passed in values");
        logger.debug(String.format("Passed in values: limit = %d, offset = %d", limit, offset));
        if (limit <= 0) {
            throw new IllegalArgumentException(String.format("Limit must be at least 1! Current passed in limit value: %d", limit));
        }

        if (offset < 0) {
            throw new IllegalArgumentException(String.format("Offset must be at least 0! Current passed in offset value: %d", offset));
        }
    }
}
