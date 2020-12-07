package spotify.api.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import retrofit2.Call;
import retrofit2.Response;
import spotify.api.enums.HttpStatusCode;
import spotify.api.enums.QueryType;
import spotify.api.interfaces.SearchApi;
import spotify.exceptions.HttpRequestFailedException;
import spotify.factories.RetrofitHttpServiceFactory;
import spotify.models.search.SearchQueryResult;
import spotify.retrofit.services.SearchService;
import spotify.utils.LoggingUtil;
import spotify.utils.ResponseChecker;
import spotify.utils.ValidatorUtil;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SearchApiRetrofit implements SearchApi {
    private final Logger logger = LoggerFactory.getLogger(SearchApiRetrofit.class);
    private final String accessToken;
    private final SearchService searchService;

    public SearchApiRetrofit(final String accessToken) {
        this.accessToken = accessToken;
        this.searchService = RetrofitHttpServiceFactory.getSearchService();
    }

    @Override
    public SearchQueryResult searchItem(String query, List<QueryType> listOfQueryTypes, Map<String, String> options) {
        options = ValidatorUtil.optionsValueCheck(options);

        final String queryTypes = listOfQueryTypes.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));

        logger.trace("Constructing HTTP call to perform a query.");
        Call<SearchQueryResult> httpCall = searchService.searchItem("Bearer " + this.accessToken, query, queryTypes, options);

        try {
            logger.info("Executing HTTP call to to perform a query.");
            logger.debug("Performing the following query: {} on the following types: {}, with following values: {}.", query, queryTypes, options);
            LoggingUtil.logHttpCall(logger, httpCall);
            Response<SearchQueryResult> response = httpCall.execute();

            ResponseChecker.throwIfRequestHasNotBeenFulfilledCorrectly(response, HttpStatusCode.OK);

            logger.info("Request to perform a query has been successfully executed.");
            return response.body();
        } catch (IOException ex) {
            logger.error("HTTP request to perform a query has failed.");
            throw new HttpRequestFailedException(ex.getMessage());
        }
    }
}
