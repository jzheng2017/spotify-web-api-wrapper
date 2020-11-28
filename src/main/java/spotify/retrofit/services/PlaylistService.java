package spotify.retrofit.services;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import spotify.models.paging.Paging;
import spotify.models.playlists.PlaylistSimplified;

import java.util.Map;

public interface PlaylistService {
    @GET("me/playlists")
    Call<Paging<PlaylistSimplified>> getPlaylists(@Header("Authorization") String accessToken, @QueryMap Map<String, String> options);

    @GET("users/{user_id}/playlists")
    Call<Paging<PlaylistSimplified>> getUserPlaylists(@Header("Authorization") String accessToken, @Path("user_id") String userId, @QueryMap Map<String, String> options);
}
