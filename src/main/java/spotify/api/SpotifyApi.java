package spotify.api;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import spotify.config.ApiUrl;
import spotify.exceptions.HttpRequestFailedException;
import spotify.factories.RetrofitClientFactory;
import spotify.models.TrackFull;
import spotify.models.TrackFullList;
import spotify.retrofit.services.TrackService;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class SpotifyApi {
    private final Retrofit httpClient;
    private TrackService trackService;
    private String accessToken;

    public SpotifyApi() {
        httpClient = RetrofitClientFactory.getRetrofitClient(ApiUrl.API_URL_HTTPS + ApiUrl.VERSION);
    }

    public SpotifyApi(String accessToken) {
        this.accessToken = accessToken;

        httpClient = RetrofitClientFactory.getRetrofitClient(ApiUrl.API_URL_HTTPS + ApiUrl.VERSION);
        trackService = httpClient.create(TrackService.class);
    }

    public TrackFull getTrack(String trackId, String market) {
        Call<TrackFull> httpCall = trackService.getTrack("Bearer " + this.accessToken, trackId, market);

        try {
            Response<TrackFull> response = httpCall.execute();
            return response.body();
        } catch (IOException e) {
            throw new HttpRequestFailedException(e.getMessage());
        }
    }

    public TrackFullList getTracks(List<String> listOfTrackIds, String market) {
        String trackIds = listOfTrackIds.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));

        if (market.isEmpty()) {
            // this is done because retrofit ignores null values
            // when an empty market value is passed to spotify it will give an error saying the market does not exist
            market = null;
        }

        Call<TrackFullList> httpCall = trackService.getTracks("Bearer " + this.accessToken, trackIds, market);

        try {
            Response<TrackFullList> response = httpCall.execute();

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
