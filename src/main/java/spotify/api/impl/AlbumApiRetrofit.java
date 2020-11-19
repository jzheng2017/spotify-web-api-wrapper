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
import spotify.models.albums.AlbumFullList;
import spotify.retrofit.services.AlbumService;

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
    public AlbumFull getAlbum(String albumId) {
        logger.trace("Constructing HTTP call to fetch an album.");
        Call<AlbumFull> httpCall = albumService.getAlbum("Bearer " + this.accessToken, albumId);

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
    public AlbumFullList getAlbums(List<String> listOfAlbumIds, String market) {
        validateAlbumListSizeAndThrowIfExceeded(listOfAlbumIds, 20);
        market = marketEmptyCheck(market);

        String albumIds = String.join(",", listOfAlbumIds);
        logger.debug(String.format("Mapped list of album ids to String: %s", albumIds));

        logger.trace("Constructing HTTP call to fetch albums.");
        Call<AlbumFullList> httpCall = albumService.getAlbums("Bearer " + this.accessToken, albumIds, market);

        try {
            logger.info("Executing HTTP call to fetch albums.");
            logger.debug(String.format("%s / %s", httpCall.request().method(), httpCall.request().url().toString()));
            Response<AlbumFullList> response = httpCall.execute();

            ResponseChecker.throwIfRequestHasNotBeenFulfilledCorrectly(response.errorBody());

            logger.info("Albums have been successfully fetched.");
            return response.body();
        } catch (IOException ex) {
            logger.error("HTTP request to fetch albums has failed.");
            throw new HttpRequestFailedException(ex.getMessage());
        }
    }

    private void setup() {
        logger.trace("Requesting Retrofit HTTP client.");
        Retrofit httpClient = RetrofitClientFactory.getRetrofitClient(ApiUrl.API_URL_HTTPS + ApiUrl.VERSION);

        albumService = httpClient.create(AlbumService.class);
    }

    private String marketEmptyCheck(String market) {
        // this is done because retrofit ignores null values
        // when an empty market value is passed to spotify it will give an error saying the market does not exist
        if (market.isEmpty()) {
            logger.warn("An empty market value has been passed in! The market value has now been set to NULL.");
            return null;
        }

        return market;
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
