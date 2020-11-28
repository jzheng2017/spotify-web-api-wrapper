package spotify.api.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import retrofit2.Call;
import retrofit2.Response;
import spotify.api.enums.HttpStatusCode;
import spotify.api.interfaces.TrackApi;
import spotify.exceptions.HttpRequestFailedException;
import spotify.factories.RetrofitHttpServiceFactory;
import spotify.models.audio.AudioAnalysis;
import spotify.models.audio.AudioFeatures;
import spotify.models.audio.AudioFeaturesCollection;
import spotify.models.tracks.TrackFull;
import spotify.models.tracks.TrackFullCollection;
import spotify.retrofit.services.TrackService;
import spotify.utils.LoggingUtil;
import spotify.utils.ResponseChecker;
import spotify.utils.ValidatorUtil;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class TrackApiRetrofit implements TrackApi {
    private final Logger logger = LoggerFactory.getLogger(TrackApiRetrofit.class);
    private final String accessToken;
    private final TrackService trackService;

    public TrackApiRetrofit(String accessToken) {
        this.accessToken = accessToken;
        this.trackService = RetrofitHttpServiceFactory.getTrackService();
    }

    @Override
    public TrackFull getTrack(String trackId, Map<String, String> options) {
        options = ValidatorUtil.optionsValueCheck(options);

        logger.trace("Constructing HTTP call to fetch a track.");
        Call<TrackFull> httpCall = trackService.getTrack("Bearer " + this.accessToken, trackId, options);

        try {
            logger.info("Executing HTTP call to fetch a track.");
            logger.debug(String.format("Fetching track %s with following values: %s.", trackId, options));
            LoggingUtil.logHttpCall(logger, httpCall);
            Response<TrackFull> response = httpCall.execute();

            ResponseChecker.throwIfRequestHasNotBeenFulfilledCorrectly(response, HttpStatusCode.OK);

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
            LoggingUtil.logHttpCall(logger, httpCall);
            Response<TrackFullCollection> response = httpCall.execute();

            ResponseChecker.throwIfRequestHasNotBeenFulfilledCorrectly(response, HttpStatusCode.OK);

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
            LoggingUtil.logHttpCall(logger, httpCall);
            Response<AudioFeatures> response = httpCall.execute();

            ResponseChecker.throwIfRequestHasNotBeenFulfilledCorrectly(response, HttpStatusCode.OK);

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
            LoggingUtil.logHttpCall(logger, httpCall);
            Response<AudioFeaturesCollection> response = httpCall.execute();

            ResponseChecker.throwIfRequestHasNotBeenFulfilledCorrectly(response, HttpStatusCode.OK);

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
            LoggingUtil.logHttpCall(logger, httpCall);
            Response<AudioAnalysis> response = httpCall.execute();

            ResponseChecker.throwIfRequestHasNotBeenFulfilledCorrectly(response, HttpStatusCode.OK);

            logger.info("Track audio analysis has been successfully fetched.");
            return response.body();
        } catch (IOException e) {
            logger.error("Fetching track audio analysis has failed.");
            throw new HttpRequestFailedException(e.getMessage());
        }
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
