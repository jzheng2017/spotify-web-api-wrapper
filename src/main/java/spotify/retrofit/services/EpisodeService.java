package spotify.retrofit.services;

import retrofit2.Call;
import retrofit2.http.*;
import spotify.models.episodes.EpisodeFull;
import spotify.models.episodes.EpisodeFullCollection;

import java.util.Map;

/**
 * Retrofit service interface for Spotify Episode API endpoints.
 * Provides methods to retrieve podcast episode information.
 */
public interface EpisodeService {

    /**
     * Retrieves detailed information about a specific episode.
     *
     * @param accessToken the OAuth access token for authorization
     * @param episodeId the Spotify ID of the episode
     * @param options optional query parameters such as market
     * @return a Call object containing the full episode details
     */
    @GET("episodes/{id}")
    Call<EpisodeFull> getEpisode(@Header("Authorization") String accessToken, @Path("id") String episodeId, @QueryMap Map<String, String> options);

    /**
     * Retrieves detailed information about multiple episodes.
     *
     * @param accessToken the OAuth access token for authorization
     * @param episodeIds a comma-separated list of Spotify episode IDs
     * @param options optional query parameters such as market
     * @return a Call object containing a collection of full episode details
     */
    @GET("episodes")
    Call<EpisodeFullCollection> getEpisodes(@Header("Authorization") String accessToken, @Query("ids") String episodeIds, @QueryMap Map<String, String> options);
}