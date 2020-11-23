package spotify.api.interfaces;

import spotify.api.enums.EntityType;

import java.util.List;

public interface FollowApi {
    List<Boolean> isFollowing(EntityType entityType, List<String> listOfEntityIds);
}
