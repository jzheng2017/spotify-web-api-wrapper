package spotify.api.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import retrofit2.Call;
import retrofit2.Response;
import spotify.api.enums.HttpStatusCode;
import spotify.api.interfaces.PlayerApi;
import spotify.exceptions.HttpRequestFailedException;
import spotify.factories.RetrofitHttpServiceFactory;
import spotify.models.paging.CursorBasedPaging;
import spotify.models.players.CurrentlyPlayingObject;
import spotify.models.players.DeviceCollection;
import spotify.models.players.PlayHistory;
import spotify.models.players.PlayingContext;
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
        logger.warn("The /player endpoints are in beta, changes that the Spotify API make may break any of these functions." +
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
}
