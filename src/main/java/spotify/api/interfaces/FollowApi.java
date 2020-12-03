package spotify.api.interfaces;

import spotify.api.enums.EntityType;
import spotify.models.artists.ArtistFull;
import spotify.models.paging.CursorBasedPaging;

import java.util.List;
import java.util.Map;

public interface FollowApi {
    List<Boolean> isFollowing(EntityType entityType, List<String> listOfEntityIds);

    List<Boolean> isFollowingPlaylist(String playlistId, List<String> listOfUserIds);

    void followEntities(EntityType entityType, List<String> listOfEntityIds);

    void followPlaylist(String playlistId, boolean setPlaylistPublic);

    CursorBasedPaging<ArtistFull> getFollowedArtists(EntityType entityType, Map<String, String> options);

    void unfollowEntities(EntityType entityType, List<String> listOfEntityIds);

    void unfollowPlaylist(String playlistId);
}
