package spotify.retrofit.services;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import spotify.models.players.DeviceCollection;

public interface PlayerService {
    @GET("me/player/devices")
    Call<DeviceCollection> getAvailableDevices(@Header("Authorization") String accessToken);
}
