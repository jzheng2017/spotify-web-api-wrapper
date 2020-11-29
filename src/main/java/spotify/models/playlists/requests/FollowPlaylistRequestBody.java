package spotify.models.playlists.requests;

import com.google.gson.annotations.SerializedName;

public class FollowPlaylistRequestBody {
    @SerializedName("public")
    private boolean setPlaylistPublic;

    public FollowPlaylistRequestBody(boolean setPlaylistPublic) {
        this.setPlaylistPublic = setPlaylistPublic;
    }

    public boolean isSetPlaylistPublic() {
        return setPlaylistPublic;
    }

    public void setSetPlaylistPublic(boolean setPlaylistPublic) {
        this.setPlaylistPublic = setPlaylistPublic;
    }
}
