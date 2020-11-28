package spotify.models.playlists;

import spotify.models.generic.AbstractPlayableObject;
import spotify.models.users.User;

public class PlaylistTrack {
    private String addedAt;
    private User addedBy;
    private boolean isLocal;
    private AbstractPlayableObject track;

    public String getAddedAt() {
        return addedAt;
    }

    public void setAddedAt(String addedAt) {
        this.addedAt = addedAt;
    }

    public User getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(User addedBy) {
        this.addedBy = addedBy;
    }

    public boolean isLocal() {
        return isLocal;
    }

    public void setLocal(boolean local) {
        isLocal = local;
    }

    public AbstractPlayableObject getTrack() {
        return track;
    }

    public void setTrack(AbstractPlayableObject track) {
        this.track = track;
    }
}
