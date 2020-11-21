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

public interface BrowseService {
    @GET("browse/categories/{category_id}")
    Call<CategoryFull> getCategory(@Header("Authorization") String accessToken, @Path("category_id") String categoryId, @QueryMap Map<String, String> options);

    @GET("browse/categories/{category_id}/playlists")
    Call<PlaylistSimplifiedPaging> getCategoryPlaylists(@Header("Authorization") String accessToken, @Path("category_id") String categoryId, @QueryMap Map<String, String> options);

    @GET("browse/categories")
    Call<CategoryFullPaging> getCategories(@Header("Authorization") String accessToken, @QueryMap Map<String, String> options);

    @GET("browse/featured-playlists")
    Call<FeaturedPlaylistCollection> getFeaturedPlaylists(@Header("Authorization") String accessToken, @QueryMap Map<String, String> options);

    @GET("browse/new-releases")
    Call<AlbumSimplifiedPaging> getNewReleases(@Header("Authorization") String accessToken, @QueryMap Map<String, String> options);

    @GET("recommendations")
    Call<RecommendationCollection> getRecommendations(@Header("Authorization") String accessToken, @QueryMap Map<String, String> options);
}
