package spotify.api.interfaces;

import spotify.models.albums.SavedAlbumFull;
import spotify.models.paging.Paging;
import spotify.models.shows.SavedShowSimplified;
import spotify.models.tracks.SavedTrackFull;

import java.util.List;
import java.util.Map;

/**
 * An API interface for managing the user's library such as (saved) albums, shows and tracks.
 * <p>
 * More information see: <a href="https://developer.spotify.com/documentation/web-api/reference/#category-library">reference documentation</a>
 *
 * @author Jiankai Zheng (jk.zheng@hotmail.com)
 * @since 1.2.0
 */
public interface LibraryApi {
    /**
     * Check if one or more albums is already saved in the current Spotify user’s ‘Your Music’ library.
     * <p>
     * More information about how to use this see: <a href="https://developer.spotify.com/documentation/web-api/reference/#endpoint-check-users-saved-albums">reference documentation</a>
     *
     * @param listOfAlbumIds a list of album ids
     * @return a list of boolean values in the same order in which the ids were specified
     */
    List<Boolean> hasSavedAlbums(List<String> listOfAlbumIds);

    /**
     * Check if one or more shows is already saved in the current Spotify user’s library.
     * <p>
     * More information about how to use this see: <a href="https://developer.spotify.com/documentation/web-api/reference/#endpoint-check-users-saved-shows">reference documentation</a>
     *
     * @param listOfShowIds a list of show ids
     * @return a list of boolean values in the same order in which the ids were specified
     */
    List<Boolean> hasSavedShows(List<String> listOfShowIds);

    /**
     * Check if one or more tracks is already saved in the current Spotify user’s library.
     * <p>
     * More information about how to use this see: <a href="https://developer.spotify.com/documentation/web-api/reference/#endpoint-check-users-saved-tracks">reference documentation</a>
     *
     * @param listOfTrackIds a list of track ids
     * @return a list of boolean values in the same order in which the ids were specified
     */
    List<Boolean> hasSavedTracks(List<String> listOfTrackIds);

    /**
     * Get a list of the albums saved in the current Spotify user’s ‘Your Music’ library.
     * <p>
     * More information about how to use this see: <a href="https://developer.spotify.com/documentation/web-api/reference/#endpoint-get-users-saved-albums">reference documentation</a>
     *
     * @param options the optional parameters
     * @return a paging object containing a list of saved albums objects
     * @see SavedAlbumFull
     */
    Paging<SavedAlbumFull> getSavedAlbums(Map<String, String> options);

    /**
     * Get a list of shows saved in the current Spotify user’s library.
     * <p>
     * More information about how to use this see: <a href="https://developer.spotify.com/documentation/web-api/reference/#endpoint-get-users-saved-shows">reference documentation</a>
     *
     * @param options the optional parameters
     * @return a paging object containing a list of saved shows objects in simplified form
     * @see SavedShowSimplified
     */
    Paging<SavedShowSimplified> getSavedShows(Map<String, String> options);

    /**
     * Get a list of the songs saved in the current Spotify user’s ‘Your Music’ library.
     * <p>
     * More information about how to use this see: <a href="https://developer.spotify.com/documentation/web-api/reference/#endpoint-get-users-saved-tracks">reference documentation</a>
     *
     * @param options the optional parameters
     * @return a paging object containing a list of saved track objects
     * @see SavedTrackFull
     */
    Paging<SavedTrackFull> getSavedTracks(Map<String, String> options);

    /**
     * Save one or more albums to the current user’s ‘Your Music’ library.
     * <p>
     * More information about how to use this see: <a href="https://developer.spotify.com/documentation/web-api/reference/#endpoint-save-albums-user>reference documentation</a>
     *
     * @param listOfAlbumIds a list of album ids to save
     */
    void saveAlbums(List<String> listOfAlbumIds);

    /**
     * Save one or more shows to current Spotify user’s library.
     * <p>
     * More information about how to use this see: <a href="https://developer.spotify.com/documentation/web-api/reference/#endpoint-save-shows-user">reference documentation</a>
     *
     * @param listOfShowIds a list of show ids to save
     */
    void saveShows(List<String> listOfShowIds);

    /**
     * Save one or more tracks to the current user’s ‘Your Music’ library.
     * <p>
     * More information about how to use this see: <a href="https://developer.spotify.com/documentation/web-api/reference/#endpoint-save-tracks-user">reference documentation</a>
     *
     * @param listOfTrackIds a list of track ids to save
     */
    void saveTracks(List<String> listOfTrackIds);

    /**
     * Remove one or more albums from the current user’s ‘Your Music’ library.
     * <p>
     * More information about how to use this see: <a href="https://developer.spotify.com/documentation/web-api/reference/#endpoint-remove-albums-user">reference documentation</a>
     *
     * @param listOfAlbumIds a list of album ids to remove
     */
    void deleteAlbums(List<String> listOfAlbumIds);

    /**
     * Delete one or more shows from current Spotify user’s library.
     * <p>
     * More information about how to use this see: <a href="https://developer.spotify.com/documentation/web-api/reference/#endpoint-remove-shows-user">reference documentation</a>
     *
     * @param listOfShowIds a list of show ids to remove
     * @param options       the optional parameters
     */
    void deleteShows(List<String> listOfShowIds, Map<String, String> options);

    /**
     * Remove one or more tracks from the current user’s ‘Your Music’ library.
     * <p>
     * More information about how to use this see: <a href="https://developer.spotify.com/documentation/web-api/reference/#endpoint-remove-tracks-user">reference documentation</a>
     *
     * @param listOfTrackIds a list of track ids to remove
     */
    void deleteTracks(List<String> listOfTrackIds);
}
