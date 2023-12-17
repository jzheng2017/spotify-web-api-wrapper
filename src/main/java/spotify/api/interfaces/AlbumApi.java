package spotify.api.interfaces;

import spotify.models.albums.AlbumFull;
import spotify.models.albums.AlbumFullCollection;
import spotify.models.paging.Paging;
import spotify.models.tracks.TrackSimplified;

import java.util.List;
import java.util.Map;

public interface AlbumApi {
    AlbumFull getAlbum(String albumId, Map<String, String> options);

    /**
     * This method is deprecated because it returns an unnecessary wrapper object. Use {@link #getAlbumsUnwrapped} instead;
     */
    @Deprecated(since = "1.5.8")
    AlbumFullCollection getAlbums(List<String> listOfAlbumIds, Map<String, String> options);

    List<AlbumFull> getAlbumsUnwrapped(List<String> listOfAlbumIds, Map<String, String> options);

    Paging<TrackSimplified> getAlbumTracks(String albumId, Map<String, String> options);
}
