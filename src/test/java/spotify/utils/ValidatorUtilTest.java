package spotify.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class ValidatorUtilTest {

    @Test
    void returnsEmptyHashmapWhenANullIsPassedIn() {
        Assertions.assertNotNull(ValidatorUtil.optionsValueCheck(null));
    }

    @Test
    void returnsSamePassedInHashmap() {
        final Map<String, String> expectedObject = new HashMap<>();

        Assertions.assertEquals(expectedObject, ValidatorUtil.optionsValueCheck(expectedObject));
    }
}
