package spotify.models.albums;

import spotify.models.paging.Paging;

public class AlbumSimplifiedPaging {
    private Paging<AlbumSimplified> albums;

    public Paging<AlbumSimplified> getAlbums() {
        return albums;
    }

    public void setAlbums(Paging<AlbumSimplified> albums) {
        this.albums = albums;
    }
}
