package spotify.models.playlists;

import spotify.models.paging.Paging;

public class PlaylistSimplifiedPaging {
    private Paging<PlaylistSimplified> playlists;

    public Paging<PlaylistSimplified> getPlaylists() {
        return playlists;
    }

    public void setPlaylists(Paging<PlaylistSimplified> playlists) {
        this.playlists = playlists;
    }
}
