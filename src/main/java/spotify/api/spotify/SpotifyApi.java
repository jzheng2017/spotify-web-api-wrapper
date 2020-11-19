package spotify.api.spotify;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spotify.api.impl.AlbumApiRetrofit;
import spotify.api.impl.TrackApiRetrofit;
import spotify.api.interfaces.AlbumApi;
import spotify.api.interfaces.TrackApi;
import spotify.models.albums.AlbumFull;
import spotify.models.albums.AlbumFullList;
import spotify.models.audio.AudioAnalysis;
import spotify.models.audio.AudioFeatures;
import spotify.models.audio.AudioFeaturesList;
import spotify.models.paging.Paging;
import spotify.models.tracks.TrackFull;
import spotify.models.tracks.TrackFullList;
import spotify.models.tracks.TrackSimplified;

import java.util.List;


public class SpotifyApi {
    private final Logger logger = LoggerFactory.getLogger(SpotifyApi.class);
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
        logger.info(String.format("Requesting a track with id %s.", trackId));
        return trackApi.getTrack(trackId, market);
    }

    public TrackFullList getTracks(List<String> listOfTrackIds, String market) {
        logger.info("Requesting a list of tracks.");
        return trackApi.getTracks(listOfTrackIds, market);
    }

    public AudioFeatures getTrackAudioFeatures(String trackId) {
        logger.info(String.format("Requesting track audio features with id %s.", trackId));
        return trackApi.getTrackAudioFeatures(trackId);
    }

    public AudioFeaturesList getTracksAudioFeatures(List<String> listOfTrackIds) {
        logger.info("Requesting multiple track audio features.");
        return trackApi.getTracksAudioFeatures(listOfTrackIds);
    }

    public AudioAnalysis getTrackAudioAnalysis(String trackId) {
        logger.info(String.format("Requesting audio analysis of track id %s", trackId));
        return trackApi.getTrackAudioAnalysis(trackId);
    }

    public AlbumFull getAlbum(String albumId) {
        logger.info(String.format("Requesting an album with id %s.", albumId));
        return albumApi.getAlbum(albumId);
    }

    public AlbumFullList getAlbums(List<String> listOfAlbumIds, String market) {
        logger.info("Requesting multiple albums.");
        return albumApi.getAlbums(listOfAlbumIds, market);
    }

    public Paging<TrackSimplified> getAlbumTracks(String albumId, int limit, int offset, String market) {
        logger.info(String.format("Requesting tracks of album with id %s", albumId));
        return albumApi.getAlbumTracks(albumId, limit, offset, market);
    }

    private void setup(String accessToken) {
        logger.trace("Constructing Retrofit APIs");
        this.trackApi = new TrackApiRetrofit(accessToken);
        this.albumApi = new AlbumApiRetrofit(accessToken);
    }
}
