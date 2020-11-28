package spotify.api.enums;

public enum HttpStatusCode {
    OK(200),
    CREATED(201),
    NO_CONTENT(204);

    private final int httpStatusCode;

    HttpStatusCode(final int httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }

    public int toInt() {
        return this.httpStatusCode;
    }
}
