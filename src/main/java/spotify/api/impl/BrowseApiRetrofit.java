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
import spotify.models.categories.CategoryFullPaging;
import spotify.models.playlists.FeaturedPlaylistCollection;
import spotify.models.playlists.PlaylistSimplifiedPaging;
import spotify.retrofit.services.BrowseService;
import spotify.utils.ValidatorUtil;

import java.io.IOException;
import java.util.Map;

public class BrowseApiRetrofit implements BrowseApi {
    private final Logger logger = LoggerFactory.getLogger(UserApiRetrofit.class);
    private final String accessToken;
    private BrowseService browseService;

    public BrowseApiRetrofit(String accessToken) {
        this.accessToken = accessToken;
        setup();
    }

    @Override
    public CategoryFull getCategory(String categoryId, Map<String, String> options) {
        options = ValidatorUtil.optionsValueCheck(options);

        logger.trace("Constructing HTTP call to fetch category.");
        Call<CategoryFull> httpCall = browseService.getCategory("Bearer " + this.accessToken, categoryId, options);

        try {
            logger.info("Executing HTTP call to fetch category.");
            logger.debug(String.format("Fetching category %s with following values: %s.", categoryId, options));
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

    @Override
    public PlaylistSimplifiedPaging getCategoryPlaylists(String categoryId, Map<String, String> options) {
        options = ValidatorUtil.optionsValueCheck(options);

        logger.trace("Constructing HTTP call to fetch category playlists.");
        Call<PlaylistSimplifiedPaging> httpCall = browseService.getCategoryPlaylists("Bearer " + this.accessToken, categoryId, options);

        try {
            logger.info("Executing HTTP call to fetch category playlists.");
            logger.debug(String.format("Fetching category %s playlists with following values: %s.", categoryId, options));
            logger.debug(String.format("%s / %s", httpCall.request().method(), httpCall.request().url().toString()));
            Response<PlaylistSimplifiedPaging> response = httpCall.execute();

            ResponseChecker.throwIfRequestHasNotBeenFulfilledCorrectly(response.errorBody());

            logger.info("Category playlists have been successfully fetched.");
            return response.body();
        } catch (IOException ex) {
            logger.error("HTTP request to fetch category playlists has failed.");
            throw new HttpRequestFailedException(ex.getMessage());
        }
    }

    @Override
    public CategoryFullPaging getCategories(Map<String, String> options) {
        options = ValidatorUtil.optionsValueCheck(options);

        logger.trace("Constructing HTTP call to fetch categories.");
        Call<CategoryFullPaging> httpCall = browseService.getCategories("Bearer " + this.accessToken, options);

        try {
            logger.info("Executing HTTP call to fetch categories.");
            logger.debug(String.format("Fetching categories with following values: %s.", options));
            logger.debug(String.format("%s / %s", httpCall.request().method(), httpCall.request().url().toString()));
            Response<CategoryFullPaging> response = httpCall.execute();

            ResponseChecker.throwIfRequestHasNotBeenFulfilledCorrectly(response.errorBody());

            logger.info("Categories have been successfully fetched.");
            return response.body();
        } catch (IOException ex) {
            logger.error("HTTP request to fetch categories has failed.");
            throw new HttpRequestFailedException(ex.getMessage());
        }
    }

    @Override
    public FeaturedPlaylistCollection getFeaturedPlaylists(Map<String, String> options) {
        options = ValidatorUtil.optionsValueCheck(options);

        logger.trace("Constructing HTTP call to fetch featured playlists.");
        Call<FeaturedPlaylistCollection> httpCall = browseService.getFeaturedPlaylists("Bearer " + this.accessToken, options);

        try {
            logger.info("Executing HTTP call to fetch featured playlists.");
            logger.debug(String.format("Fetching featured playlists with following values: %s", options));
            logger.debug(String.format("%s / %s", httpCall.request().method(), httpCall.request().url().toString()));
            Response<FeaturedPlaylistCollection> response = httpCall.execute();

            ResponseChecker.throwIfRequestHasNotBeenFulfilledCorrectly(response.errorBody());

            logger.info("Featured playlists have been successfully fetched.");
            return response.body();
        } catch (IOException ex) {
            logger.error("HTTP request to fetch featured playlists has failed.");
            throw new HttpRequestFailedException(ex.getMessage());
        }
    }

    private void setup() {
        logger.trace("Requesting Retrofit HTTP client.");
        Retrofit httpClient = RetrofitClientFactory.getRetrofitClient(ApiUrl.API_URL_HTTPS + ApiUrl.VERSION);

        browseService = httpClient.create(BrowseService.class);
    }
}
