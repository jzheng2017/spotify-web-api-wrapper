package spotify.retrofit.services;

import retrofit2.Call;
import retrofit2.http.*;
import spotify.models.episodes.EpisodeSimplified;
import spotify.models.paging.Paging;
import spotify.models.shows.ShowFull;
import spotify.models.shows.ShowSimplifiedCollection;

import java.util.Map;

/**
 * Retrofit service interface for Spotify Show API endpoints.
 * Provides methods to retrieve podcast show information and episodes.
 */
public interface ShowService {

    /**
     * Retrieves detailed information about a specific show.
     *
     * @param accessToken the OAuth access token for authorization
     * @param showId the Spotify ID of the show
     * @param options optional query parameters such as market
     * @return a Call object containing the full show details
     */
    @GET("shows/{id}")
    Call<ShowFull> getShow(@Header("Authorization") String accessToken, @Path("id") String showId, @QueryMap Map<String, String> options);

    /**
     * Retrieves the episodes of a specific show.
     *
     * @param accessToken the OAuth access token for authorization
     * @param showId the Spotify ID of the show
     * @param options optional query parameters such as market, limit, and offset
     * @return a Call object containing a paged list of simplified episode objects
     */
    @GET("shows/{id}/episodes")
    Call<Paging<EpisodeSimplified>> getShowEpisodes(@Header("Authorization") String accessToken,
                                                    @Path("id") String showId,
                                                    @QueryMap Map<String, String> options);

    /**
     * Retrieves detailed information about multiple shows.
     *
     * @param accessToken the OAuth access token for authorization
     * @param showIds a comma-separated list of Spotify show IDs
     * @param options optional query parameters such as market
     * @return a Call object containing a collection of simplified show details
     */
    @GET("shows")
    Call<ShowSimplifiedCollection> getShows(@Header("Authorization") String accessToken, @Query("ids") String showIds, @QueryMap Map<String, String> options);
}