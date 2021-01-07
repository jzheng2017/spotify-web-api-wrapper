package spotify.api.enums;

/**
 * This class contains and sets the HTTP Status codes when using POST and GET HTTP requests.
 * <p>
 * For further information see: <a href="https://developer.spotify.com/documentation/general/guides/authorization-guide/">Authorization Guide</a>
 * and <a href="https://developer.spotify.com/documentation/web-api/">Web API</a>.
 *
 * @author Jiankai Zheng (jk.zheng@hotmail.com)
 * @since 1.0.0
 */

public enum HttpStatusCode {
    OK(200),
    CREATED(201),
    ACCEPTED(202),
    NO_CONTENT(204);

    private final int httpStatusCode;

     /**
     * Set the HTTP status code.
     * 
     * @param httpStatusCode The http status code.
     */
    HttpStatusCode(final int httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }

    public int toInt() {
        return this.httpStatusCode;
    }
}
