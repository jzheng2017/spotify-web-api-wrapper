package spotify.api.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import retrofit2.Call;
import retrofit2.Response;
import spotify.api.enums.HttpStatusCode;
import spotify.api.interfaces.LibraryApi;
import spotify.exceptions.HttpRequestFailedException;
import spotify.factories.RetrofitHttpServiceFactory;
import spotify.models.albums.SavedAlbumFull;
import spotify.models.paging.Paging;
import spotify.models.shows.SavedShowSimplified;
import spotify.models.tracks.SavedTrackFull;
import spotify.retrofit.services.LibraryService;
import spotify.utils.LoggingUtil;
import spotify.utils.ResponseChecker;
import spotify.utils.ValidatorUtil;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class LibraryApiRetrofit implements LibraryApi {
    private final Logger logger = LoggerFactory.getLogger(LibraryApiRetrofit.class);
    private final String accessToken;
    private final LibraryService libraryService;

    public LibraryApiRetrofit(final String accessToken) {
        this.accessToken = accessToken;
        this.libraryService = RetrofitHttpServiceFactory.getLibraryService();
    }

    @Override
    public List<Boolean> hasSavedAlbums(List<String> listOfAlbumIds) {
        String albumIds = String.join(",", listOfAlbumIds);

        logger.trace("Constructing HTTP call check if current user has saved the given albums ");
        Call<List<Boolean>> httpCall = libraryService.hasSavedAlbums("Bearer " + this.accessToken, albumIds);

        try {
            logger.info("Executing HTTP call to check saved albums.");
            logger.debug("Checking albums with following album ids: {}.", albumIds);
            LoggingUtil.logHttpCall(logger, httpCall);
            Response<List<Boolean>> response = httpCall.execute();

            ResponseChecker.throwIfRequestHasNotBeenFulfilledCorrectly(response, HttpStatusCode.OK);

            logger.info("Saved albums have been successfully checked");
            return response.body();
        } catch (IOException ex) {
            logger.error("HTTP request to check saved albums has failed.");
            throw new HttpRequestFailedException(ex.getMessage());
        }
    }

    @Override
    public List<Boolean> hasSavedShows(List<String> listOfShowIds) {
        String showIds = String.join(",", listOfShowIds);

        logger.trace("Constructing HTTP call check if current user has saved the given shows ");
        Call<List<Boolean>> httpCall = libraryService.hasSavedShows("Bearer " + this.accessToken, showIds);

        try {
            logger.info("Executing HTTP call to check saved shows.");
            logger.debug("Checking shows with following show ids: {}.", showIds);
            LoggingUtil.logHttpCall(logger, httpCall);
            Response<List<Boolean>> response = httpCall.execute();

            ResponseChecker.throwIfRequestHasNotBeenFulfilledCorrectly(response, HttpStatusCode.OK);

            logger.info("Saved shows have been successfully checked");
            return response.body();
        } catch (IOException ex) {
            logger.error("HTTP request to check saved shows has failed.");
            throw new HttpRequestFailedException(ex.getMessage());
        }
    }

    @Override
    public List<Boolean> hasSavedTracks(List<String> listOfTrackIds) {
        String trackIds = String.join(",", listOfTrackIds);

        logger.trace("Constructing HTTP call check if current user has saved the given tracks ");
        Call<List<Boolean>> httpCall = libraryService.hasSavedTracks("Bearer " + this.accessToken, trackIds);

        try {
            logger.info("Executing HTTP call to check saved tracks.");
            logger.debug("Checking tracks with following track ids: {}.", trackIds);
            LoggingUtil.logHttpCall(logger, httpCall);
            Response<List<Boolean>> response = httpCall.execute();

            ResponseChecker.throwIfRequestHasNotBeenFulfilledCorrectly(response, HttpStatusCode.OK);

            logger.info("Saved tracks have been successfully checked");
            return response.body();
        } catch (IOException ex) {
            logger.error("HTTP request to check saved tracks has failed.");
            throw new HttpRequestFailedException(ex.getMessage());
        }
    }

    @Override
    public Paging<SavedAlbumFull> getSavedAlbums(Map<String, String> options) {
        options = ValidatorUtil.optionsValueCheck(options);

        logger.trace("Constructing HTTP call fetch current user saved albums");
        Call<Paging<SavedAlbumFull>> httpCall = libraryService.getSavedAlbums("Bearer " + this.accessToken, options);

        try {
            logger.info("Executing HTTP call to fetch current user saved albums");
            logger.debug("Fetching current user saved albums with the following values: {}.", options);
            LoggingUtil.logHttpCall(logger, httpCall);
            Response<Paging<SavedAlbumFull>> response = httpCall.execute();

            ResponseChecker.throwIfRequestHasNotBeenFulfilledCorrectly(response, HttpStatusCode.OK);

            logger.info("Saved albums have been successfully fetched");
            return response.body();
        } catch (IOException ex) {
            logger.error("HTTP request to fetch saved albums has failed.");
            throw new HttpRequestFailedException(ex.getMessage());
        }
    }

    @Override
    public Paging<SavedShowSimplified> getSavedShows(Map<String, String> options) {
        options = ValidatorUtil.optionsValueCheck(options);

        logger.trace("Constructing HTTP call fetch current user saved shows");
        Call<Paging<SavedShowSimplified>> httpCall = libraryService.getSavedShows("Bearer " + this.accessToken, options);

        try {
            logger.info("Executing HTTP call to fetch current user saved shows");
            logger.debug("Fetching current user saved shows with the following values: {}.", options);
            LoggingUtil.logHttpCall(logger, httpCall);
            Response<Paging<SavedShowSimplified>> response = httpCall.execute();

            ResponseChecker.throwIfRequestHasNotBeenFulfilledCorrectly(response, HttpStatusCode.OK);

            logger.info("Saved shows have been successfully fetched");
            return response.body();
        } catch (IOException ex) {
            logger.error("HTTP request to fetch saved shows has failed.");
            throw new HttpRequestFailedException(ex.getMessage());
        }
    }

    @Override
    public Paging<SavedTrackFull> getSavedTracks(Map<String, String> options) {
        options = ValidatorUtil.optionsValueCheck(options);

        logger.trace("Constructing HTTP call fetch current user saved tracks");
        Call<Paging<SavedTrackFull>> httpCall = libraryService.getSavedTracks("Bearer " + this.accessToken, options);

        try {
            logger.info("Executing HTTP call to fetch current user saved tracks");
            logger.debug("Fetching current user saved tracks with the following values: {}.", options);
            LoggingUtil.logHttpCall(logger, httpCall);
            Response<Paging<SavedTrackFull>> response = httpCall.execute();

            ResponseChecker.throwIfRequestHasNotBeenFulfilledCorrectly(response, HttpStatusCode.OK);

            logger.info("Saved tracks have been successfully fetched");
            return response.body();
        } catch (IOException ex) {
            logger.error("HTTP request to fetch saved tracks has failed.");
            throw new HttpRequestFailedException(ex.getMessage());
        }
    }


    @Override
    public void saveAlbums(List<String> listOfAlbumIds) {
        String albumIds = String.join(",", listOfAlbumIds);

        logger.trace("Constructing HTTP call to save the given albums");
        Call<Void> httpCall = libraryService.saveAlbums("Bearer " + this.accessToken, albumIds);

        try {
            logger.info("Executing HTTP call to save the given albums.");
            logger.debug("Saving albums with following album ids: {}.", albumIds);
            LoggingUtil.logHttpCall(logger, httpCall);
            Response<Void> response = httpCall.execute();

            ResponseChecker.throwIfRequestHasNotBeenFulfilledCorrectly(response, HttpStatusCode.CREATED);

            logger.info("Albums have been successfully saved");
        } catch (IOException ex) {
            logger.error("HTTP request to save albums has failed.");
            throw new HttpRequestFailedException(ex.getMessage());
        }
    }

    @Override
    public void saveShows(List<String> listOfShowIds) {
        String showIds = String.join(",", listOfShowIds);

        logger.trace("Constructing HTTP call to save the given shows");
        Call<Void> httpCall = libraryService.saveShows("Bearer " + this.accessToken, showIds);

        try {
            logger.info("Executing HTTP call to save the given shows.");
            logger.debug("Saving shows with following show ids: {}.", showIds);
            LoggingUtil.logHttpCall(logger, httpCall);
            Response<Void> response = httpCall.execute();

            ResponseChecker.throwIfRequestHasNotBeenFulfilledCorrectly(response, HttpStatusCode.OK);

            logger.info("Shows have been successfully saved");
        } catch (IOException ex) {
            logger.error("HTTP request to save shows has failed.");
            throw new HttpRequestFailedException(ex.getMessage());
        }
    }

    @Override
    public void saveTracks(List<String> listOfTrackIds) {
        String trackIds = String.join(",", listOfTrackIds);

        logger.trace("Constructing HTTP call to save the given tracks");
        Call<Void> httpCall = libraryService.saveTracks("Bearer " + this.accessToken, trackIds);

        try {
            logger.info("Executing HTTP call to save the given tracks.");
            logger.debug("Saving tracks with following track ids: {}.", trackIds);
            LoggingUtil.logHttpCall(logger, httpCall);
            Response<Void> response = httpCall.execute();

            ResponseChecker.throwIfRequestHasNotBeenFulfilledCorrectly(response, HttpStatusCode.OK);

            logger.info("Tracks have been successfully saved");
        } catch (IOException ex) {
            logger.error("HTTP request to save tracks has failed.");
            throw new HttpRequestFailedException(ex.getMessage());
        }
    }

    @Override
    public void deleteAlbums(List<String> listOfAlbumIds) {
        String albumIds = String.join(",", listOfAlbumIds);

        logger.trace("Constructing HTTP call to delete the saved albums");
        Call<Void> httpCall = libraryService.deleteAlbums("Bearer " + this.accessToken, albumIds);

        try {
            logger.info("Executing HTTP call to delete the saved albums.");
            logger.debug("Deleting saved albums with following album ids: {}.", albumIds);
            LoggingUtil.logHttpCall(logger, httpCall);
            Response<Void> response = httpCall.execute();

            ResponseChecker.throwIfRequestHasNotBeenFulfilledCorrectly(response, HttpStatusCode.OK);

            logger.info("Saved albums have been successfully deleted");
        } catch (IOException ex) {
            logger.error("HTTP request to delete saved albums has failed.");
            throw new HttpRequestFailedException(ex.getMessage());
        }
    }

    @Override
    public void deleteShows(List<String> listOfShowIds, Map<String, String> options) {
        options = ValidatorUtil.optionsValueCheck(options);

        String showIds = String.join(",", listOfShowIds);

        logger.trace("Constructing HTTP call to delete the saved shows");
        Call<Void> httpCall = libraryService.deleteShows("Bearer " + this.accessToken, showIds, options);

        try {
            logger.info("Executing HTTP call to delete the saved shows.");
            logger.debug("Deleting saved shows with following show ids: {} and parameter values: {}.", showIds, options);
            LoggingUtil.logHttpCall(logger, httpCall);
            Response<Void> response = httpCall.execute();

            ResponseChecker.throwIfRequestHasNotBeenFulfilledCorrectly(response, HttpStatusCode.OK);

            logger.info("Saved shows have been successfully deleted");
        } catch (IOException ex) {
            logger.error("HTTP request to delete saved shows has failed.");
            throw new HttpRequestFailedException(ex.getMessage());
        }
    }

    @Override
    public void deleteTracks(List<String> listOfTrackIds) {
        String trackIds = String.join(",", listOfTrackIds);

        logger.trace("Constructing HTTP call to delete the saved tracks");
        Call<Void> httpCall = libraryService.deleteTracks("Bearer " + this.accessToken, trackIds);

        try {
            logger.info("Executing HTTP call to delete the saved tracks.");
            logger.debug("Deleting saved tracks with following track ids: {}.", trackIds);
            LoggingUtil.logHttpCall(logger, httpCall);
            Response<Void> response = httpCall.execute();

            ResponseChecker.throwIfRequestHasNotBeenFulfilledCorrectly(response, HttpStatusCode.OK);

            logger.info("Saved tracks have been successfully deleted");
        } catch (IOException ex) {
            logger.error("HTTP request to delete saved tracks has failed.");
            throw new HttpRequestFailedException(ex.getMessage());
        }
    }
}
