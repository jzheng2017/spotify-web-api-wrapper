package spotify.api.interfaces;

import spotify.models.albums.AlbumFull;
import spotify.models.albums.AlbumFullList;

import java.util.List;

public interface AlbumApi {
    AlbumFull getAlbum(String albumId);

    AlbumFullList getAlbums(List<String> listOfAlbumIds, String market);
}
