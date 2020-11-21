package spotify.models.playlists;

import spotify.models.paging.Paging;

public class FeaturedPlaylistCollection {
    private String message;
    private Paging<PlaylistSimplified> playlists;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Paging<PlaylistSimplified> getPlaylists() {
        return playlists;
    }

    public void setPlaylists(Paging<PlaylistSimplified> playlists) {
        this.playlists = playlists;
    }
}
