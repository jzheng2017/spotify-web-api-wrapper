package spotify.api.enums;


/**
 * This class contains all suitable entity types. The entity type is used to determine what type of
 * entity a user chooses to follow.
 * <p>
 * For further information see: <a href="https://developer.spotify.com/documentation/web-api/reference/follow/">
 * Follow</a>.
 *
 * @author Jiankai Zheng (jk.zheng@hotmail.com)
 * @since 1.0.0
 */

public enum EntityType {
    ARTIST("artist"),
    USER("user");

    private final String value;

    /**
     * Constructs an enum value accepting a string argument whose value is the string representation of itself.
     * 
     * @param value The entity type to be constructed.
     */
    EntityType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
