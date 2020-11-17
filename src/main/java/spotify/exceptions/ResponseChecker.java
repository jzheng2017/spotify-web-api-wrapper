package spotify.exceptions;


import com.google.gson.Gson;
import okhttp3.ResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spotify.models.errors.SpotifyError;

public class ResponseChecker {
    private final static Logger logger = LoggerFactory.getLogger(ResponseChecker.class);

    public static void throwIfRequestHasNotBeenFulfilledCorrectly(ResponseBody errorBody) {
        if (errorBody == null) {
            return;
        }

        Gson gson = new Gson();

        logger.trace("Spotify API has returned an error body.");
        SpotifyError spotifyError = gson.fromJson(errorBody.charStream(), SpotifyError.class);

        final boolean hasFailed = spotifyError.getError().getStatus() > 300;

        if (hasFailed) {
            logger.error(
                    String.format("HTTP request to Spotify's server has not been fulfilled correctly. Spotify has returned status code %d",
                            spotifyError.getError().getStatus())
            );
            throw new SpotifyActionFailedException(spotifyError.getError().getMessage());
        }
    }

    private static class SpotifyActionFailedException extends RuntimeException {

        public SpotifyActionFailedException(String message) {
            super(message);
        }
    }
}
