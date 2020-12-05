package spotify.api.enums;

public enum QueryType {
    ALBUM("album"),
    ARTIST("artist"),
    PLAYLIST("playlist"),
    TRACK("track"),
    SHOW("show"),
    EPISODE("episode");

    private final String value;

    QueryType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
