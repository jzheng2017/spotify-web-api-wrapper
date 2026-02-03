package spotify.retrofit.services;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import spotify.models.albums.AlbumSimplifiedPaging;
import spotify.models.categories.CategoryFull;
import spotify.models.categories.CategoryFullPaging;
import spotify.models.playlists.FeaturedPlaylistCollection;
import spotify.models.playlists.PlaylistSimplifiedPaging;
import spotify.models.recommendations.RecommendationCollection;

import java.util.Map;

/**
 * Retrofit service interface for Spotify Browse API endpoints.
 * Provides methods to access categories, featured playlists, new releases, and recommendations.
 */
public interface BrowseService {


    /**
     * Retrieves detailed information about a specific browse category.
     *
     * @param accessToken the OAuth access token for authorization
     * @param categoryId the Spotify category ID
     * @param options optional query parameters such as country and locale
     * @return a Call object containing the full category details
     */
    @GET("browse/categories/{category_id}")
    Call<CategoryFull> getCategory(@Header("Authorization") String accessToken, @Path("category_id") String categoryId, @QueryMap Map<String, String> options);


    /**
     * Retrieves playlists associated with a specific browse category.
     *
     * @param accessToken the OAuth access token for authorization
     * @param categoryId the Spotify category ID
     * @param options optional query parameters such as country, limit, and offset
     * @return a Call object containing a paged list of simplified playlist objects
     */
    @GET("browse/categories/{category_id}/playlists")
    Call<PlaylistSimplifiedPaging> getCategoryPlaylists(@Header("Authorization") String accessToken, @Path("category_id") String categoryId, @QueryMap Map<String, String> options);


    /**
     * Retrieves a list of all available browse categories.
     *
     * @param accessToken the OAuth access token for authorization
     * @param options optional query parameters such as country, locale, limit, and offset
     * @return a Call object containing a paged list of full category objects
     */
    @GET("browse/categories")
    Call<CategoryFullPaging> getCategories(@Header("Authorization") String accessToken, @QueryMap Map<String, String> options);


    /**
     * Retrieves a list of Spotify featured playlists.
     *
     * @param accessToken the OAuth access token for authorization
     * @param options optional query parameters such as country, locale, timestamp, limit, and offset
     * @return a Call object containing the featured playlist collection with a message
     */
    @GET("browse/featured-playlists")
    Call<FeaturedPlaylistCollection> getFeaturedPlaylists(@Header("Authorization") String accessToken, @QueryMap Map<String, String> options);

    /**
     * Retrieves a list of new album releases on Spotify.
     *
     * @param accessToken the OAuth access token for authorization
     * @param options optional query parameters such as country, limit, and offset
     * @return a Call object containing a paged list of simplified album objects
     */
    @GET("browse/new-releases")
    Call<AlbumSimplifiedPaging> getNewReleases(@Header("Authorization") String accessToken, @QueryMap Map<String, String> options);


    /**
     * Retrieves track recommendations based on seed artists, tracks, and genres.
     *
     * @param accessToken the OAuth access token for authorization
     * @param options query parameters including seed values and tunable track attributes
     * @return a Call object containing the recommendation results
     */
    @GET("recommendations")
    Call<RecommendationCollection> getRecommendations(@Header("Authorization") String accessToken, @QueryMap Map<String, String> options);
}