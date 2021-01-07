package spotify.api.enums;

/**
 * This class contains and sets the suitable values for the album type.
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
     * Set the type of the album: album, single, appears_on, or compilation.
     * 
     * @param value The type to set the album to.
     */
    AlbumType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
