package spotify.api.impl;

import okhttp3.MediaType;
import okhttp3.RequestBody;
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
import spotify.models.playlists.Snapshot;
import spotify.models.playlists.requests.*;
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
        this(accessToken, RetrofitHttpServiceFactory.getPlaylistService());
    }

    public PlaylistApiRetrofit(final String accessToken, final PlaylistService playlistService) {
        this.accessToken = accessToken;
        this.playlistService = playlistService;
    }

    @Override
    public Paging<PlaylistSimplified> getPlaylists(Map<String, String> options) {
        options = ValidatorUtil.optionsValueCheck(options);

        logger.trace("Constructing HTTP call to fetch current user's playlists.");
        Call<Paging<PlaylistSimplified>> httpCall = playlistService.getPlaylists("Bearer " + this.accessToken, options);

        try {
            logger.info("Executing HTTP call to fetch current user's playlists.");
            logger.debug("Fetching playlists with following parameter values: {}.", options);
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
            logger.debug("Fetching playlists from user {} with the following parameter values: {}.", userId, options);
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
            logger.debug("Fetching playlist cover images with the playlist id: {}.", playlistId);
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
            logger.debug("Fetching playlist with the playlist id: {}, with the following parameter values: {}.", playlistId, options);
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
            logger.debug("Fetching tracks of a playlist with the playlist id: {}, with the following parameter values: {}.", playlistId, options);
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
            logger.debug("Adding the following items to the playlist: {} from position {}.", playlistId, startPositionToInsert);
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
    public void createPlaylist(String userId, CreateUpdatePlaylistRequestBody requestBody) {
        if (userId == null || userId.isEmpty()) {
            final String errorMessage = "userId can not be empty!";
            logger.error(errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }

        if (requestBody.getName() == null || requestBody.getName().isEmpty()) {
            final String errorMessage = "requestBody can not be empty!";
            logger.error(errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }

        logger.trace("Constructing HTTP call to create a playlist.");
        Call<Void> httpCall = playlistService.createPlaylist("Bearer " + this.accessToken, userId, requestBody);

        try {
            logger.info("Executing HTTP call to create a playlist.");
            logger.debug(
                    "Creating a playlist with the name: {} and description: {}. The playlist is {} and {}.",
                    requestBody.getName(),
                    requestBody.getDescription(),
                    requestBody.isPublic() ? "public" : "private",
                    requestBody.isCollaborative() ? "collaborative" : "not collaborative");
            LoggingUtil.logHttpCall(logger, httpCall);
            Response<Void> response = httpCall.execute();

            ResponseChecker.throwIfRequestHasNotBeenFulfilledCorrectly(response, HttpStatusCode.CREATED);

            logger.info("Playlist has been successfully created.");
        } catch (IOException ex) {
            logger.error("HTTP request to create a playlist has failed.");
            throw new HttpRequestFailedException(ex.getMessage());
        }
    }

    @Override
    public void updatePlaylist(String playlistId, CreateUpdatePlaylistRequestBody requestBody) {
        if (playlistId == null || playlistId.isEmpty()) {
            final String errorMessage = "Playlist id can not be empty!";
            logger.error(errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }

        logger.trace("Constructing HTTP call to update a playlist.");
        Call<Void> httpCall = playlistService.updatePlaylist("Bearer " + this.accessToken, playlistId, requestBody);

        try {
            logger.info("Executing HTTP call to update a playlist.");
            logger.debug(
                    "Updating playlist {} with the name: {} and description: {}. The playlist is {} and {}.",
                    playlistId,
                    requestBody.getName(),
                    requestBody.getDescription(),
                    requestBody.isPublic() ? "public" : "private",
                    requestBody.isCollaborative() ? "collaborative" : "not collaborative");
            LoggingUtil.logHttpCall(logger, httpCall);
            Response<Void> response = httpCall.execute();

            ResponseChecker.throwIfRequestHasNotBeenFulfilledCorrectly(response, HttpStatusCode.OK);

            logger.info("Playlist has been successfully updated.");
        } catch (IOException ex) {
            logger.error("HTTP request to update a playlist has failed.");
            throw new HttpRequestFailedException(ex.getMessage());
        }
    }

    @Override
    public Snapshot reorderPlaylistItems(String playlistId, ReorderPlaylistItemsRequestBody requestBody) {
        validateParametersReorderFunction(playlistId, requestBody);

        logger.trace("Constructing HTTP call to reorder items of a playlist.");
        Call<Snapshot> httpCall = playlistService.reorderPlaylistItems("Bearer " + this.accessToken, playlistId, requestBody);

        try {
            logger.info("Executing HTTP call to reorder items of a playlist.");
            logger.debug(
                    "Reordering items of playlist {} with snapshot id {} from start position {} with range of {} length and insert it in position {} ",
                    playlistId, requestBody.getSnapshotId(), requestBody.getRangeStart(), requestBody.getRangeLength(), requestBody.getInsertBefore()
            );
            LoggingUtil.logHttpCall(logger, httpCall);
            Response<Snapshot> response = httpCall.execute();

            ResponseChecker.throwIfRequestHasNotBeenFulfilledCorrectly(response, HttpStatusCode.OK);

            logger.info("Playlist has been successfully reordered.");
            return response.body();
        } catch (IOException ex) {
            logger.error("HTTP request to reorder a playlist has failed.");
            throw new HttpRequestFailedException(ex.getMessage());
        }
    }

    @Override
    public void replacePlaylistItems(String playlistId, List<String> listOfItemUris) {
        if (playlistId == null || playlistId.isEmpty()) {
            final String errorMessage = "Playlist id is empty!";
            logger.error(errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }

        logger.trace("Constructing HTTP call to replace items of a playlist.");
        Call<Void> httpCall = playlistService.replacePlaylistItems("Bearer " + this.accessToken, playlistId, new ReplacePlaylistItemsRequestBody(listOfItemUris));

        try {
            logger.info("Executing HTTP call to replace items of a playlist.");
            logger.debug("Replacing items of playlist {} with the following items: {}", playlistId, listOfItemUris);
            LoggingUtil.logHttpCall(logger, httpCall);
            Response<Void> response = httpCall.execute();

            ResponseChecker.throwIfRequestHasNotBeenFulfilledCorrectly(response, HttpStatusCode.CREATED);

            logger.info("Playlist has been successfully replaced.");
        } catch (IOException ex) {
            logger.error("HTTP request to replace a playlist has failed.");
            throw new HttpRequestFailedException(ex.getMessage());
        }
    }

    @Override
    public void uploadCoverImageToPlaylist(String playlistId, String base64EncodedJpegImage) {
        if (playlistId == null || playlistId.isEmpty()) {
            final String errorMessage = "playlistId can not be empty!";
            logger.error(errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }

        if (base64EncodedJpegImage == null || base64EncodedJpegImage.isEmpty()) {
            final String errorMessage = "Image can not be empty!";
            logger.error(errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }

        //this is done because Retrofit converts @Body automatically to json, by passing in a RequestBody with media type text/plain it will not be converted to json
        logger.trace("Creating OkHttp3 request body with text/plain media type");
        final RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), base64EncodedJpegImage);

        logger.trace("Constructing HTTP call to upload a cover image to a playlist.");
        Call<Void> httpCall = playlistService.uploadCoverImageToPlaylist("Bearer " + this.accessToken, playlistId, requestBody);

        try {
            logger.info("Executing HTTP call to upload a cover image to a playlist.");
            logger.debug("Uploading cover image to playlist {}", playlistId);
            logger.debug("Base64 encoded jpeg image data: {}", base64EncodedJpegImage);
            LoggingUtil.logHttpCall(logger, httpCall);
            Response<Void> response = httpCall.execute();

            ResponseChecker.throwIfRequestHasNotBeenFulfilledCorrectly(response, HttpStatusCode.ACCEPTED);

            logger.info("Cover image has been accepted by Spotify");
        } catch (IOException ex) {
            logger.error("HTTP request to upload cover image.");
            throw new HttpRequestFailedException(ex.getMessage());
        }
    }

    @Override
    public Snapshot deleteItemsFromPlaylist(String playlistId, DeleteItemsPlaylistRequestBody items) {
        if (playlistId == null || playlistId.isEmpty()) {
            final String errorMessage = "Playlist id is empty!";
            logger.error(errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }

        if (items.getSnapshotId() != null && items.getSnapshotId().isEmpty()) {
            logger.warn("An empty snapshot id was passed in. The snapshot id has now been set to NULL.");
            items.setSnapshotId(null);
        }

        logger.trace("Constructing HTTP call to remove items from a playlist.");
        Call<Snapshot> httpCall = playlistService.deleteItemsFromPlaylist("Bearer " + this.accessToken, playlistId, items);

        try {
            logger.info("Executing HTTP call to remove items from a playlist.");
            logger.debug("Removing items from playlist {} with snapshot id {}", playlistId, items.getSnapshotId());
            logger.debug("Removing the following items {}", items);
            LoggingUtil.logHttpCall(logger, httpCall);
            Response<Snapshot> response = httpCall.execute();

            ResponseChecker.throwIfRequestHasNotBeenFulfilledCorrectly(response, HttpStatusCode.OK);

            logger.info("Items have been successfully removed from the playlist");
            return response.body();
        } catch (IOException ex) {
            logger.error("HTTP request to remove items from playlist has failed");
            throw new HttpRequestFailedException(ex.getMessage());
        }
    }

    private void validateParametersReorderFunction(String playlistId, ReorderPlaylistItemsRequestBody requestBody) {
        if (playlistId == null || playlistId.isEmpty()) {
            final String errorMessage = "Playlist id can not be empty!";
            logger.error(errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }

        if (requestBody.getSnapshotId() != null && requestBody.getSnapshotId().isEmpty()) {
            logger.warn("An empty snapshot id was passed in. The snapshot id has now been set to NULL.");
            requestBody.setSnapshotId(null);
        }
    }
}
