package spotify.api.interfaces;

import spotify.models.albums.AlbumFull;
import spotify.models.albums.AlbumFullList;
import spotify.models.paging.Paging;
import spotify.models.tracks.TrackSimplified;

import java.util.List;

public interface AlbumApi {
    AlbumFull getAlbum(String albumId);

    AlbumFullList getAlbums(List<String> listOfAlbumIds, String market);

    Paging<TrackSimplified> getAlbumTracks(String albumId, int limit, int offset, String market);
}
