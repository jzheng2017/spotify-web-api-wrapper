package spotify.api.interfaces;

import spotify.models.albums.SavedAlbumFull;
import spotify.models.paging.Paging;
import spotify.models.shows.SavedShowSimplified;
import spotify.models.tracks.SavedTrackFull;

import java.util.List;
import java.util.Map;

public interface LibraryApi {
    List<Boolean> hasSavedAlbums(List<String> listOfAlbumIds);

    List<Boolean> hasSavedShows(List<String> listOfShowIds);

    List<Boolean> hasSavedTracks(List<String> listOfTrackIds);

    Paging<SavedAlbumFull> getSavedAlbums(Map<String, String> options);

    Paging<SavedShowSimplified> getSavedShows(Map<String, String> options);

    Paging<SavedTrackFull> getSavedTracks(Map<String, String> options);
}
