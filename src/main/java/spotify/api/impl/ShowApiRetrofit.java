package spotify.api.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import spotify.api.interfaces.ShowApi;
import spotify.config.ApiUrl;
import spotify.exceptions.HttpRequestFailedException;
import spotify.exceptions.ResponseChecker;
import spotify.factories.RetrofitClientFactory;
import spotify.models.episodes.EpisodeSimplified;
import spotify.models.paging.Paging;
import spotify.models.shows.ShowFull;
import spotify.retrofit.services.ShowService;
import spotify.utils.ValidatorUtil;

import java.io.IOException;

public class ShowApiRetrofit implements ShowApi {
    private final Logger logger = LoggerFactory.getLogger(ShowApiRetrofit.class);
    private final String accessToken;
    private ShowService showService;

    public ShowApiRetrofit(String accessToken) {
        this.accessToken = accessToken;
        setup();
    }

    @Override
    public ShowFull getShow(String showId, String market) {
        market = ValidatorUtil.marketEmptyCheck(market);

        logger.trace("Constructing HTTP call to fetch show.");
        Call<ShowFull> httpCall = showService.getShow("Bearer " + this.accessToken, showId, market);

        try {
            logger.info("Executing HTTP call to fetch show.");
            logger.debug(String.format("%s / %s", httpCall.request().method(), httpCall.request().url().toString()));
            Response<ShowFull> response = httpCall.execute();

            ResponseChecker.throwIfRequestHasNotBeenFulfilledCorrectly(response.errorBody());

            logger.info("Show has been successfully fetched.");
            return response.body();
        } catch (IOException ex) {
            logger.error("HTTP request to fetch show has failed.");
            throw new HttpRequestFailedException(ex.getMessage());
        }
    }

    @Override
    public Paging<EpisodeSimplified> getShowEpisodes(String showId, int limit, int offset, String market) {
        ValidatorUtil.validateFilters(limit, offset);
        market = ValidatorUtil.marketEmptyCheck(market);

        logger.trace("Constructing HTTP call to fetch multiple shows.");
        Call<Paging<EpisodeSimplified>> httpCall = showService.getShowEpisodes("Bearer " + this.accessToken, showId, limit, offset, market);

        try {
            logger.info("Executing HTTP call to fetch multiple shows.");
            logger.debug(String.format("%s / %s", httpCall.request().method(), httpCall.request().url().toString()));
            Response<Paging<EpisodeSimplified>> response = httpCall.execute();

            ResponseChecker.throwIfRequestHasNotBeenFulfilledCorrectly(response.errorBody());

            logger.info("Shows has been successfully fetched.");
            return response.body();
        } catch (IOException ex) {
            logger.error("HTTP request to fetch shows has failed.");
            throw new HttpRequestFailedException(ex.getMessage());
        }
    }

    private void setup() {
        logger.trace("Requesting Retrofit HTTP client.");
        Retrofit httpClient = RetrofitClientFactory.getRetrofitClient(ApiUrl.API_URL_HTTPS + ApiUrl.VERSION);

        showService = httpClient.create(ShowService.class);
    }
}
