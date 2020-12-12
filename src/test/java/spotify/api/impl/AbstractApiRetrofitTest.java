package spotify.api.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

abstract class AbstractApiRetrofitTest {
    protected final String tokenPrefix = "Bearer ";
    protected final String fakeAccessToken = "69";
    protected final String fakeAccessTokenWithBearer = tokenPrefix + fakeAccessToken;
    protected final String fakeUrl = "https://jiankai.nl";
    protected final Map<String, String> fakeOptionalParameters = new HashMap<>();


    protected final String getJson(final String fileName) {
        try {
            return Files.readString(Path.of("src/test/fixtures/responses/" + fileName));
        } catch (IOException e) {
            return "Contents could not be read";
        }
    }
}
