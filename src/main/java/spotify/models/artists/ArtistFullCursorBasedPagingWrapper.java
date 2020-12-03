package spotify.models.artists;

import spotify.models.paging.CursorBasedPaging;

public class ArtistFullCursorBasedPagingWrapper {
    private CursorBasedPaging<ArtistFull> artists;

    public CursorBasedPaging<ArtistFull> getArtists() {
        return artists;
    }

    public void setArtists(CursorBasedPaging<ArtistFull> artists) {
        this.artists = artists;
    }
}
