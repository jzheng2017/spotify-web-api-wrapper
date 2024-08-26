package spotify.api.impl;

import java.io.IOException;
import java.util.List;
import java.util.Map;

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

/**
 * Implementation of the {@link AlbumApi} interface using Retrofit to interact with the Spotify API.
 * This class provides methods to fetch album details, multiple albums, and album tracks from Spotify.
 */

public class AlbumApiRetrofit implements AlbumApi {
    private final Logger logger = LoggerFactory.getLogger(AlbumApiRetrofit.class);
    private final String accessToken;
    private final AlbumService albumService;

    /**
     * constructs an instance of {@link AlbumApiRetrofit} with the specified access token.
     *
     * @param accessToken the access token to be used for authorization
     */

    public AlbumApiRetrofit(final String accessToken) {
        this(accessToken, RetrofitHttpServiceFactory.getAlbumService());
    }

    /**
     * constructs an instance of {@link AlbumApiRetrofit} with the specified access token and album service.
     *
     * @param accessToken the access token to be used for authorization
     * @param albumService the album service to be used for making HTTP calls
     */

    public AlbumApiRetrofit(final String accessToken, final AlbumService albumService) {
        this.accessToken = accessToken;
        this.albumService = albumService;
    }

    /**
     * fetches the details of a single album from Spotify.
     *
     * @param albumId the ID of the album to fetch
     * @param options additional options for the request
     * @return the full details of the album
     * @throws HttpRequestFailedException if the HTTP request fails
     */

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

    /**
     * fetches the details of multiple albums from Spotify.
     *
     * @param listOfAlbumIds the list of album IDs to fetch
     * @param options additional options for the request
     * @return the full details of the albums
     * @throws HttpRequestFailedException if the HTTP request fails
     */

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

    /**
     * fetches the tracks of a specific album from Spotify.
     *
     * @param albumId the ID of the album whose tracks are to be fetched
     * @param options additional options for the request
     * @return a paging object containing the album's tracks
     * @throws HttpRequestFailedException if the HTTP request fails
     */

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

    /**
     * Validates the size of the album list and throws an exception if the size exceeds the maximum allowed limit.
     *
     * @param listOfAlbumIds the list of album IDs to validate
     * @param maximumAmountOfAlbumIdsAllowed the maximum allowed number of album IDs
     * @throws IllegalArgumentException if the list size exceeds the maximum allowed limit
     */

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
