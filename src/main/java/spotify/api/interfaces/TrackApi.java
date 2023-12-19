package spotify.api.interfaces;

import spotify.models.audio.AudioAnalysis;
import spotify.models.audio.AudioFeatures;
import spotify.models.audio.AudioFeaturesCollection;
import spotify.models.tracks.TrackFull;
import spotify.models.tracks.TrackFullCollection;

import java.util.List;
import java.util.Map;

public interface TrackApi {
    TrackFull getTrack(String trackId, Map<String, String> options);

    TrackFullCollection getTracks(List<String> listOfTrackIds, Map<String, String> options);

    AudioFeatures getTrackAudioFeatures(String trackId);

    AudioFeaturesCollection getTracksAudioFeatures(List<String> listOfTrackIds);

    AudioAnalysis getTrackAudioAnalysis(String trackId);
}
