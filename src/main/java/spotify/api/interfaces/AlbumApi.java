package spotify.api.interfaces;

import spotify.models.albums.AlbumFull;
import spotify.models.albums.AlbumFullCollection;
import spotify.models.paging.Paging;
import spotify.models.tracks.TrackSimplified;

import java.util.List;
import java.util.Map;

/**
 * API interface for retrieving information about one or more albums from the Spotify catalog.
 * <p>
 * For more information see: <a href="https://developer.spotify.com/documentation/web-api/reference/albums/">reference documentation</a>
 *
 * @author Jiankai Zheng (jk.zheng@hotmail.com)
 * @since 1.0.0
 */
public interface AlbumApi {
    /**
     * Get information about a single album.
     * <p>
     * More information about how to use this see: <a href="https://developer.spotify.com/documentation/web-api/reference/albums/get-album/">reference documentation</a>
     *
     * @param albumId the spotify ID of the album (ex: 3rQmTAyMDDZ73rMyhLkn98)
     * @param options the optional parameters
     * @return An album object containing the complete information of the album
     * @see AlbumFull
     */
    AlbumFull getAlbum(String albumId, Map<String, String> options);

    /**
     * Get information about multiple albums.
     * <p>
     * More information about how to use this see: <a href="https://developer.spotify.com/documentation/web-api/reference/albums/get-several-albums/">reference documentation</a>
     *
     * @param listOfAlbumIds a list of the album ids
     * @param options        the optional parameters
     * @return A collection object containing a list of the albums that was requested
     * @see AlbumFull
     */
    AlbumFullCollection getAlbums(List<String> listOfAlbumIds, Map<String, String> options);

    /**
     * Get information about the tracks of a single album.
     * <p>
     * More information about how to use this see: <a href="https://developer.spotify.com/documentation/web-api/reference/albums/get-albums-tracks/">reference documentation</a>
     *
     * @param albumId the spotify ID of the album (ex: 3rQmTAyMDDZ73rMyhLkn98)
     * @param options the optional parameters
     * @return A paging object containing the tracks of the requested album in simplified form
     * @see TrackSimplified
     */
    Paging<TrackSimplified> getAlbumTracks(String albumId, Map<String, String> options);
}
