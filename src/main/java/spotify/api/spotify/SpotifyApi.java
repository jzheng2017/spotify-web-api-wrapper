package spotify.api.spotify;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spotify.api.enums.AlbumType;
import spotify.api.enums.EntityType;
import spotify.api.impl.*;
import spotify.api.interfaces.*;
import spotify.models.albums.AlbumFull;
import spotify.models.albums.AlbumFullCollection;
import spotify.models.albums.AlbumSimplifiedPaging;
import spotify.models.artists.ArtistFull;
import spotify.models.artists.ArtistFullCollection;
import spotify.models.artists.ArtistFullCursorBasedPaging;
import spotify.models.artists.ArtistSimplified;
import spotify.models.audio.AudioAnalysis;
import spotify.models.audio.AudioFeatures;
import spotify.models.audio.AudioFeaturesCollection;
import spotify.models.categories.CategoryFull;
import spotify.models.categories.CategoryFullPaging;
import spotify.models.episodes.EpisodeFull;
import spotify.models.episodes.EpisodeFullCollection;
import spotify.models.episodes.EpisodeSimplified;
import spotify.models.paging.Paging;
import spotify.models.playlists.FeaturedPlaylistCollection;
import spotify.models.playlists.PlaylistSimplifiedPaging;
import spotify.models.recommendations.RecommendationCollection;
import spotify.models.shows.ShowFull;
import spotify.models.shows.ShowSimplifiedCollection;
import spotify.models.tracks.TrackFull;
import spotify.models.tracks.TrackFullCollection;
import spotify.models.tracks.TrackSimplified;
import spotify.models.users.User;

import java.util.List;
import java.util.Map;


public class SpotifyApi {
    private final Logger logger = LoggerFactory.getLogger(SpotifyApi.class);
    private TrackApi trackApi;
    private AlbumApi albumApi;
    private UserApi userApi;
    private EpisodeApi episodeApi;
    private ShowApi showApi;
    private ArtistApi artistApi;
    private BrowseApi browseApi;
    private FollowApi followApi;

    public SpotifyApi(String accessToken) {
        this.setup(accessToken);
    }

    public void setApis(TrackApi trackApi, AlbumApi albumApi) {
        this.trackApi = trackApi;
        this.albumApi = albumApi;
    }


    public TrackFull getTrack(String trackId, Map<String, String> options) {
        logger.info(String.format("Requesting a track with id %s.", trackId));
        return trackApi.getTrack(trackId, options);
    }

    public TrackFullCollection getTracks(List<String> listOfTrackIds, Map<String, String> options) {
        logger.info("Requesting a list of tracks.");
        return trackApi.getTracks(listOfTrackIds, options);
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

    public AlbumFull getAlbum(String albumId, Map<String, String> options) {
        logger.info(String.format("Requesting an album with id %s.", albumId));
        return albumApi.getAlbum(albumId, options);
    }

    public AlbumFullCollection getAlbums(List<String> listOfAlbumIds, Map<String, String> options) {
        logger.info("Requesting multiple albums.");
        return albumApi.getAlbums(listOfAlbumIds, options);
    }

    public Paging<TrackSimplified> getAlbumTracks(String albumId, Map<String, String> options) {
        logger.info(String.format("Requesting tracks of album with id %s", albumId));
        return albumApi.getAlbumTracks(albumId, options);
    }

    public User getCurrentUser() {
        logger.info("Requesting current user info");
        return userApi.getCurrentUser();
    }

    public User getUser(String userId) {
        logger.info("Request user info");
        return userApi.getUser(userId);
    }

    public EpisodeFull getEpisode(String episodeId, Map<String, String> options) {
        logger.info("Requesting episode");
        return episodeApi.getEpisode(episodeId, options);
    }

    public EpisodeFullCollection getEpisodes(List<String> listOfEpisodeIds, Map<String, String> options) {
        logger.info("Requesting multiple episodes");
        return episodeApi.getEpisodes(listOfEpisodeIds, options);
    }

    public ShowFull getShow(String showId, Map<String, String> options) {
        logger.info("Requesting show");
        return showApi.getShow(showId, options);
    }

    public Paging<EpisodeSimplified> getShowEpisodes(String showId, Map<String, String> options) {
        logger.info("Requesting show episodes");
        return showApi.getShowEpisodes(showId, options);
    }

    public ShowSimplifiedCollection getShows(List<String> listOfShowIds, Map<String, String> options) {
        logger.info("Requesting multiple shows");
        return showApi.getShows(listOfShowIds, options);
    }

    public ArtistFull getArtist(String artistId) {
        logger.info("Requesting an artist");
        return artistApi.getArtist(artistId);
    }

    public Paging<ArtistSimplified> getArtistAlbums(String artistId, List<AlbumType> listOfAlbumTypes, Map<String, String> options) {
        logger.info("Requesting albums of an artist");
        return artistApi.getArtistAlbums(artistId, listOfAlbumTypes, options);
    }

    public TrackFullCollection getArtistTopTracks(String artistId, Map<String, String> options) {
        logger.info("Requesting top tracks of an artist");
        return artistApi.getArtistTopTracks(artistId, options);
    }

    public ArtistFullCollection getRelatedArtists(String artistId) {
        logger.info("Requesting related artists");
        return artistApi.getRelatedArtists(artistId);
    }

    public ArtistFullCollection getArtists(List<String> listOfArtistIds) {
        logger.info("Requesting multiple artists");
        return artistApi.getArtists(listOfArtistIds);
    }

    public CategoryFull getCategory(String categoryId, Map<String, String> options) {
        logger.info("Requesting category");
        return browseApi.getCategory(categoryId, options);
    }

    public PlaylistSimplifiedPaging getCategoryPlaylists(String categoryId, Map<String, String> options) {
        logger.info("Requesting category playlists");
        return browseApi.getCategoryPlaylists(categoryId, options);
    }

    public CategoryFullPaging getCategories(Map<String, String> options) {
        logger.info("Requesting categories");
        return browseApi.getCategories(options);
    }

    public FeaturedPlaylistCollection getFeaturedPlaylists(Map<String, String> options) {
        logger.info("Requesting featured playlists");
        return browseApi.getFeaturedPlaylists(options);
    }

    public AlbumSimplifiedPaging getNewReleases(Map<String, String> options) {
        logger.info("Requesting new releases");
        return browseApi.getNewReleases(options);
    }

    public RecommendationCollection getRecommendations(List<String> listOfSeedArtists, List<String> listOfSeedGenres, List<String> listOfSeedTracks, Map<String, String> options) {
        logger.info("Requesting recommendations");
        return browseApi.getRecommendations(listOfSeedArtists, listOfSeedGenres, listOfSeedTracks, options);
    }

    public List<Boolean> isFollowing(EntityType entityType, List<String> listOfEntityIds) {
        logger.info("Requesting following list");
        return followApi.isFollowing(entityType, listOfEntityIds);
    }

    public List<Boolean> isFollowingPlaylist(String playlistId, List<String> listOfUserIds) {
        logger.info("Requesting following list");
        return followApi.isFollowingPlaylist(playlistId, listOfUserIds);
    }

    public void followEntities(EntityType entityType, List<String> listOfEntityIds) {
        logger.info("Requesting to follow entities");
        followApi.followEntities(entityType, listOfEntityIds);
    }

    public void followPlaylist(String playlistId, boolean setPlaylistPublic) {
        logger.info("Requesting to follow playlist");
        followApi.followPlaylist(playlistId, setPlaylistPublic);
    }

    public ArtistFullCursorBasedPaging getFollowedArtists(EntityType entityType, Map<String, String> options) {
        logger.info("Requesting current user's followed artists");
        return followApi.getFollowedArtists(entityType, options);
    }

    private void setup(String accessToken) {
        logger.trace("Constructing Retrofit APIs");
        this.trackApi = new TrackApiRetrofit(accessToken);
        this.albumApi = new AlbumApiRetrofit(accessToken);
        this.userApi = new UserApiRetrofit(accessToken);
        this.episodeApi = new EpisodeApiRetrofit(accessToken);
        this.showApi = new ShowApiRetrofit(accessToken);
        this.artistApi = new ArtistApiRetrofit(accessToken);
        this.browseApi = new BrowseApiRetrofit(accessToken);
        this.followApi = new FollowApiRetrofit(accessToken);
    }
}
