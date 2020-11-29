package spotify.models.playlists;

import java.util.List;

public class AddItemPlaylistRequestBody {
    private List<String> uris;
    private int position;

    public AddItemPlaylistRequestBody(List<String> objectUris, int startPositionToInsert) {
        this.uris = objectUris;
        this.position = startPositionToInsert;
    }

    public List<String> getUris() {
        return uris;
    }

    public void setUris(List<String> uris) {
        this.uris = uris;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
