package spotify.api;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import spotify.config.ApiUrl;
import spotify.exceptions.HttpRequestFailedException;
import spotify.factories.RetrofitClientFactory;
import spotify.models.TrackFull;
import spotify.retrofit.services.TrackService;

import java.io.IOException;

public class SpotifyApi {
    private final Retrofit httpClient;
    private String accessToken;

    public SpotifyApi() {
        httpClient = RetrofitClientFactory.getRetrofitClient(ApiUrl.API_URL_HTTPS + ApiUrl.VERSION);
    }

    public SpotifyApi(String accessToken) {
        this.accessToken = accessToken;

        httpClient = RetrofitClientFactory.getRetrofitClient(ApiUrl.API_URL_HTTPS + ApiUrl.VERSION);
    }

    public TrackFull getTrack(String trackId, String market) {
        TrackService trackService = httpClient.create(TrackService.class);

        Call<TrackFull> httpCall = trackService.getTrack("Bearer " + this.accessToken, trackId, market);

        try {
            Response<TrackFull> response = httpCall.execute();
            return response.body();
        } catch (IOException e) {
            throw new HttpRequestFailedException(e.getMessage());
        }
    }


    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
