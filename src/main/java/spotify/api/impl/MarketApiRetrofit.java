package spotify.api.impl;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import retrofit2.Call;
import retrofit2.Response;
import spotify.api.enums.HttpStatusCode;
import spotify.api.interfaces.MarketApi;
import spotify.exceptions.HttpRequestFailedException;
import spotify.factories.RetrofitHttpServiceFactory;
import spotify.models.markets.MarketFull;
import spotify.retrofit.services.MarketService;
import spotify.utils.LoggingUtil;
import spotify.utils.ResponseChecker;


public class MarketApiRetrofit implements MarketApi {

    private final Logger logger = LoggerFactory.getLogger(MarketApiRetrofit.class);
    private final String accessToken;
    private final MarketService marketService;


    public MarketApiRetrofit(final String accessToken) {
        this(accessToken, RetrofitHttpServiceFactory.getMarketService());
    }

    public MarketApiRetrofit(final String accessToken, final MarketService marketService) {
        this.accessToken = accessToken;
        this.marketService = marketService;
    }


    @Override
    public MarketFull getMarkets(){
        logger.trace("Constructing HTTP call to fetch markets.");
        Call<MarketFull> httpCall = marketService.getMarkets("Bearer " + this.accessToken);

        try {

            logger.info("Executing HTTP call to fetch markets");
            logger.debug("Fetching markets....");
            LoggingUtil.logHttpCall(logger, httpCall);
            Response<MarketFull> response = httpCall.execute();

            ResponseChecker.throwIfRequestHasNotBeenFulfilledCorrectly(response, HttpStatusCode.OK);

            logger.info("Markets fetched successfully.");
            return response.body();

        } catch (IOException ex) {
            logger.error("HTTP request to fetch requested markets has failed.");
            throw new HttpRequestFailedException(ex.getMessage());
        }
    }
}
