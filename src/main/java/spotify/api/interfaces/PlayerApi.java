package spotify.api.interfaces;

import spotify.models.players.DeviceCollection;

public interface PlayerApi {
    DeviceCollection getAvailableDevices();
}
