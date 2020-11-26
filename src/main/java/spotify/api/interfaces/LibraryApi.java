package spotify.api.interfaces;

import java.util.List;

public interface LibraryApi {
    List<Boolean> hasSavedAlbums(List<String> listOfAlbumIds);

    List<Boolean> hasSavedShows(List<String> listOfShowIds);

    List<Boolean> hasSavedTracks(List<String> listOfTrackIds);
}
