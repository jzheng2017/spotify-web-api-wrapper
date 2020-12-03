package spotify.api.interfaces;

import spotify.api.enums.RepeatType;
import spotify.models.paging.CursorBasedPaging;
import spotify.models.players.CurrentlyPlayingObject;
import spotify.models.players.DeviceCollection;
import spotify.models.players.PlayHistory;
import spotify.models.players.PlayingContext;

import java.util.Map;

public interface PlayerApi {
    DeviceCollection getAvailableDevices();

    PlayingContext getCurrentPlayingContext(Map<String, String> options);

    CursorBasedPaging<PlayHistory> getRecentlyPlayedTracks(Map<String, String> options);

    CurrentlyPlayingObject getCurrentlyPlayedObject(Map<String, String> options);

    void addItemToQueue(String uri, Map<String, String> options);

    void skipToNextTrack(Map<String, String> options);

    void skipToPreviousTrack(Map<String, String> options);

    void pausePlayback(Map<String, String> options);

    void jumpToPositionInCurrentTrack(int positionMs, Map<String, String> options);

    void setRepeatModePlayback(RepeatType repeatType, Map<String, String> options);
}
