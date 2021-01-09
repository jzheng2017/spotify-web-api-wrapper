package spotify.api.enums;

/**
 * This class contains all suitable grant types used for authorization.
 * <p>
 * For further information see: <a href="https://developer.spotify.com/documentation/general/guides/authorization-guide/">
 * Authorization Guide</a>
 *
 * @author Jiankai Zheng (jk.zheng@hotmail.com)
 * @since 1.0.0
 */

public enum GrantType {
    CLIENT_CREDENTIALS("client_credentials"),
    AUTHORIZATION_CODE("authorization_code"),
    REFRESH_TOKEN("refresh_token");

    private final String value;

     /**
     * Constructs an enum value accepting a string argument whose value is the string representation of itself.
     * 
     * @param value The grant type to be constructed.
     */
    GrantType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
