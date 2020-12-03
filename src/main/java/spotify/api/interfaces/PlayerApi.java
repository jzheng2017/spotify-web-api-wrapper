package spotify.api.interfaces;

import spotify.models.paging.CursorBasedPaging;
import spotify.models.players.DeviceCollection;
import spotify.models.players.PlayHistory;
import spotify.models.players.PlayingContext;

import java.util.Map;

public interface PlayerApi {
    DeviceCollection getAvailableDevices();

    PlayingContext getCurrentPlayingContext(Map<String, String> options);

    CursorBasedPaging<PlayHistory> getRecentlyPlayedTracks(Map<String, String> options);
}
