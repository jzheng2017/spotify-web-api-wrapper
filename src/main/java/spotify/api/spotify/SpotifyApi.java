package spotify.api.spotify;

import spotify.api.impl.AlbumApiRetrofit;
import spotify.api.impl.TrackApiRetrofit;
import spotify.api.interfaces.AlbumApi;
import spotify.api.interfaces.TrackApi;
import spotify.models.albums.AlbumFull;
import spotify.models.audio.AudioFeatures;
import spotify.models.audio.AudioFeaturesList;
import spotify.models.tracks.TrackFull;
import spotify.models.tracks.TrackFullList;

import java.util.List;


public class SpotifyApi {
    private final String accessToken;
    private TrackApi trackApi;
    private AlbumApi albumApi;

    public SpotifyApi(String accessToken) {
        this.accessToken = accessToken;
        this.setup();
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

    public AlbumFull getAlbum(String albumId) {
        return albumApi.getAlbum(albumId);
    }

    public String getAccessToken() {
        return accessToken;
    }

    private void setup() {
        this.trackApi = new TrackApiRetrofit(this.accessToken);
        this.albumApi = new AlbumApiRetrofit(this.accessToken);
    }
}
