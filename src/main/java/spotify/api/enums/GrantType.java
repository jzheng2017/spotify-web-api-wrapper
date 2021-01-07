package spotify.api.enums;

/**
 * This class contains and sets the grant type. This is the type of user authorization being requested.
 * <p>
 * For further information see: <a href="https://developer.spotify.com/documentation/general/guides/authorization-guide/">Authorization Guide</a>
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
     * Set the grant type.
     * 
     * @param value The type of grant that is being requested.
     */
    GrantType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
