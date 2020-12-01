package spotify.api.interfaces;

import spotify.models.players.DeviceCollection;
import spotify.models.players.PlayingContext;

import java.util.Map;

public interface PlayerApi {
    DeviceCollection getAvailableDevices();

    PlayingContext getCurrentPlayingContext(Map<String, String> options);
}
