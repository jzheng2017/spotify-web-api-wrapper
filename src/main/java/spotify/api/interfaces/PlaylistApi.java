package spotify.api.interfaces;

import spotify.models.paging.Paging;
import spotify.models.playlists.PlaylistSimplified;

import java.util.Map;

public interface PlaylistApi {
    Paging<PlaylistSimplified> getPlaylists(Map<String, String> options);

    Paging<PlaylistSimplified> getUserPlaylists(String userId, Map<String, String> options);
}
