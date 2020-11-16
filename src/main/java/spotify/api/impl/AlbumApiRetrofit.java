package spotify.api.impl;

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
    private AlbumService albumService;
    private final String accessToken;

    public AlbumApiRetrofit(String accessToken) {
        this.accessToken = accessToken;
        this.setup();
    }

    @Override
    public AlbumFull getAlbum(String albumId) {
        Call<AlbumFull> httpCall = albumService.getAlbum("Bearer " + this.accessToken, albumId);

        try {
            Response<AlbumFull> response = httpCall.execute();

            ResponseChecker.throwIfRequestHasNotBeenFulfilledCorrectly(response.errorBody());

            return response.body();
        } catch (IOException e) {
            throw new HttpRequestFailedException(e.getMessage());
        }
    }
    private void setup() {
        Retrofit httpClient = RetrofitClientFactory.getRetrofitClient(ApiUrl.API_URL_HTTPS + ApiUrl.VERSION);

        albumService = httpClient.create(AlbumService.class);
    }
}
