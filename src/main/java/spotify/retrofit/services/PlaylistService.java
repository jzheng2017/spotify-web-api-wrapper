package spotify.retrofit.services;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import spotify.models.generic.Image;
import spotify.models.paging.Paging;
import spotify.models.playlists.PlaylistFull;
import spotify.models.playlists.PlaylistSimplified;
import spotify.models.playlists.PlaylistTrack;

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
}
