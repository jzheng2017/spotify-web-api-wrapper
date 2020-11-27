package spotify.api.interfaces;

import spotify.models.albums.SavedAlbumFull;
import spotify.models.paging.Paging;

import java.util.List;
import java.util.Map;

public interface LibraryApi {
    List<Boolean> hasSavedAlbums(List<String> listOfAlbumIds);

    List<Boolean> hasSavedShows(List<String> listOfShowIds);

    List<Boolean> hasSavedTracks(List<String> listOfTrackIds);

    Paging<SavedAlbumFull> getSavedAlbums(Map<String, String> options);
}
