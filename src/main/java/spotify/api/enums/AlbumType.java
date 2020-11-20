package spotify.api.enums;

public enum AlbumType {
    SINGLE("single"),
    ALBUM("album"),
    APPEARS_ON("appears_on"),
    COMPILATION("compilation");

    private final String value;

    AlbumType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
