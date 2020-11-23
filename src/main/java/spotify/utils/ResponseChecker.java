package spotify.utils;


import com.google.gson.Gson;
import okhttp3.ResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spotify.exceptions.SpotifyActionFailedException;
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

        if (spotifyError == null) {
            final String errorMessage = "HTTP request to Spotify's server has not been fulfilled correctly. Reason is unknown.";
            logger.error(errorMessage);
            logger.warn("Converting error body to SpotifyError object has failed for some reason.");
            throw new SpotifyActionFailedException(errorMessage);
        }

        final boolean hasFailed = spotifyError.getError().getStatus() > 300;
        final int statusCode = spotifyError.getError().getStatus();
        final String message = spotifyError.getError().getMessage();

        if (hasFailed) {
            logger.error(
                    String.format(
                            "HTTP request to Spotify's server has not been fulfilled correctly. Spotify has returned status code %d with the message: \"%s\".",
                            statusCode,
                            message)
            );
            throw new SpotifyActionFailedException(message);
        }
    }
}
