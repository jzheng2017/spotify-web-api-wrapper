package spotify.retrofit.services;

import retrofit2.Call;
import retrofit2.http.*;
import spotify.models.categories.CategoryFull;
import spotify.models.categories.CategoryFullPaging;
import spotify.models.playlists.FeaturedPlaylistCollection;
import spotify.models.playlists.PlaylistSimplifiedPaging;

import java.util.Map;

public interface BrowseService {
    @GET("browse/categories/{category_id}")
    Call<CategoryFull> getCategory(@Header("Authorization") String accessToken,
                                   @Path("category_id") String categoryId,
                                   @Query("country") String country,
                                   @Query("locale") String locale);

    @GET("browse/categories/{category_id}/playlists")
    Call<PlaylistSimplifiedPaging> getCategoryPlaylists(@Header("Authorization") String accessToken,
                                                        @Path("category_id") String categoryId,
                                                        @Query("country") String country,
                                                        @Query("limit") int limit,
                                                        @Query("offset") int offset);

    @GET("browse/categories")
    Call<CategoryFullPaging> getCategories(@Header("Authorization") String accessToken,
                                           @Query("country") String country,
                                           @Query("locale") String locale,
                                           @Query("limit") int limit,
                                           @Query("offset") int offset);

    @GET("browse/featured-playlists")
    Call<FeaturedPlaylistCollection> getFeaturedPlaylists(@Header("Authorization") String accessToken,
                                                          @QueryMap Map<String, String> options);
}
