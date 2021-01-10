package spotify.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * This class contains utility functions for validating.
 * @author Jiankai Zheng (jk.zheng@hotmail.com)
 * @since 1.0.0
 */

public class ValidatorUtil {
    private final static Logger logger = LoggerFactory.getLogger(ValidatorUtil.class);

    /**
     * To be honest, I am not really sure what this does...
     * 
     * @param options
     * @return
     */
    public static Map<String, String> optionsValueCheck(Map<String, String> options) {
        if (options == null) {
            logger.warn("A null value options has been passed in! An empty hashmap has now been assigned to it.");
            return new HashMap<>();
        }

        return options;
    }
}
