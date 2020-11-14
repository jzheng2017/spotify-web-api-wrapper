package spotify.api.spotify;

import spotify.api.impl.AlbumApiRetrofit;
import spotify.api.impl.TrackApiRetrofit;
import spotify.api.interfaces.AlbumApi;
import spotify.api.interfaces.TrackApi;
import spotify.models.albums.AlbumFull;
import spotify.models.audio.AudioAnalysis;
import spotify.models.audio.AudioFeatures;
import spotify.models.audio.AudioFeaturesList;
import spotify.models.tracks.TrackFull;
import spotify.models.tracks.TrackFullList;

import java.util.List;


public class SpotifyApi {
    private TrackApi trackApi;
    private AlbumApi albumApi;

    public SpotifyApi(String accessToken) {
        this.setup(accessToken);
    }

    public void setApis(TrackApi trackApi, AlbumApi albumApi) {
        this.trackApi = trackApi;
        this.albumApi = albumApi;
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

    public AudioAnalysis getTrackAudioAnalysis(String trackId) {
        return trackApi.getTrackAudioAnalysis(trackId);
    }

    private void setup(String accessToken) {
        this.trackApi = new TrackApiRetrofit(accessToken);
        this.albumApi = new AlbumApiRetrofit(accessToken);
    }
}
