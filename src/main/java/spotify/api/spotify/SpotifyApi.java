package spotify.api.spotify;

import spotify.api.impl.TrackApiRetrofit;
import spotify.api.interfaces.TrackApi;
import spotify.models.audio.AudioFeatures;
import spotify.models.audio.AudioFeaturesList;
import spotify.models.tracks.TrackFull;
import spotify.models.tracks.TrackFullList;

import java.util.List;


public class SpotifyApi {
    private String accessToken;
    private TrackApi trackApi;

    public SpotifyApi() {
        setup();
    }

    public SpotifyApi(String accessToken) {
        this.accessToken = accessToken;
        setup();
    }

    public TrackFull getTrack(String trackId, String market) {
        return trackApi.getTrack(trackId, market);
    }

    public TrackFullList getTracks(List<String> listOfTrackIds, String market) {
        return trackApi.getTracks(listOfTrackIds, market);
    }

    public AudioFeatures getTrackAudioFeatures(String trackId) {
        return trackApi.getTrackAudioFeatures(trackId);
    }

    public AudioFeaturesList getTracksAudioFeatures(List<String> listOfTrackIds) {
        return trackApi.getTracksAudioFeatures(listOfTrackIds);
    }

    public String getAccessToken() {
        return accessToken;
    }

    private void setup() {
        this.trackApi = new TrackApiRetrofit(this.accessToken);
    }
}
