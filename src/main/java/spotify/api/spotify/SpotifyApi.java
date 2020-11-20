package spotify.api.spotify;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spotify.api.impl.*;
import spotify.api.interfaces.*;
import spotify.models.albums.AlbumFull;
import spotify.models.albums.AlbumFullCollection;
import spotify.models.artists.ArtistFull;
import spotify.models.audio.AudioAnalysis;
import spotify.models.audio.AudioFeatures;
import spotify.models.audio.AudioFeaturesCollection;
import spotify.models.episodes.EpisodeFull;
import spotify.models.episodes.EpisodeFullCollection;
import spotify.models.episodes.EpisodeSimplified;
import spotify.models.paging.Paging;
import spotify.models.shows.ShowFull;
import spotify.models.shows.ShowSimplifiedCollection;
import spotify.models.tracks.TrackFull;
import spotify.models.tracks.TrackFullCollection;
import spotify.models.tracks.TrackSimplified;
import spotify.models.users.User;

import java.util.List;


public class SpotifyApi {
    private final Logger logger = LoggerFactory.getLogger(SpotifyApi.class);
    private TrackApi trackApi;
    private AlbumApi albumApi;
    private UserApi userApi;
    private EpisodeApi episodeApi;
    private ShowApi showApi;
    private ArtistApi artistApi;

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

    public TrackFullCollection getTracks(List<String> listOfTrackIds, String market) {
        logger.info("Requesting a list of tracks.");
        return trackApi.getTracks(listOfTrackIds, market);
    }

    public AudioFeatures getTrackAudioFeatures(String trackId) {
        logger.info(String.format("Requesting track audio features with id %s.", trackId));
        return trackApi.getTrackAudioFeatures(trackId);
    }

    public AudioFeaturesCollection getTracksAudioFeatures(List<String> listOfTrackIds) {
        logger.info("Requesting multiple track audio features.");
        return trackApi.getTracksAudioFeatures(listOfTrackIds);
    }

    public AudioAnalysis getTrackAudioAnalysis(String trackId) {
        logger.info(String.format("Requesting audio analysis of track id %s", trackId));
        return trackApi.getTrackAudioAnalysis(trackId);
    }

    public AlbumFull getAlbum(String albumId, String market) {
        logger.info(String.format("Requesting an album with id %s.", albumId));
        return albumApi.getAlbum(albumId, market);
    }

    public AlbumFullCollection getAlbums(List<String> listOfAlbumIds, String market) {
        logger.info("Requesting multiple albums.");
        return albumApi.getAlbums(listOfAlbumIds, market);
    }

    public Paging<TrackSimplified> getAlbumTracks(String albumId, int limit, int offset, String market) {
        logger.info(String.format("Requesting tracks of album with id %s", albumId));
        return albumApi.getAlbumTracks(albumId, limit, offset, market);
    }

    public User getCurrentUser() {
        logger.info("Requesting current user info");
        return userApi.getCurrentUser();
    }

    public User getUser(String userId) {
        logger.info("Request user info");
        return userApi.getUser(userId);
    }

    public EpisodeFull getEpisode(String episodeId, String market) {
        logger.info("Requesting episode");
        return episodeApi.getEpisode(episodeId, market);
    }

    public EpisodeFullCollection getEpisodes(List<String> listOfEpisodeIds, String market) {
        logger.info("Requesting multiple episodes");
        return episodeApi.getEpisodes(listOfEpisodeIds, market);
    }

    public ShowFull getShow(String showId, String market) {
        logger.info("Requesting show");
        return showApi.getShow(showId, market);
    }

    public Paging<EpisodeSimplified> getShowEpisodes(String showId, int limit, int offset, String market) {
        logger.info("Requesting show episodes");
        return showApi.getShowEpisodes(showId, limit, offset, market);
    }

    public ShowSimplifiedCollection getShows(List<String> listOfShowIds, String market) {
        logger.info("Requesting multiple shows");
        return showApi.getShows(listOfShowIds, market);
    }

    public ArtistFull getArtist(String artistId) {
        logger.info("Requesting an artist");
        return artistApi.getArtist(artistId);
    }

    private void setup(String accessToken) {
        logger.trace("Constructing Retrofit APIs");
        this.trackApi = new TrackApiRetrofit(accessToken);
        this.albumApi = new AlbumApiRetrofit(accessToken);
        this.userApi = new UserApiRetrofit(accessToken);
        this.episodeApi = new EpisodeApiRetrofit(accessToken);
        this.showApi = new ShowApiRetrofit(accessToken);
        this.artistApi = new ArtistApiRetrofit(accessToken);
    }
}
