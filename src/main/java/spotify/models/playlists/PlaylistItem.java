package spotify.models.playlists;

public class PlaylistItem {
    private String uri;
    private int[] positions;

    public PlaylistItem(String uri, int[] positions) {
        this.uri = uri;
        this.positions = positions;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public int[] getPositions() {
        return positions;
    }

    public void setPositions(int[] positions) {
        this.positions = positions;
    }
}
