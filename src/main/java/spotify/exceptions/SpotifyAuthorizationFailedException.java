package spotify.exceptions;

public class SpotifyAuthorizationFailedException extends RuntimeException {
    public SpotifyAuthorizationFailedException(String message) {
        super(message);
    }
}
