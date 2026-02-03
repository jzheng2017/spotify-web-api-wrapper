package spotify.retrofit.services;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.*;
import spotify.models.generic.Image;
import spotify.models.paging.Paging;
import spotify.models.playlists.PlaylistFull;
import spotify.models.playlists.PlaylistSimplified;
import spotify.models.playlists.PlaylistTrack;
import spotify.models.playlists.Snapshot;
import spotify.models.playlists.requests.*;

import java.util.List;
import java.util.Map;

/**
 * Retrofit service interface for Spotify Playlist API endpoints.
 * Provides methods to manage playlists, including creating, updating, and modifying playlist contents.
 */
public interface PlaylistService {

    /**
     * Retrieves the playlists owned or followed by the current user.
     *
     * @param accessToken the OAuth access token for authorization
     * @param options optional query parameters such as limit and offset
     * @return a Call object containing a paged list of simplified playlist objects
     */
    @GET("me/playlists")
    Call<Paging<PlaylistSimplified>> getPlaylists(@Header("Authorization") String accessToken, @QueryMap Map<String, String> options);

    /**
     * Retrieves the playlists owned or followed by a specific user.
     *
     * @param accessToken the OAuth access token for authorization
     * @param userId the Spotify user ID
     * @param options optional query parameters such as limit and offset
     * @return a Call object containing a paged list of simplified playlist objects
     */
    @GET("users/{user_id}/playlists")
    Call<Paging<PlaylistSimplified>> getUserPlaylists(@Header("Authorization") String accessToken,
                                                      @Path("user_id") String userId,
                                                      @QueryMap Map<String, String> options);

    /**
     * Retrieves the cover images of a specific playlist.
     *
     * @param accessToken the OAuth access token for authorization
     * @param playlistId the Spotify ID of the playlist
     * @return a Call object containing a list of cover images
     */
    @GET("playlists/{playlist_id}/images")
    Call<List<Image>> getPlaylistCoverImages(@Header("Authorization") String accessToken, @Path("playlist_id") String playlistId);

    /**
     * Retrieves detailed information about a specific playlist.
     *
     * @param accessToken the OAuth access token for authorization
     * @param playlistId the Spotify ID of the playlist
     * @param options optional query parameters such as market and fields
     * @return a Call object containing the full playlist details
     */
    @GET("playlists/{playlist_id}")
    Call<PlaylistFull> getPlaylist(@Header("Authorization") String accessToken,
                                   @Path("playlist_id") String playlistId,
                                   @QueryMap Map<String, String> options);

    /**
     * Retrieves the tracks of a specific playlist.
     *
     * @param accessToken the OAuth access token for authorization
     * @param playlistId the Spotify ID of the playlist
     * @param options optional query parameters such as market, fields, limit, and offset
     * @return a Call object containing a paged list of playlist track objects
     */
    @GET("playlists/{playlist_id}/tracks")
    Call<Paging<PlaylistTrack>> getPlaylistTracks(@Header("Authorization") String accessToken,
                                                  @Path("playlist_id") String playlistId,
                                                  @QueryMap Map<String, String> options);

    /**
     * Adds one or more items to a playlist.
     *
     * @param accessToken the OAuth access token for authorization
     * @param playlistId the Spotify ID of the playlist
     * @param requestBody the request body containing the URIs of items to add and optional position
     * @return a Call object with no content on success
     */
    @Headers({"Content-Type: application/json"})
    @POST("playlists/{playlist_id}/tracks")
    Call<Void> addItemToPlaylist(@Header("Authorization") String accessToken,
                                 @Path("playlist_id") String playlistId,
                                 @Body AddItemPlaylistRequestBody requestBody);

    /**
     * Creates a new playlist for a user.
     *
     * @param accessToken the OAuth access token for authorization
     * @param userId the Spotify user ID
     * @param requestBody the request body containing playlist details such as name, public, and description
     * @return a Call object with no content on success
     */
    @Headers({"Content-Type: application/json"})
    @POST("users/{user_id}/playlists")
    Call<Void> createPlaylist(@Header("Authorization") String accessToken,
                              @Path("user_id") String userId,
                              @Body CreateUpdatePlaylistRequestBody requestBody);

    /**
     * Updates a playlist's details such as name, public status, and description.
     *
     * @param accessToken the OAuth access token for authorization
     * @param playlistId the Spotify ID of the playlist
     * @param requestBody the request body containing the updated playlist details
     * @return a Call object with no content on success
     */
    @Headers({"Content-Type: application/json"})
    @PUT("playlists/{playlist_id}")
    Call<Void> updatePlaylist(@Header("Authorization") String accessToken,
                              @Path("playlist_id") String playlistId,
                              @Body CreateUpdatePlaylistRequestBody requestBody);

    /**
     * Reorders items in a playlist.
     *
     * @param accessToken the OAuth access token for authorization
     * @param playlistId the Spotify ID of the playlist
     * @param requestBody the request body containing the reorder parameters
     * @return a Call object containing the new snapshot ID of the playlist
     */
    @PUT("playlists/{playlist_id}/tracks")
    Call<Snapshot> reorderPlaylistItems(@Header("Authorization") String accessToken,
                                        @Path("playlist_id") String playlistId,
                                        @Body ReorderPlaylistItemsRequestBody requestBody);

    /**
     * Replaces all items in a playlist with the specified items.
     *
     * @param accessToken the OAuth access token for authorization
     * @param playlistId the Spotify ID of the playlist
     * @param requestBody the request body containing the URIs of items to set
     * @return a Call object with no content on success
     */
    @Headers({"Content-Type: application/json"})
    @PUT("playlists/{playlist_id}/tracks")
    Call<Void> replacePlaylistItems(@Header("Authorization") String accessToken,
                                    @Path("playlist_id") String playlistId,
                                    @Body ReplacePlaylistItemsRequestBody requestBody);

    /**
     * Uploads a custom cover image to a playlist.
     *
     * @param accessToken the OAuth access token for authorization
     * @param playlistId the Spotify ID of the playlist
     * @param base64EncodedJpegImage the Base64-encoded JPEG image data
     * @return a Call object with no content on success
     */
    @Headers({"Content-Type: image/jpeg"})
    @PUT("playlists/{playlist_id}/images")
    Call<Void> uploadCoverImageToPlaylist(@Header("Authorization") String accessToken,
                                          @Path("playlist_id") String playlistId,
                                          @Body RequestBody base64EncodedJpegImage);

    /**
     * Removes one or more items from a playlist.
     *
     * @param accessToken the OAuth access token for authorization
     * @param playlistId the Spotify ID of the playlist
     * @param items the request body containing the URIs of items to remove
     * @return a Call object containing the new snapshot ID of the playlist
     */
    @Headers({"Content-Type: application/json"})
    @HTTP(method = "DELETE", path = "playlists/{playlist_id}/tracks", hasBody = true)
    Call<Snapshot> deleteItemsFromPlaylist(@Header("Authorization") String accessToken, @Path("playlist_id") String playlistId, @Body DeleteItemsPlaylistRequestBody items);
}