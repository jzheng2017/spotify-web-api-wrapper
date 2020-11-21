package spotify.api.interfaces;

import spotify.api.enums.AlbumType;
import spotify.models.artists.ArtistFull;
import spotify.models.artists.ArtistFullCollection;
import spotify.models.artists.ArtistSimplified;
import spotify.models.paging.Paging;
import spotify.models.tracks.TrackFullCollection;

import java.util.List;
import java.util.Map;

public interface ArtistApi {
    ArtistFull getArtist(String artistId);

    Paging<ArtistSimplified> getArtistAlbums(String artistId, List<AlbumType> listOfAlbumTypes, Map<String, String> options);

    TrackFullCollection getArtistTopTracks(String artistId, Map<String, String> options);

    ArtistFullCollection getRelatedArtists(String artistId);

    ArtistFullCollection getArtists(List<String> listOfArtistIds);
}
