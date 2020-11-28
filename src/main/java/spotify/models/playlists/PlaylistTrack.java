package spotify.models.playlists;

import spotify.models.users.User;

public class PlaylistTrack<T> {
    private String addedAt;
    private User addedBy;
    private boolean isLocal;
    private T track;
}
