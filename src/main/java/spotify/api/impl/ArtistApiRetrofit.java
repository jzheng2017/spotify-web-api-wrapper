package spotify.api.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import spotify.api.enums.AlbumType;
import spotify.api.interfaces.ArtistApi;
import spotify.config.ApiUrl;
import spotify.exceptions.HttpRequestFailedException;
import spotify.exceptions.ResponseChecker;
import spotify.factories.RetrofitClientFactory;
import spotify.models.artists.ArtistFull;
import spotify.models.artists.ArtistFullCollection;
import spotify.models.artists.ArtistSimplified;
import spotify.models.paging.Paging;
import spotify.models.tracks.TrackFullCollection;
import spotify.retrofit.services.ArtistService;
import spotify.utils.ValidatorUtil;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class ArtistApiRetrofit implements ArtistApi {
    private final Logger logger = LoggerFactory.getLogger(ArtistApiRetrofit.class);
    private final String accessToken;
    private ArtistService artistService;

    public ArtistApiRetrofit(String accessToken) {
        this.accessToken = accessToken;
        setup();
    }

    @Override
    public ArtistFull getArtist(String artistId) {
        logger.trace("Constructing HTTP call to fetch an artist.");
        Call<ArtistFull> httpCall = artistService.getArtist("Bearer " + this.accessToken, artistId);

        try {
            logger.info("Executing HTTP call to fetch an artist.");
            logger.debug(String.format("%s / %s", httpCall.request().method(), httpCall.request().url().toString()));
            Response<ArtistFull> response = httpCall.execute();

            ResponseChecker.throwIfRequestHasNotBeenFulfilledCorrectly(response.errorBody());

            logger.info("Artist has been successfully fetched.");
            return response.body();
        } catch (IOException e) {
            logger.error("Fetching artist has failed.");
            throw new HttpRequestFailedException(e.getMessage());
        }
    }

    @Override
    public Paging<ArtistSimplified> getArtistAlbums(String artistId, List<AlbumType> listOfAlbumTypes, String country, int limit, int offset) {
        ValidatorUtil.validateFiltersAndThrowIfInvalid(limit, offset);
        country = ValidatorUtil.emptyValueCheck(country);

        String albumTypesWithCommaDelimiter = listOfAlbumTypes.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));

        albumTypesWithCommaDelimiter = albumTypesWithCommaDelimiter.isEmpty() ? null : albumTypesWithCommaDelimiter;

        logger.trace("Constructing HTTP call to fetch albums of an artist.");
        Call<Paging<ArtistSimplified>> httpCall = artistService.getArtistAlbums("Bearer " + this.accessToken, artistId, albumTypesWithCommaDelimiter, country, limit, offset);

        try {
            logger.info("Executing HTTP call to fetch albums of artist.");
            logger.debug(String.format("%s / %s", httpCall.request().method(), httpCall.request().url().toString()));
            Response<Paging<ArtistSimplified>> response = httpCall.execute();

            ResponseChecker.throwIfRequestHasNotBeenFulfilledCorrectly(response.errorBody());

            logger.info("Artist albums has been successfully fetched.");
            return response.body();
        } catch (IOException e) {
            logger.error("Fetching artist albums has failed.");
            throw new HttpRequestFailedException(e.getMessage());
        }
    }

    @Override
    public TrackFullCollection getArtistTopTracks(String artistId, String country) {
        country = ValidatorUtil.emptyValueCheck(country);

        logger.trace("Constructing HTTP call to fetch an artist top tracks.");
        Call<TrackFullCollection> httpCall = artistService.getArtistTopTracks("Bearer " + this.accessToken, artistId, country);

        try {
            logger.info("Executing HTTP call to fetch an artist top tracks.");
            logger.debug(String.format("%s / %s", httpCall.request().method(), httpCall.request().url().toString()));
            Response<TrackFullCollection> response = httpCall.execute();

            ResponseChecker.throwIfRequestHasNotBeenFulfilledCorrectly(response.errorBody());

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
            logger.debug(String.format("%s / %s", httpCall.request().method(), httpCall.request().url().toString()));
            Response<ArtistFullCollection> response = httpCall.execute();

            ResponseChecker.throwIfRequestHasNotBeenFulfilledCorrectly(response.errorBody());

            logger.info("Related artist has been successfully fetched.");
            return response.body();
        } catch (IOException e) {
            logger.error("Fetching related artists has failed.");
            throw new HttpRequestFailedException(e.getMessage());
        }
    }

    private void setup() {
        logger.trace("Requesting Retrofit HTTP client.");
        Retrofit httpClient = RetrofitClientFactory.getRetrofitClient(ApiUrl.API_URL_HTTPS + ApiUrl.VERSION);

        artistService = httpClient.create(ArtistService.class);
    }
}
