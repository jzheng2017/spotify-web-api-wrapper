package spotify.api.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import spotify.api.interfaces.AlbumApi;
import spotify.config.ApiUrl;
import spotify.exceptions.HttpRequestFailedException;
import spotify.exceptions.ResponseChecker;
import spotify.factories.RetrofitClientFactory;
import spotify.models.albums.AlbumFull;
import spotify.models.albums.AlbumFullCollection;
import spotify.models.paging.Paging;
import spotify.models.tracks.TrackSimplified;
import spotify.retrofit.services.AlbumService;
import spotify.utils.ValidatorUtil;

import java.io.IOException;
import java.util.List;

public class AlbumApiRetrofit implements AlbumApi {
    private final Logger logger = LoggerFactory.getLogger(AlbumApiRetrofit.class);
    private final String accessToken;
    private AlbumService albumService;

    public AlbumApiRetrofit(String accessToken) {
        this.accessToken = accessToken;
        this.setup();
    }

    @Override
    public AlbumFull getAlbum(String albumId, String market) {
        market = ValidatorUtil.marketEmptyCheck(market);

        logger.trace("Constructing HTTP call to fetch an album.");
        Call<AlbumFull> httpCall = albumService.getAlbum("Bearer " + this.accessToken, albumId, market);

        try {
            logger.info("Executing HTTP call to fetch an album.");
            logger.debug(String.format("%s / %s", httpCall.request().method(), httpCall.request().url().toString()));
            Response<AlbumFull> response = httpCall.execute();

            ResponseChecker.throwIfRequestHasNotBeenFulfilledCorrectly(response.errorBody());

            logger.info("Album has been successfully fetched.");
            return response.body();
        } catch (IOException e) {
            logger.error("Fetching album has failed.");
            throw new HttpRequestFailedException(e.getMessage());
        }
    }

    @Override
    public AlbumFullCollection getAlbums(List<String> listOfAlbumIds, String market) {
        validateAlbumListSizeAndThrowIfExceeded(listOfAlbumIds, 20);
        market = ValidatorUtil.marketEmptyCheck(market);

        String albumIds = String.join(",", listOfAlbumIds);
        logger.debug(String.format("Mapped list of album ids to String: %s", albumIds));

        logger.trace("Constructing HTTP call to fetch albums.");
        Call<AlbumFullCollection> httpCall = albumService.getAlbums("Bearer " + this.accessToken, albumIds, market);

        try {
            logger.info("Executing HTTP call to fetch albums.");
            logger.debug(String.format("%s / %s", httpCall.request().method(), httpCall.request().url().toString()));
            Response<AlbumFullCollection> response = httpCall.execute();

            ResponseChecker.throwIfRequestHasNotBeenFulfilledCorrectly(response.errorBody());

            logger.info("Albums have been successfully fetched.");
            return response.body();
        } catch (IOException ex) {
            logger.error("HTTP request to fetch albums has failed.");
            throw new HttpRequestFailedException(ex.getMessage());
        }
    }

    @Override
    public Paging<TrackSimplified> getAlbumTracks(String albumId, int limit, int offset, String market) {
        logger.trace("Validating passed in values");
        logger.debug(String.format("Passed in values: album id = %s, limit = %d, offset = %d, market = \"%s\"", albumId, limit, offset, market));
        if (limit <= 0) {
            throw new IllegalArgumentException(String.format("Limit must be at least 1! Current passed in limit value: %d", limit));
        }

        if (offset < 0) {
            throw new IllegalArgumentException(String.format("Offset must be at least 0! Current passed in offset value: %d", offset));
        }

        market = ValidatorUtil.marketEmptyCheck(market);

        logger.trace("Constructing HTTP call to fetch album tracks.");
        Call<Paging<TrackSimplified>> httpCall = albumService.getAlbumTracks("Bearer " + this.accessToken, albumId, limit, offset, market);

        try {
            logger.info("Executing HTTP call to fetch album tracks.");
            logger.debug(String.format("%s / %s", httpCall.request().method(), httpCall.request().url().toString()));
            Response<Paging<TrackSimplified>> response = httpCall.execute();

            ResponseChecker.throwIfRequestHasNotBeenFulfilledCorrectly(response.errorBody());

            logger.info("Album tracks have been successfully fetched.");
            return response.body();
        } catch (IOException ex) {
            logger.error("HTTP request to fetch album tracks has failed.");
            throw new HttpRequestFailedException(ex.getMessage());
        }
    }


    private void setup() {
        logger.trace("Requesting Retrofit HTTP client.");
        Retrofit httpClient = RetrofitClientFactory.getRetrofitClient(ApiUrl.API_URL_HTTPS + ApiUrl.VERSION);

        albumService = httpClient.create(AlbumService.class);
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
