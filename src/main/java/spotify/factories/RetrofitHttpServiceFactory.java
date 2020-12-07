package spotify.factories;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import retrofit2.Retrofit;
import spotify.config.ApiUrl;
import spotify.retrofit.services.*;

public class RetrofitHttpServiceFactory {
    private static final String API_BASE_URL_HTTPS_WITH_VERSION = ApiUrl.API_URL_HTTPS + ApiUrl.VERSION;
    private static final Logger logger = LoggerFactory.getLogger(RetrofitHttpServiceFactory.class);

    private static <T> T getRetrofitHttpService(final Class<T> serviceClassToBeCreatedFor, final String baseUrl) {
        logger.trace("Requesting Retrofit HTTP client for {}", serviceClassToBeCreatedFor);
        Retrofit httpClient = RetrofitClientFactory.getRetrofitClient(baseUrl);

        return httpClient.create(serviceClassToBeCreatedFor);
    }

    public static AlbumService getAlbumService() {
        return getRetrofitHttpService(AlbumService.class, API_BASE_URL_HTTPS_WITH_VERSION);
    }

    public static ArtistService getArtistService() {
        return getRetrofitHttpService(ArtistService.class, API_BASE_URL_HTTPS_WITH_VERSION);
    }

    public static AuthorizationCodeFlowService getAuthorizationCodeFlowService() {
        return getRetrofitHttpService(AuthorizationCodeFlowService.class, ApiUrl.ACCOUNTS_URL_HTTPS);
    }

    public static BrowseService getBrowseService() {
        return getRetrofitHttpService(BrowseService.class, API_BASE_URL_HTTPS_WITH_VERSION);
    }

    public static ClientCredentialsFlowService getClientCredentialsFlowService() {
        return getRetrofitHttpService(ClientCredentialsFlowService.class, ApiUrl.ACCOUNTS_URL_HTTPS);
    }

    public static EpisodeService getEpisodeService() {
        return getRetrofitHttpService(EpisodeService.class, API_BASE_URL_HTTPS_WITH_VERSION);
    }

    public static ShowService getShowService() {
        return getRetrofitHttpService(ShowService.class, API_BASE_URL_HTTPS_WITH_VERSION);
    }

    public static TrackService getTrackService() {
        return getRetrofitHttpService(TrackService.class, API_BASE_URL_HTTPS_WITH_VERSION);
    }

    public static UserService getUserService() {
        return getRetrofitHttpService(UserService.class, API_BASE_URL_HTTPS_WITH_VERSION);
    }

    public static FollowService getFollowService() {
        return getRetrofitHttpService(FollowService.class, API_BASE_URL_HTTPS_WITH_VERSION);
    }

    public static LibraryService getLibraryService() {
        return getRetrofitHttpService(LibraryService.class, API_BASE_URL_HTTPS_WITH_VERSION);
    }

    public static PlaylistService getPlaylistService() {
        Retrofit httpClient = RetrofitClientFactory.getRetrofitClientWithAbstractPlayableObjectDeserializer(API_BASE_URL_HTTPS_WITH_VERSION);

        return httpClient.create(PlaylistService.class);
    }

    public static PersonalizationService getPersonalizationService() {
        return getRetrofitHttpService(PersonalizationService.class, API_BASE_URL_HTTPS_WITH_VERSION);
    }

    public static PlayerService getPlayerService() {
        Retrofit httpClient = RetrofitClientFactory.getRetrofitClientWithAbstractPlayableObjectDeserializer(API_BASE_URL_HTTPS_WITH_VERSION);

        return httpClient.create(PlayerService.class);
    }

    public static SearchService getSearchService() {
        return getRetrofitHttpService(SearchService.class, API_BASE_URL_HTTPS_WITH_VERSION);
    }
}
