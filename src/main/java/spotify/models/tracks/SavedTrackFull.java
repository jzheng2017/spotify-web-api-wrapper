package spotify.models.tracks;

public class SavedTrackFull {
    private String addedAt;
    private TrackFull track;

    public String getAddedAt() {
        return addedAt;
    }

    public void setAddedAt(String addedAt) {
        this.addedAt = addedAt;
    }

    public TrackFull getTrack() {
        return track;
    }

    public void setTrack(TrackFull track) {
        this.track = track;
    }
}
