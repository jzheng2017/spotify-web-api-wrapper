package spotify.api.interfaces;

import spotify.api.enums.RepeatType;
import spotify.models.paging.CursorBasedPaging;
import spotify.models.players.*;
import spotify.models.players.requests.ChangePlaybackStateRequestBody;
import spotify.models.players.requests.TransferPlaybackRequestBody;

import java.util.List;
import java.util.Map;

public interface PlayerApi {
    /**
     * This method is deprecated because it returns an unnecessary wrapper object. Use {@link #getAvailableDevicesUnwrapped} instead;
     */
    @Deprecated(since = "1.5.8")
    DeviceCollection getAvailableDevices();

    List<Device> getAvailableDevicesUnwrapped();

    PlayingContext getCurrentPlayingContext(Map<String, String> options);

    CursorBasedPaging<PlayHistory> getRecentlyPlayedTracks(Map<String, String> options);

    CurrentlyPlayingObject getCurrentlyPlayedObject(Map<String, String> options);

    void addItemToQueue(String uri, Map<String, String> options);

    void skipToNextTrack(Map<String, String> options);

    void skipToPreviousTrack(Map<String, String> options);

    void pausePlayback(Map<String, String> options);

    void jumpToPositionInCurrentTrack(int positionMs, Map<String, String> options);

    void setRepeatModePlayback(RepeatType repeatType, Map<String, String> options);

    void setVolumePlayback(int volumePercent, Map<String, String> options);

    void changePlaybackState(ChangePlaybackStateRequestBody requestBody);

    void shufflePlayback(boolean shuffle, Map<String, String> options);

    void transferPlayback(TransferPlaybackRequestBody requestBody);
}
