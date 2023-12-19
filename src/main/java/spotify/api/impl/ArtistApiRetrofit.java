package spotify.api.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import retrofit2.Call;
import retrofit2.Response;
import spotify.api.enums.AlbumType;
import spotify.api.enums.HttpStatusCode;
import spotify.api.interfaces.ArtistApi;
import spotify.exceptions.HttpRequestFailedException;
import spotify.factories.RetrofitHttpServiceFactory;
import spotify.models.albums.AlbumSimplified;
import spotify.models.artists.ArtistFull;
import spotify.models.artists.ArtistFullCollection;
import spotify.models.paging.Paging;
import spotify.models.tracks.TrackFullCollection;
import spotify.retrofit.services.ArtistService;
import spotify.utils.LoggingUtil;
import spotify.utils.ResponseChecker;
import spotify.utils.ValidatorUtil;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ArtistApiRetrofit implements ArtistApi {
    private final Logger logger = LoggerFactory.getLogger(ArtistApiRetrofit.class);
    private final String accessToken;
    private final ArtistService artistService;

    public ArtistApiRetrofit(final String accessToken) {
        this(accessToken, RetrofitHttpServiceFactory.getArtistService());
    }

    public ArtistApiRetrofit(final String accessToken, final ArtistService artistService) {
        this.accessToken = accessToken;
        this.artistService = artistService;
    }

    @Override
    public ArtistFull getArtist(String artistId) {
        logger.trace("Constructing HTTP call to fetch an artist.");
        Call<ArtistFull> httpCall = artistService.getArtist("Bearer " + this.accessToken, artistId);

        try {
            logger.info("Executing HTTP call to fetch an artist.");
            LoggingUtil.logHttpCall(logger, httpCall);
            Response<ArtistFull> response = httpCall.execute();

            ResponseChecker.throwIfRequestHasNotBeenFulfilledCorrectly(response, HttpStatusCode.OK);

            logger.info("Artist has been successfully fetched.");
            return response.body();
        } catch (IOException e) {
            logger.error("Fetching artist has failed.");
            throw new HttpRequestFailedException(e.getMessage());
        }
    }

    @Override
    public Paging<AlbumSimplified> getArtistAlbums(String artistId, List<AlbumType> listOfAlbumTypes, Map<String, String> options) {
        options = ValidatorUtil.optionsValueCheck(options);

        String albumTypesWithCommaDelimiter = listOfAlbumTypes.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));

        if (!albumTypesWithCommaDelimiter.isEmpty()) {
            options.put("include_groups", albumTypesWithCommaDelimiter);
        }

        logger.trace("Constructing HTTP call to fetch albums of an artist.");
        Call<Paging<AlbumSimplified>> httpCall = artistService.getArtistAlbums("Bearer " + this.accessToken, artistId, options);

        try {
            logger.info("Executing HTTP call to fetch albums of artist.");
            logger.debug("Fetching artist {} albums with following values: {}.", artistId, options);
            LoggingUtil.logHttpCall(logger, httpCall);
            Response<Paging<AlbumSimplified>> response = httpCall.execute();

            ResponseChecker.throwIfRequestHasNotBeenFulfilledCorrectly(response, HttpStatusCode.OK);

            logger.info("Artist albums has been successfully fetched.");
            return response.body();
        } catch (IOException e) {
            logger.error("Fetching artist albums has failed.");
            throw new HttpRequestFailedException(e.getMessage());
        }
    }

    @Override
    public TrackFullCollection getArtistTopTracks(String artistId, Map<String, String> options) {
        options = ValidatorUtil.optionsValueCheck(options);

        logger.trace("Constructing HTTP call to fetch an artist top tracks.");
        Call<TrackFullCollection> httpCall = artistService.getArtistTopTracks("Bearer " + this.accessToken, artistId, options);

        try {
            logger.info("Executing HTTP call to fetch an artist top tracks.");
            logger.debug("Fetching artist {} top tracks with following values: {}.", artistId, options);
            LoggingUtil.logHttpCall(logger, httpCall);
            Response<TrackFullCollection> response = httpCall.execute();

            ResponseChecker.throwIfRequestHasNotBeenFulfilledCorrectly(response, HttpStatusCode.OK);

            logger.info("Artist top tracks have been successfully fetched.");
            return response.body();
        } catch (IOException e) {
            logger.error("Fetching artist top tracks has failed.");
            throw new HttpRequestFailedException(e.getMessage());
        }
    }

    @Override
    public ArtistFullCollection getRelatedArtists(String artistId) {
        logger.trace("Constructing HTTP call to fetch a related artists.");
        Call<ArtistFullCollection> httpCall = artistService.getRelatedArtists("Bearer " + this.accessToken, artistId);

        try {
            logger.info("Executing HTTP call to fetch related artists.");
            LoggingUtil.logHttpCall(logger, httpCall);
            Response<ArtistFullCollection> response = httpCall.execute();

            ResponseChecker.throwIfRequestHasNotBeenFulfilledCorrectly(response, HttpStatusCode.OK);

            logger.info("Related artist has been successfully fetched.");
            return response.body();
        } catch (IOException e) {
            logger.error("Fetching related artists has failed.");
            throw new HttpRequestFailedException(e.getMessage());
        }
    }

    @Override
    public ArtistFullCollection getArtists(List<String> listOfArtistIds) {
        String artistIdsWithCommaDelimiter = String.join(",", listOfArtistIds);

        logger.trace("Constructing HTTP call to fetch multiple artists.");
        Call<ArtistFullCollection> httpCall = artistService.getArtists("Bearer " + this.accessToken, artistIdsWithCommaDelimiter);

        try {
            logger.info("Executing HTTP call to fetch multiple artists.");
            LoggingUtil.logHttpCall(logger, httpCall);
            Response<ArtistFullCollection> response = httpCall.execute();

            ResponseChecker.throwIfRequestHasNotBeenFulfilledCorrectly(response, HttpStatusCode.OK);

            logger.info("Artists has been successfully fetched.");
            return response.body();
        } catch (IOException e) {
            logger.error("Fetching artists has failed.");
            throw new HttpRequestFailedException(e.getMessage());
        }
    }
}
