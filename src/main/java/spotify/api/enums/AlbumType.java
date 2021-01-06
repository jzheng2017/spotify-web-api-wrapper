package spotify.api.enums;

/**
 * This class constructs the URL needed for the first step in the Authorization Code Flow.
 * Executing this step should result in a code that can be used to retrieve an access and refresh token.
 * <p>
 * For more information see: <a href="https://developer.spotify.com/documentation/general/guides/authorization-guide/#authorization-code-flow">Authorization Code Flow</a>
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
     * <p>
     * For more information, see: <a href="https://developer.spotify.com/documentation/web-api/reference-beta/#object-albumobject">Album Object</a>
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
