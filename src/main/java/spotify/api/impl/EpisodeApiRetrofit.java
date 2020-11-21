package spotify.api.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import spotify.api.interfaces.EpisodeApi;
import spotify.config.ApiUrl;
import spotify.exceptions.HttpRequestFailedException;
import spotify.factories.RetrofitClientFactory;
import spotify.models.episodes.EpisodeFull;
import spotify.models.episodes.EpisodeFullCollection;
import spotify.retrofit.services.EpisodeService;
import spotify.utils.ResponseChecker;
import spotify.utils.ValidatorUtil;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class EpisodeApiRetrofit implements EpisodeApi {
    private final Logger logger = LoggerFactory.getLogger(EpisodeApiRetrofit.class);
    private final String accessToken;
    private EpisodeService episodeService;

    public EpisodeApiRetrofit(String accessToken) {
        this.accessToken = accessToken;
        setup();
    }

    @Override
    public EpisodeFull getEpisode(String episodeId, Map<String, String> options) {
        options = ValidatorUtil.optionsValueCheck(options);

        logger.trace("Constructing HTTP call to fetch episode.");
        Call<EpisodeFull> httpCall = episodeService.getEpisode("Bearer " + this.accessToken, episodeId, options);

        try {
            logger.info("Executing HTTP call to fetch episode.");
            logger.debug(String.format("Fetching episodes %s with following values: %s.", episodeId, options));
            logger.debug(String.format("%s / %s", httpCall.request().method(), httpCall.request().url().toString()));
            Response<EpisodeFull> response = httpCall.execute();

            ResponseChecker.throwIfRequestHasNotBeenFulfilledCorrectly(response.errorBody());

            logger.info("Episode has been successfully fetched.");
            return response.body();
        } catch (IOException ex) {
            logger.error("HTTP request to fetch episode has failed.");
            throw new HttpRequestFailedException(ex.getMessage());
        }
    }

    @Override
    public EpisodeFullCollection getEpisodes(List<String> listOfEpisodeIds, Map<String, String> options) {
        validateEpisodeListSizeAndThrowIfExceeded(listOfEpisodeIds, 50);
        options = ValidatorUtil.optionsValueCheck(options);

        String episodeIds = String.join(",", listOfEpisodeIds);

        logger.trace("Constructing HTTP call to fetch multiple episodes.");
        Call<EpisodeFullCollection> httpCall = episodeService.getEpisodes("Bearer " + this.accessToken, episodeIds, options);

        try {
            logger.info("Executing HTTP call to fetch multiple episodes.");
            logger.debug(String.format("Fetching following episodes: %s with following values: %s.", episodeIds, options));
            logger.debug(String.format("%s / %s", httpCall.request().method(), httpCall.request().url().toString()));
            Response<EpisodeFullCollection> response = httpCall.execute();

            ResponseChecker.throwIfRequestHasNotBeenFulfilledCorrectly(response.errorBody());

            logger.info("Episodes has been successfully fetched.");
            return response.body();
        } catch (IOException ex) {
            logger.error("HTTP request to fetch episodes has failed.");
            throw new HttpRequestFailedException(ex.getMessage());
        }
    }

    private void setup() {
        logger.trace("Requesting Retrofit HTTP client.");
        Retrofit httpClient = RetrofitClientFactory.getRetrofitClient(ApiUrl.API_URL_HTTPS + ApiUrl.VERSION);

        episodeService = httpClient.create(EpisodeService.class);
    }

    private void validateEpisodeListSizeAndThrowIfExceeded(List<String> listOfEpisodeIds, int maximumAmountOfEpisodeIdsAllowed) {
        final int listSize = listOfEpisodeIds.size();

        if (listSize > maximumAmountOfEpisodeIdsAllowed) {
            logger.error("The list of episode ids has exceeded the maximum allowed amount!");
            throw new IllegalArgumentException(String.format(
                    "The maximum amount of episode ids allowed is %d! You have %d.",
                    maximumAmountOfEpisodeIdsAllowed,
                    listSize));
        }
    }
}
