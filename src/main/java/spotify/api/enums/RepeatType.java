package spotify.api.enums;

public enum RepeatType {
    TRACK("track"),
    CONTEXT("context"),
    OFF("off");

    private final String value;

    RepeatType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
