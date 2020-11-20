package spotify.api.interfaces;

import spotify.models.artists.ArtistFull;

public interface ArtistApi {
    ArtistFull getArtist(String artistId);
}
