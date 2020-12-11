package spotify.api.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import retrofit2.Call;
import retrofit2.Response;
import spotify.api.enums.HttpStatusCode;
import spotify.api.interfaces.AlbumApi;
import spotify.exceptions.HttpRequestFailedException;
import spotify.factories.RetrofitHttpServiceFactory;
import spotify.models.albums.AlbumFull;
import spotify.models.albums.AlbumFullCollection;
import spotify.models.paging.Paging;
import spotify.models.tracks.TrackSimplified;
import spotify.retrofit.services.AlbumService;
import spotify.utils.LoggingUtil;
import spotify.utils.ResponseChecker;
import spotify.utils.ValidatorUtil;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class AlbumApiRetrofit implements AlbumApi {
    private final Logger logger = LoggerFactory.getLogger(AlbumApiRetrofit.class);
    private final String accessToken;
    private final AlbumService albumService;

    public AlbumApiRetrofit(String accessToken) {
        this.accessToken = accessToken;
        this.albumService = RetrofitHttpServiceFactory.getAlbumService();
    }

    public AlbumApiRetrofit(String accessToken, AlbumService albumService) {
        this.accessToken = accessToken;
        this.albumService = albumService;
    }

    @Override
    public AlbumFull getAlbum(String albumId, Map<String, String> options) {
        options = ValidatorUtil.optionsValueCheck(options);

        logger.trace("Constructing HTTP call to fetch an album.");
        Call<AlbumFull> httpCall = albumService.getAlbum("Bearer " + this.accessToken, albumId, options);

        try {
            logger.info("Executing HTTP call to fetch an album.");
            logger.debug("Fetching album {} with following values: {}.", albumId, options);
            LoggingUtil.logHttpCall(logger, httpCall);
            Response<AlbumFull> response = httpCall.execute();

            ResponseChecker.throwIfRequestHasNotBeenFulfilledCorrectly(response, HttpStatusCode.OK);

            logger.info("Album has been successfully fetched.");
            return response.body();
        } catch (IOException e) {
            logger.error("Fetching album has failed.");
            throw new HttpRequestFailedException(e.getMessage());
        }
    }

    @Override
    public AlbumFullCollection getAlbums(List<String> listOfAlbumIds, Map<String, String> options) {
        validateAlbumListSizeAndThrowIfExceeded(listOfAlbumIds, 20);
        options = ValidatorUtil.optionsValueCheck(options);

        String albumIds = String.join(",", listOfAlbumIds);
        logger.debug("Mapped list of album ids to String: {}", albumIds);

        logger.trace("Constructing HTTP call to fetch albums.");
        Call<AlbumFullCollection> httpCall = albumService.getAlbums("Bearer " + this.accessToken, albumIds, options);

        try {
            logger.info("Executing HTTP call to fetch albums.");
            logger.debug("Fetching following albums: {} with following values: {}.", albumIds, options);
            LoggingUtil.logHttpCall(logger, httpCall);
            Response<AlbumFullCollection> response = httpCall.execute();

            ResponseChecker.throwIfRequestHasNotBeenFulfilledCorrectly(response, HttpStatusCode.OK);

            logger.info("Albums have been successfully fetched.");
            return response.body();
        } catch (IOException ex) {
            logger.error("HTTP request to fetch albums has failed.");
            throw new HttpRequestFailedException(ex.getMessage());
        }
    }

    @Override
    public Paging<TrackSimplified> getAlbumTracks(String albumId, Map<String, String> options) {
        options = ValidatorUtil.optionsValueCheck(options);

        logger.trace("Constructing HTTP call to fetch album tracks.");
        Call<Paging<TrackSimplified>> httpCall = albumService.getAlbumTracks("Bearer " + this.accessToken, albumId, options);

        try {
            logger.info("Executing HTTP call to fetch album tracks.");
            logger.debug("Fetching album {} tracks with following values: {}.", albumId, options);
            LoggingUtil.logHttpCall(logger, httpCall);
            Response<Paging<TrackSimplified>> response = httpCall.execute();

            ResponseChecker.throwIfRequestHasNotBeenFulfilledCorrectly(response, HttpStatusCode.OK);

            logger.info("Album tracks have been successfully fetched.");
            return response.body();
        } catch (IOException ex) {
            logger.error("HTTP request to fetch album tracks has failed.");
            throw new HttpRequestFailedException(ex.getMessage());
        }
    }

    private void validateAlbumListSizeAndThrowIfExceeded(List<String> listOfAlbumIds, int maximumAmountOfAlbumIdsAllowed) {
        final int listSize = listOfAlbumIds.size();

        if (listSize > maximumAmountOfAlbumIdsAllowed) {
            logger.error("The list of album ids has exceeded the maximum allowed amount!");
            throw new IllegalArgumentException(String.format(
                    "The maximum amount of album ids allowed is %d! You have %d.",
                    maximumAmountOfAlbumIdsAllowed,
                    listSize));
        }
    }
}
