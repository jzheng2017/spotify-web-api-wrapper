package spotify.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility class that validates the values.
 */
public class ValidatorUtil {
    private final static Logger logger = LoggerFactory.getLogger(ValidatorUtil.class);

    /**
     * Checks if the passed in value is empty, if it is it will set it to null.
     *
     * @param value the spotify value
     * @return market value
     */
    public static String emptyValueCheck(String value) {
        // this is done because retrofit ignores null values
        // when an empty value is passed to spotify it will give an error saying the value does not exist
        if (value.isEmpty()) {
            logger.warn("An empty value has been passed in! The value will now be set to NULL.");
            return null;
        }

        return value;
    }

    /**
     * Validate filters.
     *
     * @param limit  the number of items to be returned
     * @param offset the offset, 0 means starting from the first item
     */
    public static void validateFiltersAndThrowIfInvalid(int limit, int offset) {
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
