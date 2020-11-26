package spotify.api.interfaces;

import java.util.List;

public interface LibraryApi {
    List<Boolean> hasSavedAlbums(List<String> listOfAlbumIds);
}
