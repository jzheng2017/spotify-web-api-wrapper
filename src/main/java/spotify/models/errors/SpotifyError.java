package spotify.models.errors;

public class SpotifyError {
    private ErrorInfo error;

    public ErrorInfo getError() {
        return error;
    }

    public void setError(ErrorInfo error) {
        this.error = error;
    }
}
