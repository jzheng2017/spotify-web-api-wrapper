package spotify.models.playlists.requests;

import java.util.List;

public class ReplacePlaylistItemsRequestBody {
    private List<String> uris;

    public ReplacePlaylistItemsRequestBody(List<String> uris) {
        this.uris = uris;
    }

    public List<String> getUris() {
        return uris;
    }

    public void setUris(List<String> uris) {
        this.uris = uris;
    }
}
