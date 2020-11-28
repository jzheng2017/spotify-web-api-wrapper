package spotify.api.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import retrofit2.Call;
import retrofit2.Response;
import spotify.api.enums.HttpStatusCode;
import spotify.api.interfaces.PlaylistApi;
import spotify.exceptions.HttpRequestFailedException;
import spotify.factories.RetrofitHttpServiceFactory;
import spotify.models.paging.Paging;
import spotify.models.playlists.PlaylistSimplified;
import spotify.retrofit.services.PlaylistService;
import spotify.utils.LoggingUtil;
import spotify.utils.ResponseChecker;
import spotify.utils.ValidatorUtil;

import java.io.IOException;
import java.util.Map;

public class PlaylistApiRetrofit implements PlaylistApi {
    private final Logger logger = LoggerFactory.getLogger(PlaylistApiRetrofit.class);
    private final String accessToken;
    private final PlaylistService playlistService;


    public PlaylistApiRetrofit(final String accessToken) {
        this.accessToken = accessToken;
        playlistService = RetrofitHttpServiceFactory.getPlaylistService();
    }

    @Override
    public Paging<PlaylistSimplified> getPlaylists(Map<String, String> options) {
        options = ValidatorUtil.optionsValueCheck(options);

        logger.trace("Constructing HTTP call to fetch current user's playlists.");
        Call<Paging<PlaylistSimplified>> httpCall = playlistService.getPlaylists("Bearer " + this.accessToken, options);

        try {
            logger.info("Executing HTTP call to fetch current user's playlists.");
            logger.debug(String.format("Fetching playlists with following parameter values: %s.", options));
            LoggingUtil.logHttpCall(logger, httpCall);
            Response<Paging<PlaylistSimplified>> response = httpCall.execute();

            ResponseChecker.throwIfRequestHasNotBeenFulfilledCorrectly(response, HttpStatusCode.OK);

            logger.info("Playlists have been successfully fetched");
            return response.body();
        } catch (IOException ex) {
            logger.error("HTTP request to fetch playlists has failed.");
            throw new HttpRequestFailedException(ex.getMessage());
        }
    }

    @Override
    public Paging<PlaylistSimplified> getUserPlaylists(String userId, Map<String, String> options) {
        options = ValidatorUtil.optionsValueCheck(options);

        logger.trace("Constructing HTTP call to fetch a user's playlists.");
        Call<Paging<PlaylistSimplified>> httpCall = playlistService.getUserPlaylists("Bearer " + this.accessToken, userId, options);

        try {
            logger.info("Executing HTTP call to fetch a user's playlists.");
            logger.debug(String.format("Fetching playlists from user %s with the following parameter values: %s.", userId, options));
            LoggingUtil.logHttpCall(logger, httpCall);
            Response<Paging<PlaylistSimplified>> response = httpCall.execute();

            ResponseChecker.throwIfRequestHasNotBeenFulfilledCorrectly(response, HttpStatusCode.OK);

            logger.info("Playlists have been successfully fetched");
            return response.body();
        } catch (IOException ex) {
            logger.error("HTTP request to fetch playlists has failed.");
            throw new HttpRequestFailedException(ex.getMessage());
        }
    }
}
