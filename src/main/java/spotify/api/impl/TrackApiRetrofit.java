package spotify.api.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import spotify.api.interfaces.TrackApi;
import spotify.config.ApiUrl;
import spotify.exceptions.HttpRequestFailedException;
import spotify.factories.RetrofitClientFactory;
import spotify.models.audio.AudioAnalysis;
import spotify.models.audio.AudioFeatures;
import spotify.models.audio.AudioFeaturesCollection;
import spotify.models.tracks.TrackFull;
import spotify.models.tracks.TrackFullCollection;
import spotify.retrofit.services.TrackService;
import spotify.utils.ResponseChecker;
import spotify.utils.ValidatorUtil;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class TrackApiRetrofit implements TrackApi {
    private final Logger logger = LoggerFactory.getLogger(TrackApiRetrofit.class);
    private final String accessToken;
    private TrackService trackService;

    public TrackApiRetrofit(String accessToken) {
        this.accessToken = accessToken;
        setup();
    }

    @Override
    public TrackFull getTrack(String trackId, Map<String, String> options) {
        options = ValidatorUtil.optionsValueCheck(options);

        logger.trace("Constructing HTTP call to fetch a track.");
        Call<TrackFull> httpCall = trackService.getTrack("Bearer " + this.accessToken, trackId, options);

        try {
            logger.info("Executing HTTP call to fetch a track.");
            logger.debug(String.format("Fetching track %s with following values: %s.", trackId, options));
            logger.debug(String.format("%s / %s", httpCall.request().method(), httpCall.request().url().toString()));
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
    public TrackFullCollection getTracks(List<String> listOfTrackIds, Map<String, String> options) {
        validateTrackListSizeAndThrowIfExceeded(listOfTrackIds, 50);
        options = ValidatorUtil.optionsValueCheck(options);

        String trackIds = String.join(",", listOfTrackIds);

        logger.trace("Constructing HTTP call to fetch multiple tracks.");
        Call<TrackFullCollection> httpCall = trackService.getTracks("Bearer " + this.accessToken, trackIds, options);

        try {
            logger.info("Executing HTTP call to fetch multiple tracks.");
            logger.debug(String.format("Fetching following tracks: %s with following values: %s.", trackIds, options));
            logger.debug(String.format("%s / %s", httpCall.request().method(), httpCall.request().url().toString()));
            Response<TrackFullCollection> response = httpCall.execute();

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
            logger.debug(String.format("Fetching track %s audio features.", trackId));
            logger.debug(String.format("%s / %s", httpCall.request().method(), httpCall.request().url().toString()));
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
    public AudioFeaturesCollection getTracksAudioFeatures(List<String> listOfTrackIds) {
        validateTrackListSizeAndThrowIfExceeded(listOfTrackIds, 100);

        String trackIds = String.join(",", listOfTrackIds);

        logger.trace("Constructing HTTP call to fetch audio features.");
        Call<AudioFeaturesCollection> httpCall = trackService.getTracksAudioFeatures("Bearer " + this.accessToken, trackIds);

        try {
            logger.info("Executing HTTP call to fetch track audio features.");
            logger.debug(String.format("Fetching following tracks: %s audio features.", trackIds));
            logger.debug(String.format("%s / %s", httpCall.request().method(), httpCall.request().url().toString()));
            Response<AudioFeaturesCollection> response = httpCall.execute();

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
            logger.debug(String.format("%s / %s", httpCall.request().method(), httpCall.request().url().toString()));
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
