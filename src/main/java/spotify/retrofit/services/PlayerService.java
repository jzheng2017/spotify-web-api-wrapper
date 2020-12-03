package spotify.retrofit.services;

import retrofit2.Call;
import retrofit2.http.*;
import spotify.models.paging.CursorBasedPaging;
import spotify.models.players.CurrentlyPlayingObject;
import spotify.models.players.DeviceCollection;
import spotify.models.players.PlayHistory;
import spotify.models.players.PlayingContext;

import java.util.Map;

public interface PlayerService {
    @GET("me/player/devices")
    Call<DeviceCollection> getAvailableDevices(@Header("Authorization") String accessToken);

    @GET("me/player")
    Call<PlayingContext> getCurrentPlayingContext(@Header("Authorization") String accessToken, @QueryMap Map<String, String> options);

    @GET("me/player/recently-played")
    Call<CursorBasedPaging<PlayHistory>> getRecentlyPlayedTracks(@Header("Authorization") String accessToken, @QueryMap Map<String, String> options);

    @GET("me/player/currently-playing")
    Call<CurrentlyPlayingObject> getCurrentlyPlayingObject(@Header("Authorization") String accessToken, @QueryMap Map<String, String> options);

    @POST("me/player/queue")
    Call<Void> addItemToQueue(@Header("Authorization") String accessToken, @Query("uri") String uri, @QueryMap Map<String, String> options);

    @POST("me/player/next")
    Call<Void> skipToNextTrack(@Header("Authorization") String accessToken, @QueryMap Map<String, String> option);

    @POST("me/player/previous")
    Call<Void> skipToPreviousTrack(@Header("Authorization") String accessToken, @QueryMap Map<String, String> options);
}
