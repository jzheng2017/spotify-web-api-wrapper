package spotify.api.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import retrofit2.Call;
import retrofit2.Response;
import spotify.api.interfaces.UserApi;
import spotify.exceptions.HttpRequestFailedException;
import spotify.factories.RetrofitHttpServiceFactory;
import spotify.models.users.User;
import spotify.retrofit.services.UserService;
import spotify.utils.LoggingUtil;
import spotify.utils.ResponseChecker;

import java.io.IOException;

public class UserApiRetrofit implements UserApi {
    private final Logger logger = LoggerFactory.getLogger(UserApiRetrofit.class);
    private final String accessToken;
    private UserService userService;

    public UserApiRetrofit(String accessToken) {
        this.accessToken = accessToken;
        this.userService = RetrofitHttpServiceFactory.getUserService();
    }

    @Override
    public User getCurrentUser() {
        logger.trace("Constructing HTTP call to fetch current user.");
        Call<User> httpCall = userService.getCurrentUser("Bearer " + this.accessToken);

        try {
            logger.info("Executing HTTP call to fetch current user.");
            LoggingUtil.logHttpCall(logger, httpCall);
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
            LoggingUtil.logHttpCall(logger, httpCall);
            Response<User> response = httpCall.execute();

            ResponseChecker.throwIfRequestHasNotBeenFulfilledCorrectly(response.errorBody());

            logger.info("Requested user has been successfully fetched.");
            return response.body();
        } catch (IOException ex) {
            logger.error("HTTP request to fetch requested user has failed.");
            throw new HttpRequestFailedException(ex.getMessage());
        }
    }
}
