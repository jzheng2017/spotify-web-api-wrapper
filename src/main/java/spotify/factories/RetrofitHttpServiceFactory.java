package spotify.factories;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import retrofit2.Retrofit;
import spotify.config.ApiUrl;
import spotify.retrofit.services.*;

public class RetrofitHttpServiceFactory {
    private final static Logger logger = LoggerFactory.getLogger(RetrofitHttpServiceFactory.class);

    private static <T> T getRetrofitHttpService(final Class<T> serviceClassToBeCreatedFor, final String baseUrl) {
        logger.trace("Requesting Retrofit HTTP client.");
        Retrofit httpClient = RetrofitClientFactory.getRetrofitClient(baseUrl);

        return httpClient.create(serviceClassToBeCreatedFor);
    }

    public static AlbumService getAlbumService() {
        return getRetrofitHttpService(AlbumService.class, ApiUrl.API_URL_HTTPS + ApiUrl.VERSION);
    }

    public static ArtistService getArtistService() {
        return getRetrofitHttpService(ArtistService.class, ApiUrl.API_URL_HTTPS + ApiUrl.VERSION);
    }

    public static AuthorizationCodeFlowService getAuthorizationCodeFlowService() {
        return getRetrofitHttpService(AuthorizationCodeFlowService.class, ApiUrl.ACCOUNTS_URL_HTTPS);
    }

    public static BrowseService getBrowseService() {
        return getRetrofitHttpService(BrowseService.class, ApiUrl.API_URL_HTTPS + ApiUrl.VERSION);
    }

    public static ClientCredentialsFlowService getClientCredentialsFlowService() {
        return getRetrofitHttpService(ClientCredentialsFlowService.class, ApiUrl.ACCOUNTS_URL_HTTPS);
    }

    public static EpisodeService getEpisodeService() {
        return getRetrofitHttpService(EpisodeService.class, ApiUrl.API_URL_HTTPS + ApiUrl.VERSION);
    }

    public static ShowService getShowService() {
        return getRetrofitHttpService(ShowService.class, ApiUrl.API_URL_HTTPS + ApiUrl.VERSION);
    }

    public static TrackService getTrackService() {
        return getRetrofitHttpService(TrackService.class, ApiUrl.API_URL_HTTPS + ApiUrl.VERSION);
    }

    public static UserService getUserService() {
        return getRetrofitHttpService(UserService.class, ApiUrl.API_URL_HTTPS + ApiUrl.VERSION);
    }

    public static FollowService getFollowService() {
        return getRetrofitHttpService(FollowService.class, ApiUrl.API_URL_HTTPS + ApiUrl.VERSION);
    }

    public static LibraryService getLibraryService() {
        return getRetrofitHttpService(LibraryService.class, ApiUrl.API_URL_HTTPS + ApiUrl.VERSION);
    }
}
