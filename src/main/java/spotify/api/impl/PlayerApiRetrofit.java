package spotify.api.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import retrofit2.Call;
import retrofit2.Response;
import spotify.api.enums.HttpStatusCode;
import spotify.api.interfaces.PlayerApi;
import spotify.exceptions.HttpRequestFailedException;
import spotify.factories.RetrofitHttpServiceFactory;
import spotify.models.players.DeviceCollection;
import spotify.retrofit.services.PlayerService;
import spotify.utils.LoggingUtil;
import spotify.utils.ResponseChecker;

import java.io.IOException;

public class PlayerApiRetrofit implements PlayerApi {
    private final Logger logger = LoggerFactory.getLogger(PlayerApiRetrofit.class);
    private final String accessToken;
    private final PlayerService playerService;

    public PlayerApiRetrofit(final String accessToken) {
        this.accessToken = accessToken;
        this.playerService = RetrofitHttpServiceFactory.getPlayerService();
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
}
