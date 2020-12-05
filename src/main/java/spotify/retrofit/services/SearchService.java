package spotify.retrofit.services;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import spotify.models.search.SearchQueryResult;

import java.util.Map;

public interface SearchService {
    @GET("search")
    Call<SearchQueryResult> searchItem(@Header("Authorization") String accessToken,
                                       @Query("q") String query,
                                       @Query("type") String queryTypes,
                                       @QueryMap Map<String, String> options);
}
