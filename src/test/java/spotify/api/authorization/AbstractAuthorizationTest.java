package spotify.api.authorization;

import okhttp3.Credentials;

abstract class AbstractAuthorizationTest {
    protected final String fakeClientId = "jiankai";
    protected final String fakeClientSecret = "zheng";
    protected final String fakeBasicAuth = Credentials.basic(fakeClientId, fakeClientSecret);
    protected final String fakeUrl = "https://jiankai.nl";
    protected final String fakeCode = "jzheng2017";
    protected final String fakeUri = "https://github.com/jzheng2017/spotify-web-api-wrapper";
}
