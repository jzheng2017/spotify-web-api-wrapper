package spotify.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Utility class for validating input values and ensuring safe defaults.
 */
public class ValidatorUtil {
    private final static Logger logger = LoggerFactory.getLogger(ValidatorUtil.class);

    /**
     * Validates the options map and returns an empty map if null is provided.
     * This prevents NullPointerExceptions when working with optional parameters.
     *
     * @param options the options map to validate, may be null
     * @return the original options map if not null, otherwise an empty HashMap
     */
    public static Map<String, String> optionsValueCheck(Map<String, String> options) {
        if (options == null) {
            logger.warn("A null value options has been passed in! An empty hashmap has now been assigned to it.");
            return new HashMap<>();
        }

        return options;
    }
}




