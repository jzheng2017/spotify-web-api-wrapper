package spotify.models.episodes;

public class ResumePointObject {
    private boolean fullyPlayed;
    private int resumePositionMs;

    public boolean isFullyPlayed() {
        return fullyPlayed;
    }

    public void setFullyPlayed(boolean fullyPlayed) {
        this.fullyPlayed = fullyPlayed;
    }

    public int getResumePositionMs() {
        return resumePositionMs;
    }

    public void setResumePositionMs(int resumePositionMs) {
        this.resumePositionMs = resumePositionMs;
    }
}
