package spotify.retrofit.services;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;
import spotify.models.categories.CategoryFull;
import spotify.models.playlists.PlaylistSimplifiedPaging;

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
}
