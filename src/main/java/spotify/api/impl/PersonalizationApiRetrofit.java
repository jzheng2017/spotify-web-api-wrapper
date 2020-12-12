package spotify.api.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import retrofit2.Call;
import retrofit2.Response;
import spotify.api.enums.HttpStatusCode;
import spotify.api.interfaces.PersonalizationApi;
import spotify.exceptions.HttpRequestFailedException;
import spotify.factories.RetrofitHttpServiceFactory;
import spotify.models.artists.ArtistFull;
import spotify.models.paging.Paging;
import spotify.models.tracks.TrackFull;
import spotify.retrofit.services.PersonalizationService;
import spotify.utils.LoggingUtil;
import spotify.utils.ResponseChecker;
import spotify.utils.ValidatorUtil;

import java.io.IOException;
import java.util.Map;

public class PersonalizationApiRetrofit implements PersonalizationApi {
    private final Logger logger = LoggerFactory.getLogger(PersonalizationApiRetrofit.class);
    private final String accessToken;
    private final PersonalizationService personalizationService;

    public PersonalizationApiRetrofit(final String accessToken) {
        this.accessToken = accessToken;
        this.personalizationService = RetrofitHttpServiceFactory.getPersonalizationService();
    }

    public PersonalizationApiRetrofit(final String accessToken, final PersonalizationService personalizationService) {
        this.accessToken = accessToken;
        this.personalizationService = personalizationService;
    }

    @Override
    public Paging<ArtistFull> getTopArtists(Map<String, String> options) {
        options = ValidatorUtil.optionsValueCheck(options);

        logger.trace("Constructing HTTP call to fetch top artists.");
        Call<Paging<ArtistFull>> httpCall = personalizationService.getTopArtists("Bearer " + this.accessToken, options);

        try {
            logger.info("Executing HTTP call to fetch top artists.");
            logger.debug("Fetching top artists with following values: {}.", options);
            LoggingUtil.logHttpCall(logger, httpCall);
            Response<Paging<ArtistFull>> response = httpCall.execute();

            ResponseChecker.throwIfRequestHasNotBeenFulfilledCorrectly(response, HttpStatusCode.OK);

            logger.info("Artists has been successfully fetched.");
            return response.body();
        } catch (IOException e) {
            logger.error("Fetching artists has failed.");
            throw new HttpRequestFailedException(e.getMessage());
        }
    }

    @Override
    public Paging<TrackFull> getTopTracks(Map<String, String> options) {
        options = ValidatorUtil.optionsValueCheck(options);

        logger.trace("Constructing HTTP call to fetch top tracks.");
        Call<Paging<TrackFull>> httpCall = personalizationService.getTopTracks("Bearer " + this.accessToken, options);

        try {
            logger.info("Executing HTTP call to fetch top tracks.");
            logger.debug("Fetching top tracks with following values: {}.", options);
            LoggingUtil.logHttpCall(logger, httpCall);
            Response<Paging<TrackFull>> response = httpCall.execute();

            ResponseChecker.throwIfRequestHasNotBeenFulfilledCorrectly(response, HttpStatusCode.OK);

            logger.info("Tracks has been successfully fetched.");
            return response.body();
        } catch (IOException e) {
            logger.error("Fetching tracks has failed.");
            throw new HttpRequestFailedException(e.getMessage());
        }
    }
}
