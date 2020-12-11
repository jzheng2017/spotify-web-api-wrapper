package spotify.api.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

abstract class AbstractApiRetrofitTest {

    protected final String getJson(final String fileName) {
        try {
            return Files.readString(Path.of("src/test/fixtures/responses/" + fileName));
        } catch (IOException e) {
            return "Contents could not be read";
        }
    }
}
