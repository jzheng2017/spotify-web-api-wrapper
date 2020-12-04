package spotify.models.players.requests;

import spotify.models.players.Offset;

import java.util.List;

public class ChangePlaybackStateRequestBody {
    private String contextUri;
    private List<String> uris;
    private Offset offset;
    private int positionMs;

    public ChangePlaybackStateRequestBody() {
    }

    public ChangePlaybackStateRequestBody(String contextUri, List<String> uris, Offset offset, int positionMs) {
        setContextUri(contextUri);
        setUris(uris);
        this.offset = offset;
        this.positionMs = positionMs;
    }

    public String getContextUri() {
        return contextUri;
    }

    public void setContextUri(String contextUri) {
        this.contextUri = contextUri != null && !contextUri.isEmpty() ? contextUri : null;
    }

    public List<String> getUris() {
        return uris;
    }

    public void setUris(List<String> uris) {
        this.uris = uris != null && !uris.isEmpty() ? uris : null;
    }

    public Offset getOffset() {
        return offset;
    }

    public void setOffset(Offset offset) {
        this.offset = offset;
    }

    public int getPositionMs() {
        return positionMs;
    }

    public void setPositionMs(int positionMs) {
        this.positionMs = positionMs;
    }

    @Override
    public String toString() {
        return "ChangePlaybackStateRequestBody{" +
                "contextUri='" + contextUri + '\'' +
                ", uris=" + uris +
                ", offset=" + offset +
                ", positionMs=" + positionMs +
                '}';
    }
}
