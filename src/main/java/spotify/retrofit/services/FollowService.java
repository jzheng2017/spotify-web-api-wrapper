package spotify.retrofit.services;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;
import spotify.api.enums.EntityType;

import java.util.List;

public interface FollowService {
    @GET("me/following/contains")
    Call<List<Boolean>> isFollowing(@Header("Authorization") String accessToken, @Query("type") EntityType entityType, @Query("ids") String entityIds);

    @GET("playlists/{playlist_id}/followers/contains")
    Call<List<Boolean>> isFollowingPlaylist(@Header("Authorization") String accessToken, @Path("playlist_id") String playlistId, @Query("ids") String userIds);
}
