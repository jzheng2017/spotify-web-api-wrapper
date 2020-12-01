package spotify.models.players;

import spotify.models.generic.ExternalUrl;

public class Context {
    private String uri;
    private String href;
    private ExternalUrl externalUrls;
    private String type;

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public ExternalUrl getExternalUrls() {
        return externalUrls;
    }

    public void setExternalUrls(ExternalUrl externalUrls) {
        this.externalUrls = externalUrls;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
