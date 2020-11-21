package spotify.api.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import spotify.api.interfaces.BrowseApi;
import spotify.config.ApiUrl;
import spotify.exceptions.HttpRequestFailedException;
import spotify.exceptions.ResponseChecker;
import spotify.factories.RetrofitClientFactory;
import spotify.models.categories.CategoryFull;
import spotify.retrofit.services.BrowseService;
import spotify.utils.ValidatorUtil;

import java.io.IOException;

public class BrowseApiRetrofit implements BrowseApi {
    private final Logger logger = LoggerFactory.getLogger(UserApiRetrofit.class);
    private final String accessToken;
    private BrowseService browseService;

    public BrowseApiRetrofit(String accessToken) {
        this.accessToken = accessToken;
        setup();
    }

    @Override
    public CategoryFull getCategory(String categoryId, String country, String locale) {
        country = ValidatorUtil.emptyValueCheck(country);
        locale = ValidatorUtil.emptyValueCheck(locale);

        logger.trace("Constructing HTTP call to fetch category.");
        Call<CategoryFull> httpCall = browseService.getCategory("Bearer " + this.accessToken, categoryId, country, locale);

        try {
            logger.info("Executing HTTP call to fetch category.");
            logger.debug(String.format("Fetching category %s with country code %s with locale %s", categoryId, country, locale));
            logger.debug(String.format("%s / %s", httpCall.request().method(), httpCall.request().url().toString()));
            Response<CategoryFull> response = httpCall.execute();

            ResponseChecker.throwIfRequestHasNotBeenFulfilledCorrectly(response.errorBody());

            logger.info("Category has been successfully fetched.");
            return response.body();
        } catch (IOException ex) {
            logger.error("HTTP request to fetch category has failed.");
            throw new HttpRequestFailedException(ex.getMessage());
        }
    }

    private void setup() {
        logger.trace("Requesting Retrofit HTTP client.");
        Retrofit httpClient = RetrofitClientFactory.getRetrofitClient(ApiUrl.API_URL_HTTPS + ApiUrl.VERSION);

        browseService = httpClient.create(BrowseService.class);
    }
}
