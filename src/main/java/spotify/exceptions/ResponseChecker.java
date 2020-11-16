package spotify.exceptions;


import com.google.gson.Gson;
import okhttp3.ResponseBody;
import spotify.models.errors.SpotifyError;

public class ResponseChecker {

    public static void throwIfRequestHasNotBeenFulfilledCorrectly(ResponseBody errorBody) {
        if (errorBody == null) {
            return;
        }

        Gson gson = new Gson();

        SpotifyError spotifyError = gson.fromJson(errorBody.charStream(), SpotifyError.class);

        final boolean hasFailed = spotifyError.getError().getStatus() > 300;

        if (hasFailed) {
            throw new SpotifyActionFailedException(spotifyError.getError().getMessage());
        }
    }

    private static class SpotifyActionFailedException extends RuntimeException {

        public SpotifyActionFailedException(String message) {
            super(message);
        }
    }
}
