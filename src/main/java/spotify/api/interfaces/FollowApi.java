package spotify.api.interfaces;

import spotify.api.enums.EntityType;
import spotify.models.artists.ArtistFull;
import spotify.models.paging.CursorBasedPaging;

import java.util.List;
import java.util.Map;

/**
 * API interface for managing the artists, users, and playlists that a Spotify user follows.
 * <p>
 * More information see: <a href="https://developer.spotify.com/documentation/web-api/reference/follow/">reference documentation</a>
 *
 * @author Jiankai Zheng (jk.zheng@hotmail.com)
 * @since 1.1.0
 */
public interface FollowApi {

    /**
     * Checks to see if the current user is following one or more artists or other Spotify users.
     * <p>
     * More information about how to use this see: <a href="https://developer.spotify.com/documentation/web-api/reference/follow/check-current-user-follows/">reference documentation</a>
     *
     * @param entityType      the type to check if the user is following (ex: artist)
     * @param listOfEntityIds a list of Spotify IDs of entities (ex: 6rqhFgbbKwnb9MLmUQDhG6)
     * @return A list of booleans in the same order as in which the list of entity ids were specified (true means the entity is followed)
     */
    List<Boolean> isFollowing(EntityType entityType, List<String> listOfEntityIds);

    /**
     * Checks to see if one or more Spotify users are following a specified playlist.
     * <p>
     * More information about how to use this see: <a href="https://developer.spotify.com/documentation/web-api/reference/follow/check-user-following-playlist/">reference documentation</a>
     *
     * @param playlistId    the Spotify ID of the playlist (ex: 6rqhFgbbKwnb9MLmUQDhG6)
     * @param listOfUserIds a list of Spotify User IDs (ex: jiankai)
     * @return A list of booleans in the same order as in which the list of user ids were specified (true means the user follows the playlist)
     */
    List<Boolean> isFollowingPlaylist(String playlistId, List<String> listOfUserIds);

    /**
     * Adds the current user as a follower of one or more artists or other Spotify users
     * <p>
     * More information see: <a href="https://developer.spotify.com/documentation/web-api/reference/follow/follow-artists-users/">reference documentation</a>
     *
     * @param entityType      the type of entity to follow (ex: artist)
     * @param listOfEntityIds a list of Spotify IDs of entities (ex: 6rqhFgbbKwnb9MLmUQDhG6)
     */
    void followEntities(EntityType entityType, List<String> listOfEntityIds);

    /**
     * Adds the current user as a follower of a playlist.
     * <p>
     * More information see: <a href="https://developer.spotify.com/documentation/web-api/reference/follow/follow-playlist/">reference documentation</a>
     *
     * @param playlistId        the Spotify ID of the playlist (ex: 6rqhFgbbKwnb9MLmUQDhG6)
     * @param setPlaylistPublic specifies whether the playlist must be made public or not
     */
    void followPlaylist(String playlistId, boolean setPlaylistPublic);

    /**
     * Gets the current user’s followed artists.
     * <p>
     * More information see: <a href="https://developer.spotify.com/documentation/web-api/reference-beta/#endpoint-get-followed">reference documentation</a>
     *
     * @param entityType the entity type to get follow list from
     * @param options    the optional parameters
     * @return a cursor based paging object containing a list of artists in its complete form
     * @see ArtistFull
     */
    CursorBasedPaging<ArtistFull> getFollowedArtists(EntityType entityType, Map<String, String> options);

    /**
     * Remove the current user as a follower of one or more artists or other Spotify users.
     * <p>
     * More information see: <a href="https://developer.spotify.com/documentation/web-api/reference-beta/#endpoint-unfollow-artists-users">reference documentation</a>
     *
     * @param entityType      the entity type to unfollow
     * @param listOfEntityIds a list of Spotify IDs of entities (ex: 6rqhFgbbKwnb9MLmUQDhG6)
     */
    void unfollowEntities(EntityType entityType, List<String> listOfEntityIds);

    /**
     * Remove the current user as a follower of a playlist.
     * <p>
     * More information see: <a href="https://developer.spotify.com/documentation/web-api/reference-beta/#endpoint-unfollow-playlist">reference documentation</a>
     *
     * @param playlistId the Spotify ID of the playlist (ex: 6rqhFgbbKwnb9MLmUQDhG6)
     */
    void unfollowPlaylist(String playlistId);
}
