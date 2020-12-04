package spotify.retrofit.services;

import retrofit2.Call;
import retrofit2.http.*;
import spotify.api.enums.RepeatType;
import spotify.models.paging.CursorBasedPaging;
import spotify.models.players.CurrentlyPlayingObject;
import spotify.models.players.DeviceCollection;
import spotify.models.players.PlayHistory;
import spotify.models.players.PlayingContext;
import spotify.models.players.requests.ChangePlaybackStateRequestBody;

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

    @PUT("me/player/pause")
    Call<Void> pausePlayback(@Header("Authorization") String accessToken, @QueryMap Map<String, String> options);

    @PUT("me/player/seek")
    Call<Void> jumpToPositionInCurrentTrack(@Header("Authorization") String accessToken,
                                            @Query("position_ms") int positionMs,
                                            @QueryMap Map<String, String> options);

    @PUT("me/player/repeat")
    Call<Void> setRepeatModePlayback(@Header("Authorization") String accessToken,
                                     @Query("state") RepeatType repeatType,
                                     @QueryMap Map<String, String> options);

    @PUT("me/player/volume")
    Call<Void> setVolumePlayback(@Header("Authorization") String accessToken,
                                 @Query("volume_percent") int volumePercent,
                                 @QueryMap Map<String, String> options);

    @Headers({"Content-Type: application/json"})
    @PUT("me/player/play")
    Call<Void> changePlaybackState(@Header("Authorization") String accessToken, @Body ChangePlaybackStateRequestBody requestBody);

    @PUT("me/player/shuffle")
    Call<Void> shufflePlayback(@Header("Authorization") String accessToken,
                               @Query("state") boolean shuffle,
                               @QueryMap Map<String, String> options);
}
