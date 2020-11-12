package spotify.exceptions;

public class HttpRequestFailedException extends RuntimeException {

    public HttpRequestFailedException(String message) {
        super(message);
    }
}
