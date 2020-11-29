package spotify.models.playlists.requests;

import spotify.models.playlists.PlaylistItem;

import java.util.List;

public class DeleteItemsPlaylistRequestBody {
    List<PlaylistItem> tracks;

    public DeleteItemsPlaylistRequestBody(List<PlaylistItem> tracks) {
        this.tracks = tracks;
    }

    public List<PlaylistItem> getTracks() {
        return tracks;
    }

    public void setTracks(List<PlaylistItem> tracks) {
        this.tracks = tracks;
    }
}
