package spotify.retrofit.services;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.QueryMap;
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
}
