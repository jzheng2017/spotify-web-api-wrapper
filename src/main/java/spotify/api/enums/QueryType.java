package spotify.api.enums;

/**
 * This class contains suitable query types used when searching Spotify.
 * <p>
 * For further information see: <a href="https://developer.spotify.com/documentation/web-api/reference/search/search/">
 * Search</a>.
 *
 * @author Jiankai Zheng (jk.zheng@hotmail.com)
 * @since 1.0.0
 */

public enum QueryType {
    ALBUM("album"),
    ARTIST("artist"),
    PLAYLIST("playlist"),
    TRACK("track"),
    SHOW("show"),
    EPISODE("episode");

    private final String value;

     /**
     * Constructs an enum value accepting a string argument whose value is the string representation of itself.
     * 
     * @param value The query type to be constructed.
     */
    QueryType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
