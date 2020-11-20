package spotify.api.interfaces;

import spotify.models.audio.AudioAnalysis;
import spotify.models.audio.AudioFeatures;
import spotify.models.audio.AudioFeaturesCollection;
import spotify.models.tracks.TrackFull;
import spotify.models.tracks.TrackFullCollection;

import java.util.List;

public interface TrackApi {
    TrackFull getTrack(String trackId, String market);

    TrackFullCollection getTracks(List<String> listOfTrackIds, String market);

    AudioFeatures getTrackAudioFeatures(String trackId);

    AudioFeaturesCollection getTracksAudioFeatures(List<String> listOfTrackIds);

    AudioAnalysis getTrackAudioAnalysis(String trackId);
}
