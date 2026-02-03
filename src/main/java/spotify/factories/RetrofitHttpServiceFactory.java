package spotify.factories;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import retrofit2.Retrofit;
import spotify.config.ApiUrl;
import spotify.retrofit.services.*;

/**
 * Factory class for creating Retrofit HTTP service interfaces for the Spotify API.
 * Provides methods to obtain service instances for each Spotify API endpoint category,
 * such as albums, artists, playlists, and player controls.
 */
public class RetrofitHttpServiceFactory {
    private static final String API_BASE_URL_HTTPS_WITH_VERSION = ApiUrl.API_URL_HTTPS + ApiUrl.VERSION;
    private static final Logger logger = LoggerFactory.getLogger(RetrofitHttpServiceFactory.class);

    /**
     * Creates a service for accessing album-related endpoints.
     *
     * @return an {@link AlbumService} instance
     */
    public static AlbumService getAlbumService() {
        return getRetrofitHttpService(AlbumService.class, API_BASE_URL_HTTPS_WITH_VERSION);
    }

    /**
     * Creates a service for accessing artist-related endpoints.
     *
     * @return an {@link ArtistService} instance
     */
    public static ArtistService getArtistService() {
        return getRetrofitHttpService(ArtistService.class, API_BASE_URL_HTTPS_WITH_VERSION);
    }

    /**
     * Creates a service for handling Authorization Code Flow authentication.
     *
     * @return an {@link AuthorizationCodeFlowService} instance
     */
    public static AuthorizationCodeFlowService getAuthorizationCodeFlowService() {
        return getRetrofitHttpService(AuthorizationCodeFlowService.class, ApiUrl.ACCOUNTS_URL_HTTPS);
    }

    /**
     * Creates a service for accessing browse-related endpoints such as new releases and featured playlists.
     *
     * @return a {@link BrowseService} instance
     */
    public static BrowseService getBrowseService() {
        return getRetrofitHttpService(BrowseService.class, API_BASE_URL_HTTPS_WITH_VERSION);
    }

    /**
     * Creates a service for handling Client Credentials Flow authentication.
     *
     * @return a {@link ClientCredentialsFlowService} instance
     */
    public static ClientCredentialsFlowService getClientCredentialsFlowService() {
        return getRetrofitHttpService(ClientCredentialsFlowService.class, ApiUrl.ACCOUNTS_URL_HTTPS);
    }

    /**
     * Creates a service for accessing episode-related endpoints.
     *
     * @return an {@link EpisodeService} instance
     */
    public static EpisodeService getEpisodeService() {
        return getRetrofitHttpService(EpisodeService.class, API_BASE_URL_HTTPS_WITH_VERSION);
    }

    /**
     * Creates a service for accessing show-related endpoints.
     *
     * @return a {@link ShowService} instance
     */
    public static ShowService getShowService() {
        return getRetrofitHttpService(ShowService.class, API_BASE_URL_HTTPS_WITH_VERSION);
    }

    /**
     * Creates a service for accessing track-related endpoints.
     *
     * @return a {@link TrackService} instance
     */
    public static TrackService getTrackService() {
        return getRetrofitHttpService(TrackService.class, API_BASE_URL_HTTPS_WITH_VERSION);
    }

    /**
     * Creates a service for accessing user profile endpoints.
     *
     * @return a {@link UserService} instance
     */
    public static UserService getUserService() {
        return getRetrofitHttpService(UserService.class, API_BASE_URL_HTTPS_WITH_VERSION);
    }

    /**
     * Creates a service for accessing follow-related endpoints for artists, users, and playlists.
     *
     * @return a {@link FollowService} instance
     */
    public static FollowService getFollowService() {
        return getRetrofitHttpService(FollowService.class, API_BASE_URL_HTTPS_WITH_VERSION);
    }

    /**
     * Creates a service for accessing user library endpoints for saved tracks, albums, and shows.
     *
     * @return a {@link LibraryService} instance
     */
    public static LibraryService getLibraryService() {
        return getRetrofitHttpService(LibraryService.class, API_BASE_URL_HTTPS_WITH_VERSION);
    }

    /**
     * Creates a service for accessing playlist-related endpoints.
     * Uses a custom deserializer to handle polymorphic playable objects in playlist tracks.
     *
     * @return a {@link PlaylistService} instance
     */
    public static PlaylistService getPlaylistService() {
        Retrofit httpClient = RetrofitClientFactory.getRetrofitClientWithAbstractPlayableObjectDeserializer(API_BASE_URL_HTTPS_WITH_VERSION);

        return httpClient.create(PlaylistService.class);
    }

    /**
     * Creates a service for accessing personalization endpoints such as top artists and tracks.
     *
     * @return a {@link PersonalizationService} instance
     */
    public static PersonalizationService getPersonalizationService() {
        return getRetrofitHttpService(PersonalizationService.class, API_BASE_URL_HTTPS_WITH_VERSION);
    }

    /**
     * Creates a service for accessing player-related endpoints for playback control.
     * Uses a custom deserializer to handle polymorphic playable objects in the current playback state.
     *
     * @return a {@link PlayerService} instance
     */
    public static PlayerService getPlayerService() {
        Retrofit httpClient = RetrofitClientFactory.getRetrofitClientWithAbstractPlayableObjectDeserializer(API_BASE_URL_HTTPS_WITH_VERSION);

        return httpClient.create(PlayerService.class);
    }

    /**
     * Creates a service for accessing search endpoints.
     *
     * @return a {@link SearchService} instance
     */
    public static SearchService getSearchService() {
        return getRetrofitHttpService(SearchService.class, API_BASE_URL_HTTPS_WITH_VERSION);
    }

    /**
     * Creates a Retrofit HTTP service instance for the specified service class.
     *
     * @param serviceClassToBeCreatedFor the service interface class to create
     * @param baseUrl the base URL for the API endpoints
     * @param <T> the type of the service interface
     * @return an instance of the specified service interface
     */
    private static <T> T getRetrofitHttpService(final Class<T> serviceClassToBeCreatedFor, final String baseUrl) {
        logger.trace("Requesting Retrofit HTTP client for {}", serviceClassToBeCreatedFor);
        Retrofit httpClient = RetrofitClientFactory.getRetrofitClient(baseUrl);

        return httpClient.create(serviceClassToBeCreatedFor);
    }

    /**
     * Creates a service for accessing market-related endpoints.
     *
     * @return a {@link MarketService} instance
     */
    public static MarketService getMarketService() {
        return getRetrofitHttpService(MarketService.class, API_BASE_URL_HTTPS_WITH_VERSION);
    }
}