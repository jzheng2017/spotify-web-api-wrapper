package spotify.exceptions;

public class SpotifyActionFailedException extends RuntimeException {
    public SpotifyActionFailedException(String message) {
        super(message);
    }
}
