package spotify.api.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import retrofit2.Call;
import retrofit2.Response;
import spotify.api.enums.HttpStatusCode;
import spotify.api.interfaces.PlaylistApi;
import spotify.exceptions.HttpRequestFailedException;
import spotify.factories.RetrofitHttpServiceFactory;
import spotify.models.generic.Image;
import spotify.models.paging.Paging;
import spotify.models.playlists.PlaylistFull;
import spotify.models.playlists.PlaylistSimplified;
import spotify.models.playlists.PlaylistTrack;
import spotify.models.playlists.requests.AddItemPlaylistRequestBody;
import spotify.models.playlists.requests.CreatePlaylistRequestBody;
import spotify.retrofit.services.PlaylistService;
import spotify.utils.LoggingUtil;
import spotify.utils.ResponseChecker;
import spotify.utils.ValidatorUtil;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class PlaylistApiRetrofit implements PlaylistApi {
    private final Logger logger = LoggerFactory.getLogger(PlaylistApiRetrofit.class);
    private final String accessToken;
    private final PlaylistService playlistService;


    public PlaylistApiRetrofit(final String accessToken) {
        this.accessToken = accessToken;
        playlistService = RetrofitHttpServiceFactory.getPlaylistService();
    }

    @Override
    public Paging<PlaylistSimplified> getPlaylists(Map<String, String> options) {
        options = ValidatorUtil.optionsValueCheck(options);

        logger.trace("Constructing HTTP call to fetch current user's playlists.");
        Call<Paging<PlaylistSimplified>> httpCall = playlistService.getPlaylists("Bearer " + this.accessToken, options);

        try {
            logger.info("Executing HTTP call to fetch current user's playlists.");
            logger.debug(String.format("Fetching playlists with following parameter values: %s.", options));
            LoggingUtil.logHttpCall(logger, httpCall);
            Response<Paging<PlaylistSimplified>> response = httpCall.execute();

            ResponseChecker.throwIfRequestHasNotBeenFulfilledCorrectly(response, HttpStatusCode.OK);

            logger.info("Playlists have been successfully fetched");
            return response.body();
        } catch (IOException ex) {
            logger.error("HTTP request to fetch playlists has failed.");
            throw new HttpRequestFailedException(ex.getMessage());
        }
    }

    @Override
    public Paging<PlaylistSimplified> getUserPlaylists(String userId, Map<String, String> options) {
        options = ValidatorUtil.optionsValueCheck(options);

        logger.trace("Constructing HTTP call to fetch a user's playlists.");
        Call<Paging<PlaylistSimplified>> httpCall = playlistService.getUserPlaylists("Bearer " + this.accessToken, userId, options);

        try {
            logger.info("Executing HTTP call to fetch a user's playlists.");
            logger.debug(String.format("Fetching playlists from user %s with the following parameter values: %s.", userId, options));
            LoggingUtil.logHttpCall(logger, httpCall);
            Response<Paging<PlaylistSimplified>> response = httpCall.execute();

            ResponseChecker.throwIfRequestHasNotBeenFulfilledCorrectly(response, HttpStatusCode.OK);

            logger.info("Playlists have been successfully fetched");
            return response.body();
        } catch (IOException ex) {
            logger.error("HTTP request to fetch playlists has failed.");
            throw new HttpRequestFailedException(ex.getMessage());
        }
    }

    @Override
    public List<Image> getPlaylistCoverImages(String playlistId) {
        logger.trace("Constructing HTTP call to fetch a playlist cover images.");
        Call<List<Image>> httpCall = playlistService.getPlaylistCoverImages("Bearer " + this.accessToken, playlistId);

        try {
            logger.info("Executing HTTP call to fetch a playlist cover images.");
            logger.debug(String.format("Fetching playlist cover images with the playlist id: %s.", playlistId));
            LoggingUtil.logHttpCall(logger, httpCall);
            Response<List<Image>> response = httpCall.execute();

            ResponseChecker.throwIfRequestHasNotBeenFulfilledCorrectly(response, HttpStatusCode.OK);

            logger.info("Playlist cover images have been successfully fetched");
            return response.body();
        } catch (IOException ex) {
            logger.error("HTTP request to fetch playlist cover images has failed.");
            throw new HttpRequestFailedException(ex.getMessage());
        }
    }

    @Override
    public PlaylistFull getPlaylist(String playlistId, Map<String, String> options) {
        options = ValidatorUtil.optionsValueCheck(options);

        logger.trace("Constructing HTTP call to fetch a playlist.");
        Call<PlaylistFull> httpCall = playlistService.getPlaylist("Bearer " + this.accessToken, playlistId, options);

        try {
            logger.info("Executing HTTP call to fetch a playlist.");
            logger.debug(String.format("Fetching playlist with the playlist id: %s, with the following parameter values: %s.", playlistId, options));
            LoggingUtil.logHttpCall(logger, httpCall);
            Response<PlaylistFull> response = httpCall.execute();

            ResponseChecker.throwIfRequestHasNotBeenFulfilledCorrectly(response, HttpStatusCode.OK);

            logger.info("Playlist has been successfully fetched");
            return response.body();
        } catch (IOException ex) {
            logger.error("HTTP request to fetch playlist has failed.");
            throw new HttpRequestFailedException(ex.getMessage());
        }
    }

    @Override
    public Paging<PlaylistTrack> getPlaylistTracks(String playlistId, Map<String, String> options) {
        options = ValidatorUtil.optionsValueCheck(options);

        logger.trace("Constructing HTTP call to fetch tracks of a playlist.");
        Call<Paging<PlaylistTrack>> httpCall = playlistService.getPlaylistTracks("Bearer " + this.accessToken, playlistId, options);

        try {
            logger.info("Executing HTTP call to fetch tracks of a playlist.");
            logger.debug(String.format("Fetching tracks of a playlist with the playlist id: %s, with the following parameter values: %s.", playlistId, options));
            LoggingUtil.logHttpCall(logger, httpCall);
            Response<Paging<PlaylistTrack>> response = httpCall.execute();

            ResponseChecker.throwIfRequestHasNotBeenFulfilledCorrectly(response, HttpStatusCode.OK);

            logger.info("Tracks has been successfully fetched");
            return response.body();
        } catch (IOException ex) {
            logger.error("HTTP request to fetch tracks has failed.");
            throw new HttpRequestFailedException(ex.getMessage());
        }
    }

    @Override
    public void addItemToPlaylist(List<String> listOfObjectUris, String playlistId, int startPositionToInsert) {
        final AddItemPlaylistRequestBody requestBody = new AddItemPlaylistRequestBody(listOfObjectUris, startPositionToInsert);

        logger.trace("Constructing HTTP call to add items to a playlist.");
        Call<Void> httpCall = playlistService.addItemToPlaylist("Bearer " + this.accessToken, playlistId, requestBody);

        try {
            logger.info("Executing HTTP call to add items to a playlist.");
            logger.debug(String.format("Adding the following items to the playlist: %s from position %s.", playlistId, startPositionToInsert));
            LoggingUtil.logHttpCall(logger, httpCall);
            Response<Void> response = httpCall.execute();

            ResponseChecker.throwIfRequestHasNotBeenFulfilledCorrectly(response, HttpStatusCode.CREATED);

            logger.info("Items have been successfully added to the playlist");
        } catch (IOException ex) {
            logger.error("HTTP request to add items to the playlist has failed.");
            throw new HttpRequestFailedException(ex.getMessage());
        }
    }

    @Override
    public void createPlaylist(String userId, String playlistName, String description, boolean isPublic, boolean isCollaborative) {
        if (userId.isEmpty() || playlistName.isEmpty()) {
            final String errorMessage = "Required parameters are empty!";
            logger.error(errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }

        final CreatePlaylistRequestBody requestBody = new CreatePlaylistRequestBody(playlistName, description, isPublic, isCollaborative);

        logger.trace("Constructing HTTP call to create a playlist.");
        Call<Void> httpCall = playlistService.createPlaylist("Bearer " + this.accessToken, userId, requestBody);

        try {
            logger.info("Executing HTTP call to creat2e a playlist.");
            logger.debug(String.format(
                    "Creating a playlist with the name: %s and description: %s. The playlist is %s and %s.",
                    playlistName,
                    description,
                    isPublic ? "public" : "private",
                    isCollaborative ? "collaborative" : "not collaborative"));
            LoggingUtil.logHttpCall(logger, httpCall);
            Response<Void> response = httpCall.execute();

            ResponseChecker.throwIfRequestHasNotBeenFulfilledCorrectly(response, HttpStatusCode.CREATED);

            logger.info("Playlist has been successfully created.");
        } catch (IOException ex) {
            logger.error("HTTP request to create a playlist playlist has failed.");
            throw new HttpRequestFailedException(ex.getMessage());
        }
    }
}
