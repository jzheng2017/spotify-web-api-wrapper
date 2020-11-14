package spotify.api.enums;

public enum GrantType {
    CLIENT_CREDENTIALS("client_credentials"),
    AUTHORIZATION_CODE("authorization_code");

    private final String value;

    GrantType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
