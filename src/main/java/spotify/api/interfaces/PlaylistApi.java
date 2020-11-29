package spotify.api.interfaces;

import spotify.models.generic.Image;
import spotify.models.paging.Paging;
import spotify.models.playlists.PlaylistFull;
import spotify.models.playlists.PlaylistSimplified;
import spotify.models.playlists.PlaylistTrack;
import spotify.models.playlists.Snapshot;
import spotify.models.playlists.requests.CreateUpdatePlaylistRequestBody;
import spotify.models.playlists.requests.ReorderPlaylistItemsRequestBody;

import java.util.List;
import java.util.Map;

public interface PlaylistApi {
    Paging<PlaylistSimplified> getPlaylists(Map<String, String> options);

    Paging<PlaylistSimplified> getUserPlaylists(String userId, Map<String, String> options);

    List<Image> getPlaylistCoverImages(String playlistId);

    PlaylistFull getPlaylist(String playlistId, Map<String, String> options);

    Paging<PlaylistTrack> getPlaylistTracks(String playlistId, Map<String, String> options);

    void addItemToPlaylist(List<String> listOfObjectUris, String playlistId, int startPositionToInsert);

    void createPlaylist(String userId, CreateUpdatePlaylistRequestBody requestBody);

    void updatePlaylist(String playlistId, CreateUpdatePlaylistRequestBody requestBody);

    Snapshot reorderPlaylistItems(String playlistId, ReorderPlaylistItemsRequestBody requestBody);

    void replacePlaylistItems(String playlistId, List<String> listOfItemUris);

    void uploadCoverImageToPlaylist(String playlistId, String base64EncodedJpegImage);
}
