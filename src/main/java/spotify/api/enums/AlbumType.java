package spotify.api.enums;

/**
 * This class contains all suitable album types.
 * <p>
 * For more information, see: <a href="https://developer.spotify.com/documentation/web-api/reference/albums/">
 * Albums</a>.
 *
 * @author Jiankai Zheng (jk.zheng@hotmail.com)
 * @since 1.0.0
 */
public enum AlbumType {
    SINGLE("single"),
    ALBUM("album"),
    APPEARS_ON("appears_on"),
    COMPILATION("compilation");

    private final String value;

    /**
     * Constructs an enum value accepting a string argument whose value is the string representation of itself.
     * 
     * @param value The album type to be constructed.
     */
    AlbumType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
