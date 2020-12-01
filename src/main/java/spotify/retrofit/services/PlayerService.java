package spotify.retrofit.services;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.QueryMap;
import spotify.models.players.DeviceCollection;
import spotify.models.players.PlayingContext;

import java.util.Map;

public interface PlayerService {
    @GET("me/player/devices")
    Call<DeviceCollection> getAvailableDevices(@Header("Authorization") String accessToken);

    @GET("me/player")
    Call<PlayingContext> getCurrentPlayingContext(@Header("Authorization") String accessToken, @QueryMap Map<String, String> options);
}
