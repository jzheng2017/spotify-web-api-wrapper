package spotify.api.interfaces;

import spotify.api.enums.EntityType;

import java.util.List;

public interface FollowApi {
    List<Boolean> isFollowing(EntityType entityType, List<String> listOfEntityIds);

    List<Boolean> isFollowingPlaylist(String playlistId, List<String> listOfUserIds);

    void followEntity(EntityType entityType, List<String> listOfEntityIds);

    void followPlaylist(String playlistId, boolean setPlaylistPublic);
}
