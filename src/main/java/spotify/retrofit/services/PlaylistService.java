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
import spotify.models.playlists.requests.AddItemPlaylistRequestBody;
import spotify.models.playlists.requests.CreateUpdatePlaylistRequestBody;
import spotify.models.playlists.requests.ReorderPlaylistItemsRequestBody;
import spotify.models.playlists.requests.ReplacePlaylistItemsRequestBody;

import java.util.List;
import java.util.Map;

public interface PlaylistService {
    @GET("me/playlists")
    Call<Paging<PlaylistSimplified>> getPlaylists(@Header("Authorization") String accessToken, @QueryMap Map<String, String> options);

    @GET("users/{user_id}/playlists")
    Call<Paging<PlaylistSimplified>> getUserPlaylists(@Header("Authorization") String accessToken,
                                                      @Path("user_id") String userId,
                                                      @QueryMap Map<String, String> options);

    @GET("playlists/{playlist_id}/images")
    Call<List<Image>> getPlaylistCoverImages(@Header("Authorization") String accessToken, @Path("playlist_id") String playlistId);

    @GET("playlists/{playlist_id}")
    Call<PlaylistFull> getPlaylist(@Header("Authorization") String accessToken,
                                   @Path("playlist_id") String playlistId,
                                   @QueryMap Map<String, String> options);

    @GET("playlists/{playlist_id}/tracks")
    Call<Paging<PlaylistTrack>> getPlaylistTracks(@Header("Authorization") String accessToken,
                                                  @Path("playlist_id") String playlistId,
                                                  @QueryMap Map<String, String> options);

    @Headers({"Content-Type: application/json"})
    @POST("playlists/{playlist_id}/tracks")
    Call<Void> addItemToPlaylist(@Header("Authorization") String accessToken,
                                 @Path("playlist_id") String playlistId,
                                 @Body AddItemPlaylistRequestBody requestBody);

    @Headers({"Content-Type: application/json"})
    @POST("users/{user_id}/playlists")
    Call<Void> createPlaylist(@Header("Authorization") String accessToken,
                              @Path("user_id") String userId,
                              @Body CreateUpdatePlaylistRequestBody requestBody);

    @Headers({"Content-Type: application/json"})
    @PUT("playlists/{playlist_id}")
    Call<Void> updatePlaylist(@Header("Authorization") String accessToken,
                              @Path("playlist_id") String playlistId,
                              @Body CreateUpdatePlaylistRequestBody requestBody);

    @PUT("playlists/{playlist_id}/tracks")
    Call<Snapshot> reorderPlaylistItems(@Header("Authorization") String accessToken,
                                        @Path("playlist_id") String playlistId,
                                        @Body ReorderPlaylistItemsRequestBody requestBody);

    @Headers({"Content-Type: application/json"})
    @PUT("playlists/{playlist_id}/tracks")
    Call<Void> replacePlaylistItems(@Header("Authorization") String accessToken,
                                    @Path("playlist_id") String playlistId,
                                    @Body ReplacePlaylistItemsRequestBody requestBody);

    @Headers({"Content-Type: image/jpeg"})
    @PUT("playlists/{playlist_id}/images")
    Call<Void> uploadCoverImageToPlaylist(@Header("Authorization") String accessToken, @Path("playlist_id") String playlistId, @Body RequestBody base64EncodedJpegImage);
}
