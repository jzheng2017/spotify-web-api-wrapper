package spotify.retrofit.services;

import retrofit2.Call;
import retrofit2.http.*;
import spotify.api.enums.EntityType;
import spotify.models.artists.ArtistFullCursorBasedPagingWrapper;
import spotify.models.playlists.requests.FollowPlaylistRequestBody;

import java.util.List;
import java.util.Map;

/**
 * Retrofit service interface for Spotify Follow API endpoints.
 * Provides methods to manage following and unfollowing artists, users, and playlists.
 */
public interface FollowService {

    /**
     * Checks if the current user follows one or more artists or users.
     *
     * @param accessToken the OAuth access token for authorization
     * @param entityType the type of entity to check (artist or user)
     * @param entityIds a comma-separated list of Spotify IDs to check
     * @return a Call object containing a list of booleans indicating follow status for each ID
     */
    @GET("me/following/contains")
    Call<List<Boolean>> isFollowing(@Header("Authorization") String accessToken, @Query("type") EntityType entityType, @Query("ids") String entityIds);

    /**
     * Checks if one or more users follow a specific playlist.
     *
     * @param accessToken the OAuth access token for authorization
     * @param playlistId the Spotify ID of the playlist
     * @param userIds a comma-separated list of Spotify user IDs to check
     * @return a Call object containing a list of booleans indicating follow status for each user
     */
    @GET("playlists/{playlist_id}/followers/contains")
    Call<List<Boolean>> isFollowingPlaylist(@Header("Authorization") String accessToken, @Path("playlist_id") String playlistId, @Query("ids") String userIds);

    /**
     * Adds the current user as a follower of one or more artists or users.
     *
     * @param accessToken the OAuth access token for authorization
     * @param entityType the type of entity to follow (artist or user)
     * @param entityIds a comma-separated list of Spotify IDs to follow
     * @return a Call object with no content on success
     */
    @PUT("me/following")
    Call<Void> followEntities(@Header("Authorization") String accessToken, @Query("type") EntityType entityType, @Query("ids") String entityIds);

    /**
     * Retrieves the artists followed by the current user.
     *
     * @param accessToken the OAuth access token for authorization
     * @param entityType the entity type, should be artist
     * @param options optional query parameters such as after and limit for pagination
     * @return a Call object containing a cursor-based paged list of followed artists
     */
    @GET("me/following")
    Call<ArtistFullCursorBasedPagingWrapper> getFollowedArtists(@Header("Authorization") String accessToken,
                                                                @Query("type") EntityType entityType,
                                                                @QueryMap Map<String, String> options);

    /**
     * Adds the current user as a follower of a playlist.
     *
     * @param accessToken the OAuth access token for authorization
     * @param playlistId the Spotify ID of the playlist to follow
     * @param setPlaylistPublic request body specifying whether to follow publicly or privately
     * @return a Call object with no content on success
     */
    @PUT("playlists/{playlist_id}/followers")
    Call<Void> followPlaylist(@Header("Authorization") String accessToken,
                              @Path("playlist_id") String playlistId,
                              @Body FollowPlaylistRequestBody setPlaylistPublic);

    /**
     * Removes the current user as a follower of one or more artists or users.
     *
     * @param accessToken the OAuth access token for authorization
     * @param entityType the type of entity to unfollow (artist or user)
     * @param entityIds a comma-separated list of Spotify IDs to unfollow
     * @return a Call object with no content on success
     */
    @DELETE("me/following")
    Call<Void> unfollowEntities(@Header("Authorization") String accessToken, @Query("type") EntityType entityType, @Query("ids") String entityIds);

    /**
     * Removes the current user as a follower of a playlist.
     *
     * @param accessToken the OAuth access token for authorization
     * @param playlistId the Spotify ID of the playlist to unfollow
     * @return a Call object with no content on success
     */
    @DELETE("playlists/{playlist_id}/followers")
    Call<Void> unfollowPlaylist(@Header("Authorization") String accessToken, @Path("playlist_id") String playlistId);
}