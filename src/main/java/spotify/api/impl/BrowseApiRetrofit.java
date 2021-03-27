package spotify.api.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import retrofit2.Call;
import retrofit2.Response;
import spotify.api.enums.HttpStatusCode;
import spotify.api.interfaces.BrowseApi;
import spotify.exceptions.ExceptionMessages;
import spotify.exceptions.HttpRequestFailedException;
import spotify.exceptions.SpotifyActionFailedException;
import spotify.factories.RetrofitHttpServiceFactory;
import spotify.models.albums.AlbumSimplified;
import spotify.models.albums.AlbumSimplifiedPaging;
import spotify.models.categories.CategoryFull;
import spotify.models.categories.CategoryFullPaging;
import spotify.models.paging.Paging;
import spotify.models.playlists.FeaturedPlaylistCollection;
import spotify.models.playlists.PlaylistSimplified;
import spotify.models.playlists.PlaylistSimplifiedPaging;
import spotify.models.recommendations.RecommendationCollection;
import spotify.retrofit.services.BrowseService;
import spotify.utils.LoggingUtil;
import spotify.utils.ResponseChecker;
import spotify.utils.ValidatorUtil;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class BrowseApiRetrofit implements BrowseApi {
    private final Logger logger = LoggerFactory.getLogger(BrowseApiRetrofit.class);
    private final String accessToken;
    private final BrowseService browseService;

    public BrowseApiRetrofit(final String accessToken) {
        this(accessToken, RetrofitHttpServiceFactory.getBrowseService());
    }

    public BrowseApiRetrofit(final String accessToken, final BrowseService browseService) {
        this.accessToken = accessToken;
        this.browseService = browseService;
    }

    @Override
    public CategoryFull getCategory(String categoryId, Map<String, String> options) {
        options = ValidatorUtil.optionsValueCheck(options);

        logger.trace("Constructing HTTP call to fetch category.");
        Call<CategoryFull> httpCall = browseService.getCategory("Bearer " + this.accessToken, categoryId, options);

        try {
            logger.info("Executing HTTP call to fetch category.");
            logger.debug("Fetching category {} with following values: {}.", categoryId, options);
            LoggingUtil.logHttpCall(logger, httpCall);
            Response<CategoryFull> response = httpCall.execute();

            ResponseChecker.throwIfRequestHasNotBeenFulfilledCorrectly(response, HttpStatusCode.OK);

            logger.info("Category has been successfully fetched.");
            return response.body();
        } catch (IOException ex) {
            logger.error("HTTP request to fetch category has failed.");
            throw new HttpRequestFailedException(ex.getMessage());
        }
    }

    /**
     * @deprecated Replaced by {@link #getCategoryPlaylistsPaging(String, Map)} in version 1.5.8.
     */
    @Override
    @Deprecated(since = "1.5.8")
    public PlaylistSimplifiedPaging getCategoryPlaylists(String categoryId, Map<String, String> options) {
        Paging<PlaylistSimplified> categoryFullPaging = getCategoryPlaylistsPaging(categoryId, options);
        PlaylistSimplifiedPaging output = new PlaylistSimplifiedPaging();
        output.setPlaylists(categoryFullPaging);
        return output;
    }
    
    @Override
    public Paging<PlaylistSimplified> getCategoryPlaylistsPaging(String categoryId, Map<String, String> options) {
        options = ValidatorUtil.optionsValueCheck(options);

        logger.trace("Constructing HTTP call to fetch category playlists.");
        Call<PlaylistSimplifiedPaging> httpCall = browseService.getCategoryPlaylists("Bearer " + this.accessToken, categoryId, options);

        try {
            logger.info("Executing HTTP call to fetch category playlists.");
            logger.debug("Fetching category {} playlists with following values: {}.", categoryId, options);
            LoggingUtil.logHttpCall(logger, httpCall);
            Response<PlaylistSimplifiedPaging> response = httpCall.execute();

            ResponseChecker.throwIfRequestHasNotBeenFulfilledCorrectly(response, HttpStatusCode.OK);

            if (response.body() == null) {
                throw new SpotifyActionFailedException(ExceptionMessages.EMPTY_RESPONSE_BODY);
            }
            logger.info("Category playlists have been successfully fetched.");
            return response.body().getPlaylists();
        } catch (IOException ex) {
            logger.error("HTTP request to fetch category playlists has failed.");
            throw new HttpRequestFailedException(ex.getMessage());
        }
    }

    /**
     * @deprecated Replaced by {@link #getCategoriesPaging(Map)} in version 1.5.8.
     */
    @Override
    @Deprecated(since = "1.5.8")
    public CategoryFullPaging getCategories(Map<String, String> options) {
        Paging<CategoryFull> categoryFullPaging = getCategoriesPaging(options);
        CategoryFullPaging output = new CategoryFullPaging();
        output.setCategories(categoryFullPaging);
        return output;
    }
    
    @Override
    public Paging<CategoryFull> getCategoriesPaging(Map<String, String> options) {
        options = ValidatorUtil.optionsValueCheck(options);

        logger.trace("Constructing HTTP call to fetch categories.");
        Call<CategoryFullPaging> httpCall = browseService.getCategories("Bearer " + this.accessToken, options);

        try {
            logger.info("Executing HTTP call to fetch categories.");
            logger.debug("Fetching categories with following values: {}.", options);
            LoggingUtil.logHttpCall(logger, httpCall);
            Response<CategoryFullPaging> response = httpCall.execute();

            ResponseChecker.throwIfRequestHasNotBeenFulfilledCorrectly(response, HttpStatusCode.OK);

            if (response.body() == null) {
                throw new SpotifyActionFailedException(ExceptionMessages.EMPTY_RESPONSE_BODY);
            }
            
            logger.info("Categories have been successfully fetched.");
            return response.body().getCategories();
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
            logger.debug("Fetching featured playlists with following values: {}", options);
            LoggingUtil.logHttpCall(logger, httpCall);
            Response<FeaturedPlaylistCollection> response = httpCall.execute();

            ResponseChecker.throwIfRequestHasNotBeenFulfilledCorrectly(response, HttpStatusCode.OK);

            logger.info("Featured playlists have been successfully fetched.");
            return response.body();
        } catch (IOException ex) {
            logger.error("HTTP request to fetch featured playlists has failed.");
            throw new HttpRequestFailedException(ex.getMessage());
        }
    }

    /**
     * @deprecated Replaced by {@link #getNewReleasesPaging(Map)} in version 1.5.8.
     */
    @Override
    public AlbumSimplifiedPaging getNewReleases(Map<String, String> options) {
        Paging<AlbumSimplified> albumSimplifiedPaging = getNewReleasesPaging(options);
        AlbumSimplifiedPaging output = new AlbumSimplifiedPaging();
        output.setAlbums(albumSimplifiedPaging);
        return output;
    }
    
    @Override
    public Paging<AlbumSimplified> getNewReleasesPaging(Map<String, String> options) {
        options = ValidatorUtil.optionsValueCheck(options);

        logger.trace("Constructing HTTP call to fetch new releases.");
        Call<AlbumSimplifiedPaging> httpCall = browseService.getNewReleases("Bearer " + this.accessToken, options);

        try {
            logger.info("Executing HTTP call to fetch new releases.");
            logger.debug("Fetching new releases with following values: {}", options);
            LoggingUtil.logHttpCall(logger, httpCall);
            Response<AlbumSimplifiedPaging> response = httpCall.execute();

            ResponseChecker.throwIfRequestHasNotBeenFulfilledCorrectly(response, HttpStatusCode.OK);

            if (response.body() == null) {
                throw new SpotifyActionFailedException(ExceptionMessages.EMPTY_RESPONSE_BODY);
            }
            
            logger.info("New releases have been successfully fetched.");
            return response.body().getAlbums();
        } catch (IOException ex) {
            logger.error("HTTP request to fetch new releases has failed.");
            throw new HttpRequestFailedException(ex.getMessage());
        }
    }

    @Override
    public RecommendationCollection getRecommendations(List<String> listOfSeedArtists, List<String> listOfSeedGenres, List<String> listOfSeedTracks, Map<String, String> options) {
        options = ValidatorUtil.optionsValueCheck(options);

        mapSeedParameters(listOfSeedArtists, listOfSeedGenres, listOfSeedTracks, options);

        logger.trace("Constructing HTTP call to fetch recommendations.");
        Call<RecommendationCollection> httpCall = browseService.getRecommendations("Bearer " + this.accessToken, options);

        try {
            logger.info("Executing HTTP call to fetch recommendations.");
            logger.debug("Fetching recommendations with following values: {}", options);
            LoggingUtil.logHttpCall(logger, httpCall);
            Response<RecommendationCollection> response = httpCall.execute();

            ResponseChecker.throwIfRequestHasNotBeenFulfilledCorrectly(response, HttpStatusCode.OK);

            logger.info("Recommendations have been successfully fetched.");
            return response.body();
        } catch (IOException ex) {
            logger.error("HTTP request to fetch recommendations has failed.");
            throw new HttpRequestFailedException(ex.getMessage());
        }
    }

    private void mapSeedParameters(List<String> listOfSeedArtists, List<String> listOfSeedGenres, List<String> listOfSeedTracks, Map<String, String> options) {
        final String artistSeedIds = String.join(",", listOfSeedArtists);
        final String genreSeedIds = String.join(",", listOfSeedGenres);
        final String trackSeedIds = String.join(",", listOfSeedTracks);

        if (!artistSeedIds.isEmpty()) {
            options.put("seed_artists", artistSeedIds);
        }

        if (!genreSeedIds.isEmpty()) {
            options.put("seed_genres", genreSeedIds);
        }

        if (!trackSeedIds.isEmpty()) {
            options.put("seed_tracks", trackSeedIds);
        }
    }
}
