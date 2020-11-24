package spotify.api.interfaces;

import spotify.api.enums.EntityType;
import spotify.models.artists.ArtistFullCursorBasedPaging;

import java.util.List;
import java.util.Map;

public interface FollowApi {
    List<Boolean> isFollowing(EntityType entityType, List<String> listOfEntityIds);

    List<Boolean> isFollowingPlaylist(String playlistId, List<String> listOfUserIds);

    void followEntities(EntityType entityType, List<String> listOfEntityIds);

    void followPlaylist(String playlistId, boolean setPlaylistPublic);

    ArtistFullCursorBasedPaging getFollowedArtists(EntityType entityType, Map<String, String> options);

    void unfollowEntities(EntityType entityType, List<String> listOfEntityIds);

}
