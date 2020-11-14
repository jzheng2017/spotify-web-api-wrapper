package spotify.exceptions;

/**
 * This exception gets thrown when the authorization has failed.
 * The cause could for instance be that the credentials are not correct.
 */
public class SpotifyAuthorizationFailedException extends RuntimeException {
    /**
     * Instantiates a new SpotifyAuthorizationFailedException.
     *
     * @param message the exception message
     */
    public SpotifyAuthorizationFailedException(String message) {
        super(message);
    }
}
