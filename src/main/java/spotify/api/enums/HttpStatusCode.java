package spotify.api.enums;

/**
 * This class contains all suitable HTTP Status codes used within the API.
 *
 * @author Jiankai Zheng (jk.zheng@hotmail.com)
 * @since 1.1.0
 */

public enum HttpStatusCode {
    OK(200),
    CREATED(201),
    ACCEPTED(202),
    NO_CONTENT(204);

    private final int httpStatusCode;

     /**
     * Constructs an enum value accepting a integer argument whose value is the integer
     * representation of itself.
     * 
     * @param httpStatusCode The http status code to be constructed.
     */
    HttpStatusCode(final int httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }

    public int toInt() {
        return this.httpStatusCode;
    }
}
