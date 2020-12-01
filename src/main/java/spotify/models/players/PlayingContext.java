package spotify.models.players;

import spotify.models.generic.AbstractPlayableObject;

public class PlayingContext {
    private Device device;
    private String repeatState;
    private boolean shuffleState;
    private Context context;
    private long timestamp;
    private int progressMs;
    private boolean isPlaying;
    private AbstractPlayableObject item;
    private String currentlyPlayingType;
    private Action actions;

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public String getRepeatState() {
        return repeatState;
    }

    public void setRepeatState(String repeatState) {
        this.repeatState = repeatState;
    }

    public boolean isShuffleState() {
        return shuffleState;
    }

    public void setShuffleState(boolean shuffleState) {
        this.shuffleState = shuffleState;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int getProgressMs() {
        return progressMs;
    }

    public void setProgressMs(int progressMs) {
        this.progressMs = progressMs;
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public void setPlaying(boolean playing) {
        isPlaying = playing;
    }

    public AbstractPlayableObject getItem() {
        return item;
    }

    public void setItem(AbstractPlayableObject item) {
        this.item = item;
    }

    public String getCurrentlyPlayingType() {
        return currentlyPlayingType;
    }

    public void setCurrentlyPlayingType(String currentlyPlayingType) {
        this.currentlyPlayingType = currentlyPlayingType;
    }

    public Action getActions() {
        return actions;
    }

    public void setActions(Action actions) {
        this.actions = actions;
    }
}
