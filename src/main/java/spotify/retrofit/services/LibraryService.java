package spotify.retrofit.services;

import retrofit2.Call;
import retrofit2.http.*;
import spotify.models.albums.SavedAlbumFull;
import spotify.models.paging.Paging;
import spotify.models.shows.SavedShowSimplified;
import spotify.models.tracks.SavedTrackFull;

import java.util.List;
import java.util.Map;

/**
 * Retrofit service interface for Spotify Library API endpoints.
 * Provides methods to manage the current user's saved albums, shows, and tracks.
 */
public interface LibraryService {

    /**
     * Checks if one or more albums are saved in the current user's library.
     *
     * @param accessToken the OAuth access token for authorization
     * @param albumIds a comma-separated list of Spotify album IDs to check
     * @return a Call object containing a list of booleans indicating saved status for each album
     */
    @GET("me/albums/contains")
    Call<List<Boolean>> hasSavedAlbums(@Header("Authorization") String accessToken, @Query("ids") String albumIds);

    /**
     * Checks if one or more shows are saved in the current user's library.
     *
     * @param accessToken the OAuth access token for authorization
     * @param showIds a comma-separated list of Spotify show IDs to check
     * @return a Call object containing a list of booleans indicating saved status for each show
     */
    @GET("me/shows/contains")
    Call<List<Boolean>> hasSavedShows(@Header("Authorization") String accessToken, @Query("ids") String showIds);

    /**
     * Checks if one or more tracks are saved in the current user's library.
     *
     * @param accessToken the OAuth access token for authorization
     * @param trackIds a comma-separated list of Spotify track IDs to check
     * @return a Call object containing a list of booleans indicating saved status for each track
     */
    @GET("me/tracks/contains")
    Call<List<Boolean>> hasSavedTracks(@Header("Authorization") String accessToken, @Query("ids") String trackIds);

    /**
     * Retrieves the albums saved in the current user's library.
     *
     * @param accessToken the OAuth access token for authorization
     * @param options optional query parameters such as market, limit, and offset
     * @return a Call object containing a paged list of saved albums with timestamps
     */
    @GET("me/albums")
    Call<Paging<SavedAlbumFull>> getSavedAlbums(@Header("Authorization") String accessToken, @QueryMap Map<String, String> options);

    /**
     * Retrieves the shows saved in the current user's library.
     *
     * @param accessToken the OAuth access token for authorization
     * @param options optional query parameters such as limit and offset
     * @return a Call object containing a paged list of saved shows with timestamps
     */
    @GET("me/shows")
    Call<Paging<SavedShowSimplified>> getSavedShows(@Header("Authorization") String accessToken, @QueryMap Map<String, String> options);

    /**
     * Retrieves the tracks saved in the current user's library.
     *
     * @param accessToken the OAuth access token for authorization
     * @param options optional query parameters such as market, limit, and offset
     * @return a Call object containing a paged list of saved tracks with timestamps
     */
    @GET("me/tracks")
    Call<Paging<SavedTrackFull>> getSavedTracks(@Header("Authorization") String accessToken, @QueryMap Map<String, String> options);

    /**
     * Saves one or more albums to the current user's library.
     *
     * @param accessToken the OAuth access token for authorization
     * @param albumIds a comma-separated list of Spotify album IDs to save
     * @return a Call object with no content on success
     */
    @PUT("me/albums")
    Call<Void> saveAlbums(@Header("Authorization") String accessToken, @Query("ids") String albumIds);

    /**
     * Saves one or more shows to the current user's library.
     *
     * @param accessToken the OAuth access token for authorization
     * @param showIds a comma-separated list of Spotify show IDs to save
     * @return a Call object with no content on success
     */
    @PUT("me/shows")
    Call<Void> saveShows(@Header("Authorization") String accessToken, @Query("ids") String showIds);

    /**
     * Saves one or more tracks to the current user's library.
     *
     * @param accessToken the OAuth access token for authorization
     * @param trackIds a comma-separated list of Spotify track IDs to save
     * @return a Call object with no content on success
     */
    @PUT("me/tracks")
    Call<Void> saveTracks(@Header("Authorization") String accessToken, @Query("ids") String trackIds);

    /**
     * Removes one or more albums from the current user's library.
     *
     * @param accessToken the OAuth access token for authorization
     * @param albumIds a comma-separated list of Spotify album IDs to remove
     * @return a Call object with no content on success
     */
    @DELETE("me/albums")
    Call<Void> deleteAlbums(@Header("Authorization") String accessToken, @Query("ids") String albumIds);

    /**
     * Removes one or more shows from the current user's library.
     *
     * @param accessToken the OAuth access token for authorization
     * @param showIds a comma-separated list of Spotify show IDs to remove
     * @param options optional query parameters such as market
     * @return a Call object with no content on success
     */
    @DELETE("me/shows")
    Call<Void> deleteShows(@Header("Authorization") String accessToken, @Query("ids") String showIds, @QueryMap Map<String, String> options);

    /**
     * Removes one or more tracks from the current user's library.
     *
     * @param accessToken the OAuth access token for authorization
     * @param trackIds a comma-separated list of Spotify track IDs to remove
     * @return a Call object with no content on success
     */
    @DELETE("me/tracks")
    Call<Void> deleteTracks(@Header("Authorization") String accessToken, @Query("ids") String trackIds);
}