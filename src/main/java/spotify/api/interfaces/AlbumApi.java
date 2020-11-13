package spotify.api.interfaces;

import spotify.models.albums.AlbumFull;

public interface AlbumApi {
    AlbumFull getAlbum(String albumId);
}
