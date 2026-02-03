package spotify.retrofit.services;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import spotify.models.search.SearchQueryResult;

import java.util.Map;

/**
 * Retrofit service interface for Spotify Search API endpoints.
 * Provides a method to search for tracks, artists, albums, playlists, shows, and episodes.
 */
public interface SearchService {

    /**
     * Searches for items in the Spotify catalog.
     *
     * @param accessToken the OAuth access token for authorization
     * @param query the search query string
     * @param queryTypes a comma-separated list of item types to search for (album, artist, playlist, track, show, episode)
     * @param options optional query parameters such as market, limit, offset, and include_external
     * @return a Call object containing the search results grouped by type
     */
    @GET("search")
    Call<SearchQueryResult> searchItem(@Header("Authorization") String accessToken,
                                       @Query("q") String query,
                                       @Query("type") String queryTypes,
                                       @QueryMap Map<String, String> options);
}