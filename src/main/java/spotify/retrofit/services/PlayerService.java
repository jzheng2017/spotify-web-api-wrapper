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
import spotify.models.players.requests.TransferPlaybackRequestBody;

import java.util.Map;

/**
 * Retrofit service interface for Spotify Player API endpoints.
 * Provides methods to control playback, retrieve playback state, and manage devices.
 */
public interface PlayerService {

    /**
     * Retrieves the list of devices available for playback.
     *
     * @param accessToken the OAuth access token for authorization
     * @return a Call object containing the collection of available devices
     */
    @GET("me/player/devices")
    Call<DeviceCollection> getAvailableDevices(@Header("Authorization") String accessToken);

    /**
     * Retrieves the current playback context including track, device, and playback state.
     *
     * @param accessToken the OAuth access token for authorization
     * @param options optional query parameters such as market and additional_types
     * @return a Call object containing the current playing context
     */
    @GET("me/player")
    Call<PlayingContext> getCurrentPlayingContext(@Header("Authorization") String accessToken, @QueryMap Map<String, String> options);

    /**
     * Retrieves the user's recently played tracks.
     *
     * @param accessToken the OAuth access token for authorization
     * @param options optional query parameters such as limit, after, and before for pagination
     * @return a Call object containing a cursor-based paged list of play history items
     */
    @GET("me/player/recently-played")
    Call<CursorBasedPaging<PlayHistory>> getRecentlyPlayedTracks(@Header("Authorization") String accessToken, @QueryMap Map<String, String> options);

    /**
     * Retrieves the currently playing track or episode.
     *
     * @param accessToken the OAuth access token for authorization
     * @param options optional query parameters such as market and additional_types
     * @return a Call object containing the currently playing object
     */
    @GET("me/player/currently-playing")
    Call<CurrentlyPlayingObject> getCurrentlyPlayingObject(@Header("Authorization") String accessToken, @QueryMap Map<String, String> options);

    /**
     * Adds an item to the user's playback queue.
     *
     * @param accessToken the OAuth access token for authorization
     * @param uri the Spotify URI of the item to add (track or episode)
     * @param options optional query parameters such as device_id
     * @return a Call object with no content on success
     */
    @POST("me/player/queue")
    Call<Void> addItemToQueue(@Header("Authorization") String accessToken, @Query("uri") String uri, @QueryMap Map<String, String> options);

    /**
     * Skips to the next track in the user's queue.
     *
     * @param accessToken the OAuth access token for authorization
     * @param option optional query parameters such as device_id
     * @return a Call object with no content on success
     */
    @POST("me/player/next")
    Call<Void> skipToNextTrack(@Header("Authorization") String accessToken, @QueryMap Map<String, String> option);

    /**
     * Skips to the previous track in the user's queue.
     *
     * @param accessToken the OAuth access token for authorization
     * @param options optional query parameters such as device_id
     * @return a Call object with no content on success
     */
    @POST("me/player/previous")
    Call<Void> skipToPreviousTrack(@Header("Authorization") String accessToken, @QueryMap Map<String, String> options);

    /**
     * Pauses the current playback.
     *
     * @param accessToken the OAuth access token for authorization
     * @param options optional query parameters such as device_id
     * @return a Call object with no content on success
     */
    @PUT("me/player/pause")
    Call<Void> pausePlayback(@Header("Authorization") String accessToken, @QueryMap Map<String, String> options);

    /**
     * Seeks to a specific position in the currently playing track.
     *
     * @param accessToken the OAuth access token for authorization
     * @param positionMs the position in milliseconds to seek to
     * @param options optional query parameters such as device_id
     * @return a Call object with no content on success
     */
    @PUT("me/player/seek")
    Call<Void> jumpToPositionInCurrentTrack(@Header("Authorization") String accessToken,
                                            @Query("position_ms") int positionMs,
                                            @QueryMap Map<String, String> options);

    /**
     * Sets the repeat mode for the user's playback.
     *
     * @param accessToken the OAuth access token for authorization
     * @param repeatType the repeat mode (track, context, or off)
     * @param options optional query parameters such as device_id
     * @return a Call object with no content on success
     */
    @PUT("me/player/repeat")
    Call<Void> setRepeatModePlayback(@Header("Authorization") String accessToken,
                                     @Query("state") RepeatType repeatType,
                                     @QueryMap Map<String, String> options);

    /**
     * Sets the volume for the user's current playback device.
     *
     * @param accessToken the OAuth access token for authorization
     * @param volumePercent the volume level (0-100)
     * @param options optional query parameters such as device_id
     * @return a Call object with no content on success
     */
    @PUT("me/player/volume")
    Call<Void> setVolumePlayback(@Header("Authorization") String accessToken,
                                 @Query("volume_percent") int volumePercent,
                                 @QueryMap Map<String, String> options);

    /**
     * Starts or resumes playback with optional context and offset.
     *
     * @param accessToken the OAuth access token for authorization
     * @param requestBody the request body containing playback options such as context URI and offset
     * @return a Call object with no content on success
     */
    @Headers({"Content-Type: application/json"})
    @PUT("me/player/play")
    Call<Void> changePlaybackState(@Header("Authorization") String accessToken, @Body ChangePlaybackStateRequestBody requestBody);

    /**
     * Toggles shuffle mode for the user's playback.
     *
     * @param accessToken the OAuth access token for authorization
     * @param shuffle true to enable shuffle, false to disable
     * @param options optional query parameters such as device_id
     * @return a Call object with no content on success
     */
    @PUT("me/player/shuffle")
    Call<Void> shufflePlayback(@Header("Authorization") String accessToken,
                               @Query("state") boolean shuffle,
                               @QueryMap Map<String, String> options);

    /**
     * Transfers playback to a different device.
     *
     * @param accessToken the OAuth access token for authorization
     * @param requestBody the request body containing the target device ID and play state
     * @return a Call object with no content on success
     */
    @PUT("me/player")
    Call<Void> transferPlayback(@Header("Authorization") String accessToken, @Body TransferPlaybackRequestBody requestBody);
}