package spotify.api.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import retrofit2.Call;
import retrofit2.Response;
import spotify.api.enums.HttpStatusCode;
import spotify.api.enums.RepeatType;
import spotify.api.interfaces.PlayerApi;
import spotify.exceptions.HttpRequestFailedException;
import spotify.factories.RetrofitHttpServiceFactory;
import spotify.models.paging.CursorBasedPaging;
import spotify.models.players.CurrentlyPlayingObject;
import spotify.models.players.DeviceCollection;
import spotify.models.players.PlayHistory;
import spotify.models.players.PlayingContext;
import spotify.models.players.requests.ChangePlaybackStateRequestBody;
import spotify.retrofit.services.PlayerService;
import spotify.utils.LoggingUtil;
import spotify.utils.ResponseChecker;
import spotify.utils.ValidatorUtil;

import java.io.IOException;
import java.util.Map;

public class PlayerApiRetrofit implements PlayerApi {
    private final Logger logger = LoggerFactory.getLogger(PlayerApiRetrofit.class);
    private final String accessToken;
    private final PlayerService playerService;

    public PlayerApiRetrofit(final String accessToken) {
        this.accessToken = accessToken;
        this.playerService = RetrofitHttpServiceFactory.getPlayerService();
        logger.warn("The /player endpoints are in beta. Spotify API changes may break any of these functions." +
                "\nIf there are any issues please report them on GitHub." +
                " https://github.com/jzheng2017/spotify-web-api-wrapper");
    }

    @Override
    public DeviceCollection getAvailableDevices() {
        logger.trace("Constructing HTTP call to fetch current user's available devices.");
        Call<DeviceCollection> httpCall = playerService.getAvailableDevices("Bearer " + this.accessToken);

        try {
            logger.info("Executing HTTP call to fetch current user's available devices.");
            LoggingUtil.logHttpCall(logger, httpCall);
            Response<DeviceCollection> response = httpCall.execute();

            ResponseChecker.throwIfRequestHasNotBeenFulfilledCorrectly(response, HttpStatusCode.OK);

            logger.info("Available devices have been successfully fetched.");
            return response.body();
        } catch (IOException ex) {
            logger.error("HTTP request to fetch available devices has failed.");
            throw new HttpRequestFailedException(ex.getMessage());
        }
    }

    @Override
    public PlayingContext getCurrentPlayingContext(Map<String, String> options) {
        options = ValidatorUtil.optionsValueCheck(options);

        logger.trace("Constructing HTTP call to fetch current playing context.");
        Call<PlayingContext> httpCall = playerService.getCurrentPlayingContext("Bearer " + this.accessToken, options);

        try {
            logger.info("Executing HTTP call to fetch current playing context.");
            logger.debug(String.format("Fetching current playing context with following values: %s.", options));
            LoggingUtil.logHttpCall(logger, httpCall);
            Response<PlayingContext> response = httpCall.execute();

            ResponseChecker.throwIfRequestHasNotBeenFulfilledCorrectly(response, HttpStatusCode.OK);

            logger.info("Current playing context has been successfully fetched.");
            return response.body();
        } catch (IOException ex) {
            logger.error("HTTP request to fetch current playing context has failed.");
            throw new HttpRequestFailedException(ex.getMessage());
        }
    }

    @Override
    public CursorBasedPaging<PlayHistory> getRecentlyPlayedTracks(Map<String, String> options) {
        options = ValidatorUtil.optionsValueCheck(options);

        logger.trace("Constructing HTTP call to fetch current user's recently played tracks.");
        Call<CursorBasedPaging<PlayHistory>> httpCall = playerService.getRecentlyPlayedTracks("Bearer " + this.accessToken, options);

        try {
            logger.info("Executing HTTP call to fetch current user's recently played tracks.");
            logger.debug(String.format("Fetching recently played tracks with following values: %s.", options));
            LoggingUtil.logHttpCall(logger, httpCall);
            Response<CursorBasedPaging<PlayHistory>> response = httpCall.execute();

            ResponseChecker.throwIfRequestHasNotBeenFulfilledCorrectly(response, HttpStatusCode.OK);

            logger.info("Recently played tracks have been successfully fetched.");
            return response.body();
        } catch (IOException ex) {
            logger.error("HTTP request to fetch recently played tracks has failed.");
            throw new HttpRequestFailedException(ex.getMessage());
        }
    }

    @Override
    public CurrentlyPlayingObject getCurrentlyPlayedObject(Map<String, String> options) {
        options = ValidatorUtil.optionsValueCheck(options);

        logger.trace("Constructing HTTP call to fetch current user's currently played object.");
        Call<CurrentlyPlayingObject> httpCall = playerService.getCurrentlyPlayingObject("Bearer " + this.accessToken, options);

        try {
            logger.info("Executing HTTP call to fetch current user's currently played object.");
            logger.debug(String.format("Fetching currently played object with following values: %s.", options));
            LoggingUtil.logHttpCall(logger, httpCall);
            Response<CurrentlyPlayingObject> response = httpCall.execute();

            ResponseChecker.throwIfRequestHasNotBeenFulfilledCorrectly(response, HttpStatusCode.OK);

            logger.info("Currently played object has been successfully fetched.");
            return response.body();
        } catch (IOException ex) {
            logger.error("HTTP request to fetch currently played object has failed.");
            throw new HttpRequestFailedException(ex.getMessage());
        }
    }

    @Override
    public void addItemToQueue(String uri, Map<String, String> options) {
        options = ValidatorUtil.optionsValueCheck(options);

        logger.trace("Constructing HTTP call to add item to the queue.");
        Call<Void> httpCall = playerService.addItemToQueue("Bearer " + this.accessToken, uri, options);

        try {
            logger.info("Executing HTTP call to add item to the queue.");
            logger.debug(String.format("Adding item to the queue with following values: %s.", options));
            LoggingUtil.logHttpCall(logger, httpCall);
            Response<Void> response = httpCall.execute();

            ResponseChecker.throwIfRequestHasNotBeenFulfilledCorrectly(response, HttpStatusCode.NO_CONTENT);

            logger.info("Request to add an item to the queue has been completed.");
        } catch (IOException ex) {
            logger.error("HTTP request to add item to the queue has failed.");
            throw new HttpRequestFailedException(ex.getMessage());
        }
    }

    @Override
    public void skipToNextTrack(Map<String, String> options) {
        options = ValidatorUtil.optionsValueCheck(options);

        logger.trace("Constructing HTTP call to skip to the next track.");
        Call<Void> httpCall = playerService.skipToNextTrack("Bearer " + this.accessToken, options);

        try {
            logger.info("Executing HTTP call to skip to the next track.");
            logger.debug(String.format("Skipping to the next track with following values: %s.", options));
            LoggingUtil.logHttpCall(logger, httpCall);
            Response<Void> response = httpCall.execute();

            ResponseChecker.throwIfRequestHasNotBeenFulfilledCorrectly(response, HttpStatusCode.NO_CONTENT);

            logger.info("Request to skip to the next track has been completed.");
        } catch (IOException ex) {
            logger.error("HTTP request to skip to the next track has failed.");
            throw new HttpRequestFailedException(ex.getMessage());
        }
    }

    @Override
    public void skipToPreviousTrack(Map<String, String> options) {
        options = ValidatorUtil.optionsValueCheck(options);

        logger.trace("Constructing HTTP call to skip to the previous track.");
        Call<Void> httpCall = playerService.skipToPreviousTrack("Bearer " + this.accessToken, options);

        try {
            logger.info("Executing HTTP call to skip to the previous track.");
            logger.debug(String.format("Skipping to the previous track with following values: %s.", options));
            LoggingUtil.logHttpCall(logger, httpCall);
            Response<Void> response = httpCall.execute();

            ResponseChecker.throwIfRequestHasNotBeenFulfilledCorrectly(response, HttpStatusCode.NO_CONTENT);

            logger.info("Request to skip to the previous track has been completed.");
        } catch (IOException ex) {
            logger.error("HTTP request to skip to the previous track has failed.");
            throw new HttpRequestFailedException(ex.getMessage());
        }
    }

    @Override
    public void pausePlayback(Map<String, String> options) {
        options = ValidatorUtil.optionsValueCheck(options);

        logger.trace("Constructing HTTP call to pause user's playback.");
        Call<Void> httpCall = playerService.pausePlayback("Bearer " + this.accessToken, options);

        try {
            logger.info("Executing HTTP call to pause user's playback.");
            logger.debug(String.format("Pausing playback with following values: %s.", options));
            LoggingUtil.logHttpCall(logger, httpCall);
            Response<Void> response = httpCall.execute();

            ResponseChecker.throwIfRequestHasNotBeenFulfilledCorrectly(response, HttpStatusCode.NO_CONTENT);

            logger.info("Request to pause user's playback has been completed.");
        } catch (IOException ex) {
            logger.error("HTTP request to pause user's playback has failed.");
            throw new HttpRequestFailedException(ex.getMessage());
        }
    }

    @Override
    public void jumpToPositionInCurrentTrack(int positionMs, Map<String, String> options) {
        if (positionMs < 0) {
            final String errorMessage = "Time position must be a positive number!";
            logger.error(errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }

        options = ValidatorUtil.optionsValueCheck(options);

        logger.trace("Constructing HTTP call to jump to a position in the current track.");
        Call<Void> httpCall = playerService.jumpToPositionInCurrentTrack("Bearer " + this.accessToken, positionMs, options);

        try {
            logger.info("Executing HTTP call to jump to a position in the current track.");
            logger.debug(String.format("Jumping to position with following values: %s.", options));
            LoggingUtil.logHttpCall(logger, httpCall);
            Response<Void> response = httpCall.execute();

            ResponseChecker.throwIfRequestHasNotBeenFulfilledCorrectly(response, HttpStatusCode.NO_CONTENT);

            logger.info("Request to jump to position in the track has been completed.");
        } catch (IOException ex) {
            logger.error("HTTP request to jump to position in the track has failed.");
            throw new HttpRequestFailedException(ex.getMessage());
        }
    }

    @Override
    public void setRepeatModePlayback(RepeatType repeatType, Map<String, String> options) {
        options = ValidatorUtil.optionsValueCheck(options);

        logger.trace("Constructing HTTP call to set the repeat mode of the playback.");
        Call<Void> httpCall = playerService.setRepeatModePlayback("Bearer " + this.accessToken, repeatType, options);

        try {
            logger.info("Executing HTTP call to set the repeat mode of the playback.");
            logger.debug(String.format("Setting the repeat mode of the playback with following values: %s.", options));
            LoggingUtil.logHttpCall(logger, httpCall);
            Response<Void> response = httpCall.execute();

            ResponseChecker.throwIfRequestHasNotBeenFulfilledCorrectly(response, HttpStatusCode.NO_CONTENT);

            logger.info("Request to set the repeat mode of the playback has been completed.");
        } catch (IOException ex) {
            logger.error("HTTP request to set the repeat mode of the playback has failed.");
            throw new HttpRequestFailedException(ex.getMessage());
        }
    }

    @Override
    public void setVolumePlayback(int volumePercent, Map<String, String> options) {
        options = ValidatorUtil.optionsValueCheck(options);

        logger.trace("Constructing HTTP call to set the volume of the playback.");
        Call<Void> httpCall = playerService.setVolumePlayback("Bearer " + this.accessToken, volumePercent, options);

        try {
            logger.info("Executing HTTP call to set the volume of the playback.");
            logger.debug(String.format("Setting the volume of the playback with following values: %s.", options));
            LoggingUtil.logHttpCall(logger, httpCall);
            Response<Void> response = httpCall.execute();

            ResponseChecker.throwIfRequestHasNotBeenFulfilledCorrectly(response, HttpStatusCode.NO_CONTENT);

            logger.info("Request to set the volume of the playback has been completed.");
        } catch (IOException ex) {
            logger.error("HTTP request to set the volume of the playback has failed.");
            throw new HttpRequestFailedException(ex.getMessage());
        }
    }

    @Override
    public void changePlaybackState(ChangePlaybackStateRequestBody requestBody) {
        logger.trace("Constructing HTTP call to change the state of the playback.");
        Call<Void> httpCall = playerService.changePlaybackState("Bearer " + this.accessToken, requestBody);

        try {
            logger.info("Executing HTTP call to change the state of the playback.");
            logger.debug(String.format("Changing the state of the playback with following values: %s.", requestBody));
            LoggingUtil.logHttpCall(logger, httpCall);
            Response<Void> response = httpCall.execute();

            ResponseChecker.throwIfRequestHasNotBeenFulfilledCorrectly(response, HttpStatusCode.NO_CONTENT);

            logger.info("Request to change the state of the playback has been completed.");
        } catch (IOException ex) {
            logger.error("HTTP request to change the state of the playback has failed.");
            throw new HttpRequestFailedException(ex.getMessage());
        }
    }

    @Override
    public void shufflePlayback(boolean shuffle, Map<String, String> options) {
        options = ValidatorUtil.optionsValueCheck(options);

        logger.trace("Constructing HTTP call to toggle shuffle of the playback.");
        Call<Void> httpCall = playerService.shufflePlayback("Bearer " + this.accessToken, shuffle, options);

        try {
            logger.info("Executing HTTP call to toggle shuffle of the playback.");
            logger.debug(String.format("Toggling shuffle %s of the playback with following values: %s.", shuffle ? "on" : "off", options));
            LoggingUtil.logHttpCall(logger, httpCall);
            Response<Void> response = httpCall.execute();

            ResponseChecker.throwIfRequestHasNotBeenFulfilledCorrectly(response, HttpStatusCode.NO_CONTENT);

            logger.info("Request to toggle shuffle of the playback has been completed.");
        } catch (IOException ex) {
            logger.error("HTTP request to toggle shuffle of the playback has failed.");
            throw new HttpRequestFailedException(ex.getMessage());
        }
    }
}
