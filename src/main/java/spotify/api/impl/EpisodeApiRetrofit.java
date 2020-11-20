package spotify.api.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import spotify.api.interfaces.EpisodeApi;
import spotify.config.ApiUrl;
import spotify.exceptions.HttpRequestFailedException;
import spotify.exceptions.ResponseChecker;
import spotify.factories.RetrofitClientFactory;
import spotify.models.episodes.EpisodeFull;
import spotify.retrofit.services.EpisodeService;

import java.io.IOException;

public class EpisodeApiRetrofit implements EpisodeApi {
    private final Logger logger = LoggerFactory.getLogger(EpisodeApiRetrofit.class);
    private final String accessToken;
    private EpisodeService episodeService;

    public EpisodeApiRetrofit(String accessToken) {
        this.accessToken = accessToken;
        setup();
    }

    @Override
    public EpisodeFull getEpisode(String episodeId) {
        logger.trace("Constructing HTTP call to fetch episode.");
        Call<EpisodeFull> httpCall = episodeService.getEpisode("Bearer " + this.accessToken, episodeId);

        try {
            logger.info("Executing HTTP call to fetch episode.");
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

    private void setup() {
        logger.trace("Requesting Retrofit HTTP client.");
        Retrofit httpClient = RetrofitClientFactory.getRetrofitClient(ApiUrl.API_URL_HTTPS + ApiUrl.VERSION);

        episodeService = httpClient.create(EpisodeService.class);
    }
}
