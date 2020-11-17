package spotify.api.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final Logger logger = LoggerFactory.getLogger(TrackApiRetrofit.class);
    private final String accessToken;
    private TrackService trackService;

    public TrackApiRetrofit(String accessToken) {
        this.accessToken = accessToken;
        setup();
    }

    @Override
    public TrackFull getTrack(String trackId, String market) {
        market = marketEmptyCheck(market);

        logger.trace("Constructing HTTP call to fetch a track.");
        Call<TrackFull> httpCall = trackService.getTrack("Bearer " + this.accessToken, trackId, market);

        try {
            logger.info("Executing HTTP call to fetch a track.");
            Response<TrackFull> response = httpCall.execute();

            ResponseChecker.throwIfRequestHasNotBeenFulfilledCorrectly(response.errorBody());

            logger.info("Track has been successfully fetched.");
            return response.body();
        } catch (IOException e) {
            logger.error("Fetching track has failed.");
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

        logger.trace("Constructing HTTP call to fetch multiple tracks.");
        Call<TrackFullList> httpCall = trackService.getTracks("Bearer " + this.accessToken, trackIds, market);

        try {
            logger.info("Executing HTTP call to fetch multiple tracks.");
            Response<TrackFullList> response = httpCall.execute();

            ResponseChecker.throwIfRequestHasNotBeenFulfilledCorrectly(response.errorBody());

            logger.info("Tracks have been successfully fetched.");
            return response.body();
        } catch (IOException e) {
            logger.error("Fetching tracks has failed.");
            throw new HttpRequestFailedException(e.getMessage());
        }
    }

    @Override
    public AudioFeatures getTrackAudioFeatures(String trackId) {
        logger.trace("Constructing HTTP call to fetch audio features.");
        Call<AudioFeatures> httpCall = trackService.getTrackAudioFeatures("Bearer " + this.accessToken, trackId);

        try {
            logger.info("Executing HTTP call to fetch multiple track audio features.");
            Response<AudioFeatures> response = httpCall.execute();

            ResponseChecker.throwIfRequestHasNotBeenFulfilledCorrectly(response.errorBody());

            logger.info("Track audio features has been successfully fetched.");
            return response.body();
        } catch (IOException e) {
            logger.error("Fetching track audio features has failed.");
            throw new HttpRequestFailedException(e.getMessage());
        }
    }

    @Override
    public AudioFeaturesList getTracksAudioFeatures(List<String> listOfTrackIds) {
        validateTrackListSizeAndThrowIfExceeded(listOfTrackIds, 100);

        String trackIds = listOfTrackIds.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));

        logger.trace("Constructing HTTP call to fetch audio features.");
        Call<AudioFeaturesList> httpCall = trackService.getTracksAudioFeatures("Bearer " + this.accessToken, trackIds);

        try {
            logger.info("Executing HTTP call to fetch track audio features.");
            Response<AudioFeaturesList> response = httpCall.execute();

            ResponseChecker.throwIfRequestHasNotBeenFulfilledCorrectly(response.errorBody());

            logger.info("Track audio features has been successfully fetched.");
            return response.body();
        } catch (IOException e) {
            logger.error("Fetching track audio features has failed.");
            throw new HttpRequestFailedException(e.getMessage());
        }
    }

    @Override
    public AudioAnalysis getTrackAudioAnalysis(String trackId) {
        logger.trace("Constructing HTTP call to fetch audio analysis.");
        Call<AudioAnalysis> httpCall = trackService.getTrackAudioAnalysis("Bearer " + this.accessToken, trackId);

        try {
            logger.info(String.format("Executing HTTP call to fetch audio analysis for track %s.", trackId));
            Response<AudioAnalysis> response = httpCall.execute();

            ResponseChecker.throwIfRequestHasNotBeenFulfilledCorrectly(response.errorBody());

            logger.info("Track audio analysis has been successfully fetched.");
            return response.body();
        } catch (IOException e) {
            logger.error("Fetching track audio analysis has failed.");
            throw new HttpRequestFailedException(e.getMessage());
        }
    }

    private void setup() {
        logger.trace("Requesting Retrofit HTTP client.");
        Retrofit httpClient = RetrofitClientFactory.getRetrofitClient(ApiUrl.API_URL_HTTPS + ApiUrl.VERSION);

        trackService = httpClient.create(TrackService.class);
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

    private void validateTrackListSizeAndThrowIfExceeded(List<String> listOfTrackIds, int maximumAmountOfTrackIdsAllowed) {
        final int listSize = listOfTrackIds.size();

        if (listSize > maximumAmountOfTrackIdsAllowed) {
            logger.error("The list of track ids has exceeded the maximum allowed amount!");
            throw new IllegalArgumentException(String.format(
                    "The maximum amount of track ids allowed is %d! You have %d.",
                    maximumAmountOfTrackIdsAllowed,
                    listSize));
        }
    }
}
