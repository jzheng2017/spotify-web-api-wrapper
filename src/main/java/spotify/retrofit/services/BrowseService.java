package spotify.retrofit.services;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import spotify.models.albums.AlbumSimplified;
import spotify.models.categories.CategoryFull;
import spotify.models.paging.Paging;
import spotify.models.playlists.FeaturedPlaylistCollection;
import spotify.models.playlists.PlaylistSimplified;
import spotify.models.recommendations.RecommendationCollection;

import java.util.Map;

public interface BrowseService {

    @GET("browse/categories/{category_id}")
    Call<CategoryFull> getCategory(
            @Header("Authorization") String accessToken,
            @Path("category_id") String categoryId,
            @QueryMap Map<String, String> options
    );

    @GET("browse/categories/{category_id}/playlists")
    Call<Paging<PlaylistSimplified>> getCategoryPlaylists(
            @Header("Authorization") String accessToken,
            @Path("category_id") String categoryId,
            @QueryMap Map<String, String> options
    );

    @GET("browse/categories")
    Call<Paging<CategoryFull>> getCategories(
            @Header("Authorization") String accessToken,
            @QueryMap Map<String, String> options
    );

    @GET("browse/featured-playlists")
    Call<FeaturedPlaylistCollection> getFeaturedPlaylists(
            @Header("Authorization") String accessToken,
            @QueryMap Map<String, String> options
    );

    @GET("browse/new-releases")
    Call<Paging<AlbumSimplified>> getNewReleases(
            @Header("Authorization") String accessToken,
            @QueryMap Map<String, String> options
    );

    @GET("recommendations")
    Call<RecommendationCollection> getRecommendations(
            @Header("Authorization") String accessToken,
            @QueryMap Map<String, String> options
    );
}