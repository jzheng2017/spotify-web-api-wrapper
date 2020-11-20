package spotify.models.episodes;

import spotify.models.generic.ExternalUrl;
import spotify.models.generic.Image;

import java.util.List;

public class EpisodeSimplified {
    private String audioPreviewUrl;
    private String description;
    private int durationMs;
    private boolean explicit;
    private ExternalUrl externalUrls;
    private String href;
    private String id;
    private List<Image> images;
    private boolean isExternallyHosted;
    private boolean isPlayable;
    private List<String> languages;
    private String name;
    private String releaseDate;
    private String releaseDatePrecision;
    private ResumePointObject resumePoint;
    private String type;
    private String uri;

    public String getAudioPreviewUrl() {
        return audioPreviewUrl;
    }

    public void setAudioPreviewUrl(String audioPreviewUrl) {
        this.audioPreviewUrl = audioPreviewUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDurationMs() {
        return durationMs;
    }

    public void setDurationMs(int durationMs) {
        this.durationMs = durationMs;
    }

    public boolean isExplicit() {
        return explicit;
    }

    public void setExplicit(boolean explicit) {
        this.explicit = explicit;
    }

    public ExternalUrl getExternalUrls() {
        return externalUrls;
    }

    public void setExternalUrls(ExternalUrl externalUrls) {
        this.externalUrls = externalUrls;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public boolean isExternallyHosted() {
        return isExternallyHosted;
    }

    public void setExternallyHosted(boolean externallyHosted) {
        isExternallyHosted = externallyHosted;
    }

    public boolean isPlayable() {
        return isPlayable;
    }

    public void setPlayable(boolean playable) {
        isPlayable = playable;
    }

    public List<String> getLanguages() {
        return languages;
    }

    public void setLanguages(List<String> languages) {
        this.languages = languages;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getReleaseDatePrecision() {
        return releaseDatePrecision;
    }

    public void setReleaseDatePrecision(String releaseDatePrecision) {
        this.releaseDatePrecision = releaseDatePrecision;
    }

    public ResumePointObject getResumePoint() {
        return resumePoint;
    }

    public void setResumePoint(ResumePointObject resumePoint) {
        this.resumePoint = resumePoint;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
