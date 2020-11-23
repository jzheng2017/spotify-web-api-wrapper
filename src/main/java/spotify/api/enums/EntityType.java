package spotify.api.enums;

public enum EntityType {
    ARTIST("artist"),
    USER("user");

    private final String value;

    EntityType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
