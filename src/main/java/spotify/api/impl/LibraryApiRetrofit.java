package spotify.api.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import retrofit2.Call;
import retrofit2.Response;
import spotify.api.interfaces.LibraryApi;
import spotify.exceptions.HttpRequestFailedException;
import spotify.factories.RetrofitHttpServiceFactory;
import spotify.retrofit.services.LibraryService;
import spotify.utils.LoggingUtil;
import spotify.utils.ResponseChecker;

import java.io.IOException;
import java.util.List;

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
            logger.debug(String.format("Checking albums with following album ids: %s.", albumIds));
            LoggingUtil.logHttpCall(logger, httpCall);
            Response<List<Boolean>> response = httpCall.execute();

            ResponseChecker.throwIfRequestHasNotBeenFulfilledCorrectly(response.errorBody());

            logger.info("Saved albums have been successfully checked");
            return response.body();
        } catch (IOException ex) {
            logger.error("HTTP request to check saved albums has failed.");
            throw new HttpRequestFailedException(ex.getMessage());
        }
    }
}
