package spotify.api.interfaces;

import spotify.api.enums.AlbumType;
import spotify.models.artists.ArtistFull;
import spotify.models.artists.ArtistFullCollection;
import spotify.models.artists.ArtistSimplified;
import spotify.models.paging.Paging;
import spotify.models.tracks.TrackFullCollection;

import java.util.List;

public interface ArtistApi {
    ArtistFull getArtist(String artistId);

    Paging<ArtistSimplified> getArtistAlbums(String artistId, List<AlbumType> listOfAlbumTypes, String country, int limit, int offset);

    TrackFullCollection getArtistTopTracks(String artistId, String country);

    ArtistFullCollection getRelatedArtists(String artistId);
}
