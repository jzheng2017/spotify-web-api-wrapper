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
import spotify.models.albums.SavedAlbumFull;
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
import spotify.models.generic.Image;
import spotify.models.paging.Paging;
import spotify.models.players.DeviceCollection;
import spotify.models.playlists.*;
import spotify.models.playlists.requests.CreateUpdatePlaylistRequestBody;
import spotify.models.playlists.requests.DeleteItemsPlaylistRequestBody;
import spotify.models.playlists.requests.ReorderPlaylistItemsRequestBody;
import spotify.models.recommendations.RecommendationCollection;
import spotify.models.shows.SavedShowSimplified;
import spotify.models.shows.ShowFull;
import spotify.models.shows.ShowSimplifiedCollection;
import spotify.models.tracks.SavedTrackFull;
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
    private LibraryApi libraryApi;
    private PlaylistApi playlistApi;
    private PersonalizationApi personalizationApi;
    private PlayerApi playerApi;

    public SpotifyApi(final String accessToken) {
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

    public void unfollowEntities(EntityType entityType, List<String> listOfEntityIds) {
        logger.info("Requesting to unfollow entities");
        followApi.unfollowEntities(entityType, listOfEntityIds);
    }

    public void unfollowPlaylist(String playlistId) {
        logger.info("Requesting to unfollow playlist");
        followApi.unfollowPlaylist(playlistId);
    }

    public List<Boolean> hasSavedAlbums(List<String> listOfAlbumIds) {
        logger.info("Requesting to check saved albums");
        return libraryApi.hasSavedAlbums(listOfAlbumIds);
    }

    public List<Boolean> hasSavedShows(List<String> listOfShowIds) {
        logger.info("Requesting to check saved shows");
        return libraryApi.hasSavedAlbums(listOfShowIds);
    }

    public List<Boolean> hasSavedTracks(List<String> listOfTrackIds) {
        logger.info("Requesting to check saved tracks");
        return libraryApi.hasSavedAlbums(listOfTrackIds);
    }

    public Paging<SavedAlbumFull> getSavedAlbums(Map<String, String> options) {
        logger.info("Requesting to fetch current user's saved albums");
        return libraryApi.getSavedAlbums(options);
    }

    public Paging<SavedShowSimplified> getSavedShows(Map<String, String> options) {
        logger.info("Requesting to fetch current user's saved shows");
        return libraryApi.getSavedShows(options);
    }

    public Paging<SavedTrackFull> getSavedTracks(Map<String, String> options) {
        logger.info("Requesting to fetch current user's saved tracks");
        return libraryApi.getSavedTracks(options);
    }

    public void saveAlbums(List<String> listOfAlbumIds) {
        logger.info("Requesting to save albums");
        libraryApi.saveAlbums(listOfAlbumIds);
    }

    public void saveShows(List<String> listOfShowIds) {
        logger.info("Requesting to save shows");
        libraryApi.saveShows(listOfShowIds);
    }

    public void saveTracks(List<String> listOfTrackIds) {
        logger.info("Requesting to save tracks");
        libraryApi.saveTracks(listOfTrackIds);
    }

    public void deleteAlbums(List<String> listOfAlbumIds) {
        logger.info("Requesting to delete saved albums");
        libraryApi.deleteAlbums(listOfAlbumIds);
    }

    public void deleteShows(List<String> listOfShowIds, Map<String, String> options) {
        logger.info("Requesting to delete saved shows");
        libraryApi.deleteShows(listOfShowIds, options);
    }

    public void deleteTracks(List<String> listOfTracksIds) {
        logger.info("Requesting to delete saved tracks");
        libraryApi.deleteTracks(listOfTracksIds);
    }

    public Paging<PlaylistSimplified> getPlaylists(Map<String, String> options) {
        logger.info("Requesting to fetch current user's playlists");
        return playlistApi.getPlaylists(options);
    }

    public Paging<PlaylistSimplified> getUserPlaylists(String userId, Map<String, String> options) {
        logger.info("Requesting to fetch a user's playlists");
        return playlistApi.getUserPlaylists(userId, options);
    }

    public List<Image> getPlaylistCoverImages(String playlistId) {
        logger.info("Requesting to fetch a playlist cover images");
        return playlistApi.getPlaylistCoverImages(playlistId);
    }

    public PlaylistFull getPlaylist(String playlistId, Map<String, String> options) {
        logger.info("Requesting to fetch a playlist");
        return playlistApi.getPlaylist(playlistId, options);
    }

    public Paging<PlaylistTrack> getPlaylistTracks(String playlistId, Map<String, String> options) {
        logger.info("Requesting to fetch tracks of a playlist");
        return playlistApi.getPlaylistTracks(playlistId, options);
    }

    public void addItemsToPlaylist(List<String> listOfObjectUris, String playlistId, int startPositionToInsert) {
        logger.info("Requesting to add items to a playlist");
        playlistApi.addItemToPlaylist(listOfObjectUris, playlistId, startPositionToInsert);
    }

    public void createPlaylist(String userId, CreateUpdatePlaylistRequestBody requestBody) {
        logger.info("Requesting to create a playlist");
        playlistApi.createPlaylist(userId, requestBody);
    }


    public void updatePlaylist(String playlistId, CreateUpdatePlaylistRequestBody requestBody) {
        logger.info("Requesting to update a playlist");
        playlistApi.updatePlaylist(playlistId, requestBody);
    }

    public Snapshot reorderPlaylistItems(String playlistId, ReorderPlaylistItemsRequestBody requestBody) {
        logger.info("Requesting to reorder items of a playlist");
        return playlistApi.reorderPlaylistItems(playlistId, requestBody);
    }

    public void replacePlaylistItems(String playlistId, List<String> listOfItemUris) {
        logger.info("Requesting to replace items of a playlist");
        playlistApi.replacePlaylistItems(playlistId, listOfItemUris);
    }

    public void uploadCoverImageToPlaylist(String playlistId, String base64EncodedJpegImage) {
        logger.info("Requesting to upload cover image to a playlist");
        playlistApi.uploadCoverImageToPlaylist(playlistId, base64EncodedJpegImage);
    }

    public Snapshot deleteItemsFromPlaylist(String playlistId, String snapshotId, DeleteItemsPlaylistRequestBody items) {
        logger.info("Requesting to remove items from a playlist");
        return playlistApi.deleteItemsFromPlaylist(playlistId, snapshotId, items);
    }

    public Paging<ArtistFull> getTopArtists(Map<String, String> options) {
        logger.info("Requesting to fetch user's top artists");
        return personalizationApi.getTopArtists(options);
    }


    public Paging<TrackFull> getTopTracks(Map<String, String> options) {
        logger.info("Requesting to fetch user's top tracks");
        return personalizationApi.getTopTracks(options);
    }

    public DeviceCollection getAvailableDevices() {
        logger.info("Requesting to fetch user's available devices");
        return playerApi.getAvailableDevices();
    }

    private void setup(final String accessToken) {
        logger.trace("Constructing Retrofit APIs");
        this.trackApi = new TrackApiRetrofit(accessToken);
        this.albumApi = new AlbumApiRetrofit(accessToken);
        this.userApi = new UserApiRetrofit(accessToken);
        this.episodeApi = new EpisodeApiRetrofit(accessToken);
        this.showApi = new ShowApiRetrofit(accessToken);
        this.artistApi = new ArtistApiRetrofit(accessToken);
        this.browseApi = new BrowseApiRetrofit(accessToken);
        this.followApi = new FollowApiRetrofit(accessToken);
        this.libraryApi = new LibraryApiRetrofit(accessToken);
        this.playlistApi = new PlaylistApiRetrofit(accessToken);
        this.personalizationApi = new PersonalizationApiRetrofit(accessToken);
        this.playerApi = new PlayerApiRetrofit(accessToken);
    }
}
