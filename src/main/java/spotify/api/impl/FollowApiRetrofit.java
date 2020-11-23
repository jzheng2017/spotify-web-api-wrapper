package spotify.api.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import retrofit2.Call;
import retrofit2.Response;
import spotify.api.enums.EntityType;
import spotify.api.interfaces.FollowApi;
import spotify.exceptions.HttpRequestFailedException;
import spotify.factories.RetrofitHttpServiceFactory;
import spotify.retrofit.services.FollowService;
import spotify.utils.LoggingUtil;
import spotify.utils.ResponseChecker;

import java.io.IOException;
import java.util.List;

public class FollowApiRetrofit implements FollowApi {
    private final Logger logger = LoggerFactory.getLogger(EpisodeApiRetrofit.class);
    private final String accessToken;
    private final FollowService followService;

    public FollowApiRetrofit(final String accessToken) {
        this.accessToken = accessToken;
        this.followService = RetrofitHttpServiceFactory.getFollowService();
    }

    @Override
    public List<Boolean> isFollowing(EntityType entityType, List<String> listOfEntityIds) {
        String entityIds = String.join(",", listOfEntityIds);

        logger.trace("Constructing HTTP call to check if user follows the provided entities.");
        Call<List<Boolean>> httpCall = followService.isFollowing("Bearer " + this.accessToken, entityType, entityIds);

        try {
            logger.info("Executing HTTP call to check if user follows the provided entities.");
            logger.debug(String.format("Fetching %s following list with following entity ids: %s.", entityType, entityIds));
            LoggingUtil.logHttpCall(logger, httpCall);
            Response<List<Boolean>> response = httpCall.execute();

            ResponseChecker.throwIfRequestHasNotBeenFulfilledCorrectly(response.errorBody());

            logger.info("Following list has been successfully fetched.");
            return response.body();
        } catch (IOException ex) {
            logger.error("HTTP request to fetch following list has failed.");
            throw new HttpRequestFailedException(ex.getMessage());
        }
    }

    @Override
    public List<Boolean> isFollowingPlaylist(String playlistId, List<String> listOfUserIds) {
        String userIds = String.join(",", listOfUserIds);

        logger.trace("Constructing HTTP call to check if users are following the playlist.");
        Call<List<Boolean>> httpCall = followService.isFollowingPlaylist("Bearer " + this.accessToken, playlistId, userIds);

        try {
            logger.info("Executing HTTP call to check if users are following the playlist.");
            logger.debug(String.format("Fetching %s playlist following list with following user ids: %s.", playlistId, userIds));
            LoggingUtil.logHttpCall(logger, httpCall);
            Response<List<Boolean>> response = httpCall.execute();

            ResponseChecker.throwIfRequestHasNotBeenFulfilledCorrectly(response.errorBody());

            logger.info("Following list has been successfully fetched.");
            return response.body();
        } catch (IOException ex) {
            logger.error("HTTP request to fetch following list has failed.");
            throw new HttpRequestFailedException(ex.getMessage());
        }
    }
}
