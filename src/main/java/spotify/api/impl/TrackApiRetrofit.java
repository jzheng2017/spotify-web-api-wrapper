package spotify.api.impl;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import spotify.api.interfaces.TrackApi;
import spotify.config.ApiUrl;
import spotify.exceptions.HttpRequestFailedException;
import spotify.exceptions.ResponseChecker;
import spotify.factories.RetrofitClientFactory;
import spotify.models.audio.AudioAnalysis;
import spotify.models.audio.AudioFeatures;
import spotify.models.audio.AudioFeaturesList;
import spotify.models.tracks.TrackFull;
import spotify.models.tracks.TrackFullList;
import spotify.retrofit.services.TrackService;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class TrackApiRetrofit implements TrackApi {
    private TrackService trackService;
    private final String accessToken;

    public TrackApiRetrofit(String accessToken) {
        this.accessToken = accessToken;
        setup();
    }

    @Override
    public TrackFull getTrack(String trackId, String market) {
        market = marketEmptyCheck(market);

        Call<TrackFull> httpCall = trackService.getTrack("Bearer " + this.accessToken, trackId, market);

        try {
            Response<TrackFull> response = httpCall.execute();

            ResponseChecker.throwIfRequestHasNotBeenFulfilledCorrectly(response.errorBody());

            return response.body();
        } catch (IOException e) {
            throw new HttpRequestFailedException(e.getMessage());
        }
    }

    @Override
    public TrackFullList getTracks(List<String> listOfTrackIds, String market) {
        validateTrackListSizeAndThrowIfExceeded(listOfTrackIds, 50);
        market = marketEmptyCheck(market);

        String trackIds = listOfTrackIds.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));


        Call<TrackFullList> httpCall = trackService.getTracks("Bearer " + this.accessToken, trackIds, market);

        try {
            Response<TrackFullList> response = httpCall.execute();

            ResponseChecker.throwIfRequestHasNotBeenFulfilledCorrectly(response.errorBody());

            return response.body();
        } catch (IOException e) {
            throw new HttpRequestFailedException(e.getMessage());
        }
    }

    @Override
    public AudioFeatures getTrackAudioFeatures(String trackId) {
        Call<AudioFeatures> httpCall = trackService.getTrackAudioFeatures("Bearer " + this.accessToken, trackId);

        try {
            Response<AudioFeatures> response = httpCall.execute();

            ResponseChecker.throwIfRequestHasNotBeenFulfilledCorrectly(response.errorBody());

            return response.body();
        } catch (IOException e) {
            throw new HttpRequestFailedException(e.getMessage());
        }
    }

    @Override
    public AudioFeaturesList getTracksAudioFeatures(List<String> listOfTrackIds) {
        validateTrackListSizeAndThrowIfExceeded(listOfTrackIds, 100);

        String trackIds = listOfTrackIds.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));

        Call<AudioFeaturesList> httpCall = trackService.getTracksAudioFeatures("Bearer " + this.accessToken, trackIds);

        try {
            Response<AudioFeaturesList> response = httpCall.execute();

            ResponseChecker.throwIfRequestHasNotBeenFulfilledCorrectly(response.errorBody());

            return response.body();
        } catch (IOException e) {
            throw new HttpRequestFailedException(e.getMessage());
        }
    }

    @Override
    public AudioAnalysis getTrackAudioAnalysis(String trackId) {
        Call<AudioAnalysis> httpCall = trackService.getTrackAudioAnalysis("Bearer " + this.accessToken, trackId);

        try {
            Response<AudioAnalysis> response = httpCall.execute();

            ResponseChecker.throwIfRequestHasNotBeenFulfilledCorrectly(response.errorBody());

            return response.body();
        } catch (IOException e) {
            throw new HttpRequestFailedException(e.getMessage());
        }
    }

    private void setup() {
        Retrofit httpClient = RetrofitClientFactory.getRetrofitClient(ApiUrl.API_URL_HTTPS + ApiUrl.VERSION);

        trackService = httpClient.create(TrackService.class);
    }

    private String marketEmptyCheck(String market) {
        // this is done because retrofit ignores null values
        // when an empty market value is passed to spotify it will give an error saying the market does not exist
        return market.isEmpty() ? null : market;
    }

    private void validateTrackListSizeAndThrowIfExceeded(List<String> listOfTrackIds, int maximumAmountOfTrackIdsAllowed) {
        final int listSize = listOfTrackIds.size();

        if (listSize > maximumAmountOfTrackIdsAllowed) {
            throw new IllegalArgumentException(String.format(
                    "The maximum amount of track ids allowed is %d! You have %d.",
                    maximumAmountOfTrackIdsAllowed,
                    listSize));
        }
    }
}
