package spotify.api.interfaces;

import spotify.api.enums.AlbumType;
import spotify.models.albums.AlbumSimplified;
import spotify.models.artists.ArtistFull;
import spotify.models.artists.ArtistFullCollection;
import spotify.models.paging.Paging;
import spotify.models.tracks.TrackFullCollection;

import java.util.List;
import java.util.Map;

/**
 * API interface for retrieving information about one or more artists from the Spotify catalog.
 * <p>
 * For more information see: <a href="https://developer.spotify.com/documentation/web-api/reference/artists/">reference documentation</a>
 *
 * @author Jiankai Zheng (jk.zheng@hotmail.com)
 * @since 1.0.0
 */
public interface ArtistApi {
    /**
     * Get information about a single artist.
     * <p>
     * More information about how to use this see: <a href="https://developer.spotify.com/documentation/web-api/reference/artists/get-artist/">reference documentation</a>
     *
     * @param artistId the Spotify ID of the artist (ex: 3Nrfpe0tUJi4K4DXYWgMUX)
     * @return An object containing the complete information of the requested artist
     * @see ArtistFull
     */
    ArtistFull getArtist(String artistId);

    /**
     * Get information about the albums of a single artist.
     * <p>
     * More information about how to use this see: <a href="https://developer.spotify.com/documentation/web-api/reference/artists/get-artists-albums/">reference documentation</a>
     *
     * @param artistId         the Spotify ID of the artist (ex: 3Nrfpe0tUJi4K4DXYWgMUX)
     * @param listOfAlbumTypes a list of the types of albums to return (ex: single, compilation, etc)
     * @param options          the optional parameters
     * @return A paging object containing the albums of the requested artist in simplified form
     * @see AlbumSimplified
     */
    Paging<AlbumSimplified> getArtistAlbums(String artistId, List<AlbumType> listOfAlbumTypes, Map<String, String> options);

    /**
     * Get information about the top tracks of an artist.
     * <p>
     * More information about how to use this see: <a href="https://developer.spotify.com/documentation/web-api/reference/artists/get-artists-top-tracks/">reference documentation</a>
     *
     * @param artistId the Spotify ID of the artist (ex: 3Nrfpe0tUJi4K4DXYWgMUX)
     * @param options  the optional parameters
     * @return A collection object containing a list of tracks in complete form
     * @see spotify.models.tracks.TrackFull
     */
    TrackFullCollection getArtistTopTracks(String artistId, Map<String, String> options);

    /**
     * Get information about related artists.
     * <p>
     * More information about how to use this see: <a href="https://developer.spotify.com/documentation/web-api/reference/artists/get-related-artists/">reference documentation</a>
     *
     * @param artistId the Spotify ID of the artist (ex: 3Nrfpe0tUJi4K4DXYWgMUX)
     * @return A collection object containing a list of artists in complete form
     * @see ArtistFull
     */
    ArtistFullCollection getRelatedArtists(String artistId);

    /**
     * Get information about multiple artists.
     * <p>
     * More information about how to use this see: <a href="https://developer.spotify.com/documentation/web-api/reference/artists/get-several-artists/">reference documentation</a>
     *
     * @param listOfArtistIds a list of artist ids
     * @return A collection object containing a list of artists in complete form
     * @see ArtistFull
     */
    ArtistFullCollection getArtists(List<String> listOfArtistIds);
}
