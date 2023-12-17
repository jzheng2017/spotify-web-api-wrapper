package spotify.exceptions;

public class SpotifyActionFailedException extends RuntimeException {
    public static final String EMPTY_RESPONSE_BODY_UNKNOWN_REASON = "Empty response body has been returned. Reason is unknown";
    public SpotifyActionFailedException(String message) {
        super(message);
    }
}
