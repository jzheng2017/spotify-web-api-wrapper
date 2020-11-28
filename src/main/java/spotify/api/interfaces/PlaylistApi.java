package spotify.api.interfaces;

import spotify.models.paging.Paging;
import spotify.models.playlists.PlaylistSimplified;

import java.util.Map;

public interface PlaylistApi {
    Paging<PlaylistSimplified> getPlaylists(Map<String, String> options);
}
