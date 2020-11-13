package spotify.api.interfaces;

import spotify.models.audio.AudioFeatures;
import spotify.models.audio.AudioFeaturesList;
import spotify.models.tracks.TrackFull;
import spotify.models.tracks.TrackFullList;

import java.util.List;

public interface TrackApi {
    TrackFull getTrack(String trackId, String market);

    TrackFullList getTracks(List<String> listOfTrackIds, String market);

    AudioFeatures getTrackAudioFeatures(String trackId);

    AudioFeaturesList getTracksAudioFeatures(List<String> listOfTrackIds);
}
