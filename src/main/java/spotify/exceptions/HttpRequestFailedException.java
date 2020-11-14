package spotify.exceptions;

/**
 * This exception gets thrown when the HTTP request has failed for any reason.
 */
public class HttpRequestFailedException extends RuntimeException {

    /**
     * Instantiates a new HttpRequestFailedException.
     *
     * @param message the exception message
     */
    public HttpRequestFailedException(String message) {
        super(message);
    }
}
