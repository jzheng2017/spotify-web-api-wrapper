package spotify.models.players;

public class Disallow {
    private boolean interruptingPlayback;
    private boolean pausing;
    private boolean resume;
    private boolean seeking;
    private boolean skippingNext;
    private boolean skippingPrev;
    private boolean togglingRepeatContext;
    private boolean togglingShuffle;
    private boolean togglingRepeatTrack;
    private boolean transferringPlayback;

    public boolean isInterruptingPlayback() {
        return interruptingPlayback;
    }

    public void setInterruptingPlayback(boolean interruptingPlayback) {
        this.interruptingPlayback = interruptingPlayback;
    }

    public boolean isPausing() {
        return pausing;
    }

    public void setPausing(boolean pausing) {
        this.pausing = pausing;
    }

    public boolean isResume() {
        return resume;
    }

    public void setResume(boolean resume) {
        this.resume = resume;
    }

    public boolean isSeeking() {
        return seeking;
    }

    public void setSeeking(boolean seeking) {
        this.seeking = seeking;
    }

    public boolean isSkippingNext() {
        return skippingNext;
    }

    public void setSkippingNext(boolean skippingNext) {
        this.skippingNext = skippingNext;
    }

    public boolean isSkippingPrev() {
        return skippingPrev;
    }

    public void setSkippingPrev(boolean skippingPrev) {
        this.skippingPrev = skippingPrev;
    }

    public boolean isTogglingRepeatContext() {
        return togglingRepeatContext;
    }

    public void setTogglingRepeatContext(boolean togglingRepeatContext) {
        this.togglingRepeatContext = togglingRepeatContext;
    }

    public boolean isTogglingShuffle() {
        return togglingShuffle;
    }

    public void setTogglingShuffle(boolean togglingShuffle) {
        this.togglingShuffle = togglingShuffle;
    }

    public boolean isTogglingRepeatTrack() {
        return togglingRepeatTrack;
    }

    public void setTogglingRepeatTrack(boolean togglingRepeatTrack) {
        this.togglingRepeatTrack = togglingRepeatTrack;
    }

    public boolean isTransferringPlayback() {
        return transferringPlayback;
    }

    public void setTransferringPlayback(boolean transferringPlayback) {
        this.transferringPlayback = transferringPlayback;
    }
}
