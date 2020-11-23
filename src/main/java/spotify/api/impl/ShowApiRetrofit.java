package spotify.api.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import retrofit2.Call;
import retrofit2.Response;
import spotify.api.interfaces.ShowApi;
import spotify.exceptions.HttpRequestFailedException;
import spotify.factories.RetrofitHttpServiceFactory;
import spotify.models.episodes.EpisodeSimplified;
import spotify.models.paging.Paging;
import spotify.models.shows.ShowFull;
import spotify.models.shows.ShowSimplifiedCollection;
import spotify.retrofit.services.ShowService;
import spotify.utils.ResponseChecker;
import spotify.utils.ValidatorUtil;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class ShowApiRetrofit implements ShowApi {
    private final Logger logger = LoggerFactory.getLogger(ShowApiRetrofit.class);
    private final String accessToken;
    private final ShowService showService;

    public ShowApiRetrofit(String accessToken) {
        this.accessToken = accessToken;
        this.showService = RetrofitHttpServiceFactory.getShowService();
    }

    @Override
    public ShowFull getShow(String showId, Map<String, String> options) {
        options = ValidatorUtil.optionsValueCheck(options);

        logger.trace("Constructing HTTP call to fetch show.");
        Call<ShowFull> httpCall = showService.getShow("Bearer " + this.accessToken, showId, options);

        try {
            logger.info("Executing HTTP call to fetch show.");
            logger.debug(String.format("Fetching show %s with following values: %s.", showId, options));
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
    public Paging<EpisodeSimplified> getShowEpisodes(String showId, Map<String, String> options) {
        options = ValidatorUtil.optionsValueCheck(options);

        logger.trace("Constructing HTTP call to fetch show episodes.");
        Call<Paging<EpisodeSimplified>> httpCall = showService.getShowEpisodes("Bearer " + this.accessToken, showId, options);

        try {
            logger.info("Executing HTTP call to fetch show episodes.");
            logger.debug(String.format("Fetching show %s episodes with following values: %s.", showId, options));
            logger.debug(String.format("%s / %s", httpCall.request().method(), httpCall.request().url().toString()));
            Response<Paging<EpisodeSimplified>> response = httpCall.execute();

            ResponseChecker.throwIfRequestHasNotBeenFulfilledCorrectly(response.errorBody());

            logger.info("Show episodes has been successfully fetched.");
            return response.body();
        } catch (IOException ex) {
            logger.error("HTTP request to fetch show episodes has failed.");
            throw new HttpRequestFailedException(ex.getMessage());
        }
    }

    @Override
    public ShowSimplifiedCollection getShows(List<String> listOfShowIds, Map<String, String> options) {
        String showIds = String.join(",", listOfShowIds);
        options = ValidatorUtil.optionsValueCheck(options);

        logger.trace("Constructing HTTP call to fetch multiple shows.");
        Call<ShowSimplifiedCollection> httpCall = showService.getShows("Bearer " + this.accessToken, showIds, options);

        try {
            logger.info("Executing HTTP call to fetch multiple shows.");
            logger.debug(String.format("Fetching following shows: %s with following values: %s.", showIds, options));
            logger.debug(String.format("%s / %s", httpCall.request().method(), httpCall.request().url().toString()));
            Response<ShowSimplifiedCollection> response = httpCall.execute();

            ResponseChecker.throwIfRequestHasNotBeenFulfilledCorrectly(response.errorBody());

            logger.info("Shows has been successfully fetched.");
            return response.body();
        } catch (IOException ex) {
            logger.error("HTTP request to fetch shows has failed.");
            throw new HttpRequestFailedException(ex.getMessage());
        }
    }
}
