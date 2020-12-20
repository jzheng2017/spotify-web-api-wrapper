package spotify.models.playlists.requests;

import spotify.models.playlists.PlaylistItem;

import java.util.List;

public class DeleteItemsPlaylistRequestBody {
    private List<PlaylistItem> tracks;
    private String snapshotId;

    public DeleteItemsPlaylistRequestBody(List<PlaylistItem> tracks, String snapshotId) {
        this.tracks = tracks;
        this.snapshotId = snapshotId;
    }

    public List<PlaylistItem> getTracks() {
        return tracks;
    }

    public void setTracks(List<PlaylistItem> tracks) {
        this.tracks = tracks;
    }

    public String getSnapshotId() {
        return snapshotId;
    }

    public void setSnapshotId(String snapshotId) {
        this.snapshotId = snapshotId;
    }
}
