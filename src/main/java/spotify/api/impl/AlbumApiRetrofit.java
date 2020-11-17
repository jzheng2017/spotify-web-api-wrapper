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
import spotify.retrofit.services.AlbumService;

import java.io.IOException;

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
            Response<AlbumFull> response = httpCall.execute();

            ResponseChecker.throwIfRequestHasNotBeenFulfilledCorrectly(response.errorBody());

            logger.info("Album has been successfully fetched.");
            return response.body();
        } catch (IOException e) {
            logger.error("Fetching album has failed.");
            throw new HttpRequestFailedException(e.getMessage());
        }
    }

    private void setup() {
        logger.trace("Requesting Retrofit HTTP client.");
        Retrofit httpClient = RetrofitClientFactory.getRetrofitClient(ApiUrl.API_URL_HTTPS + ApiUrl.VERSION);

        albumService = httpClient.create(AlbumService.class);
    }
}
