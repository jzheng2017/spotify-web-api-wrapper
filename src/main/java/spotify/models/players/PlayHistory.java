package spotify.models.players;

import spotify.models.tracks.TrackSimplified;

public class PlayHistory {
    private TrackSimplified track;
    private String playedAt;
    private Context context;

    public TrackSimplified getTrack() {
        return track;
    }

    public void setTrack(TrackSimplified track) {
        this.track = track;
    }

    public String getPlayedAt() {
        return playedAt;
    }

    public void setPlayedAt(String playedAt) {
        this.playedAt = playedAt;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
