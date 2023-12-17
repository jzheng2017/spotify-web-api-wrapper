package spotify.api.interfaces;

import spotify.api.enums.AlbumType;
import spotify.models.albums.AlbumSimplified;
import spotify.models.artists.ArtistFull;
import spotify.models.artists.ArtistFullCollection;
import spotify.models.paging.Paging;
import spotify.models.tracks.TrackFull;
import spotify.models.tracks.TrackFullCollection;

import java.util.List;
import java.util.Map;

public interface ArtistApi {
    ArtistFull getArtist(String artistId);

    Paging<AlbumSimplified> getArtistAlbums(String artistId, List<AlbumType> listOfAlbumTypes, Map<String, String> options);

    /**
     * This method is deprecated because it returns an unnecessary wrapper object. Use {@link #getArtistTopTracksUnwrapped} instead;
     */
    @Deprecated(since = "1.5.8")
    TrackFullCollection getArtistTopTracks(String artistId, Map<String, String> options);

    List<TrackFull> getArtistTopTracksUnwrapped(String artistId, Map<String, String> options);

    /**
     * This method is deprecated because it returns an unnecessary wrapper object. Use {@link #getRelatedArtistsUnwrapped} instead;
     */
    @Deprecated(since = "1.5.8")
    ArtistFullCollection getRelatedArtists(String artistId);

    List<ArtistFull> getRelatedArtistsUnwrapped(String artistId);

    /**
     * This method is deprecated because it returns an unnecessary wrapper object. Use {@link #getArtistsUnwrapped} instead;
     */
    @Deprecated(since = "1.5.8")
    ArtistFullCollection getArtists(List<String> listOfArtistIds);

    List<ArtistFull> getArtistsUnwrapped(List<String> listOfArtistIds);
}
