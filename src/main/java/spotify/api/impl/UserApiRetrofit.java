package spotify.api.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import spotify.api.interfaces.UserApi;
import spotify.config.ApiUrl;
import spotify.exceptions.HttpRequestFailedException;
import spotify.exceptions.ResponseChecker;
import spotify.factories.RetrofitClientFactory;
import spotify.models.users.User;
import spotify.retrofit.services.UserService;

import java.io.IOException;

public class UserApiRetrofit implements UserApi {
    private final Logger logger = LoggerFactory.getLogger(UserApiRetrofit.class);
    private final String accessToken;
    private UserService userService;

    public UserApiRetrofit(String accessToken) {
        this.accessToken = accessToken;
        setup();
    }

    @Override
    public User getCurrentUser() {
        logger.trace("Constructing HTTP call to fetch current user.");
        Call<User> httpCall = userService.getCurrentUser("Bearer " + this.accessToken);

        try {
            logger.info("Executing HTTP call to fetch current user.");
            logger.debug(String.format("%s / %s", httpCall.request().method(), httpCall.request().url().toString()));
            Response<User> response = httpCall.execute();

            ResponseChecker.throwIfRequestHasNotBeenFulfilledCorrectly(response.errorBody());

            logger.info("Current user has been successfully fetched.");
            return response.body();
        } catch (IOException ex) {
            logger.error("HTTP request to fetch current user has failed.");
            throw new HttpRequestFailedException(ex.getMessage());
        }
    }

    @Override
    public User getUser(String userId) {
        logger.trace("Constructing HTTP call to fetch user.");
        Call<User> httpCall = userService.getUser("Bearer " + this.accessToken, userId);

        try {
            logger.info(String.format("Executing HTTP call to fetch user with id %s.", userId));
            logger.debug(String.format("%s / %s", httpCall.request().method(), httpCall.request().url().toString()));
            Response<User> response = httpCall.execute();

            ResponseChecker.throwIfRequestHasNotBeenFulfilledCorrectly(response.errorBody());

            logger.info("Requested user has been successfully fetched.");
            return response.body();
        } catch (IOException ex) {
            logger.error("HTTP request to fetch requested user has failed.");
            throw new HttpRequestFailedException(ex.getMessage());
        }
    }

    private void setup() {
        logger.trace("Requesting Retrofit HTTP client.");
        Retrofit httpClient = RetrofitClientFactory.getRetrofitClient(ApiUrl.API_URL_HTTPS + ApiUrl.VERSION);

        userService = httpClient.create(UserService.class);
    }
}
