package spotify.api.enums;


/**
 * This class contains and sets the suitable values for the entity type. The entity
 * type is used when a user follows either another user or an artist.
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
     * Set the entity type.
     * 
     * @param value The type of entity the user is following.
     */
    EntityType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
