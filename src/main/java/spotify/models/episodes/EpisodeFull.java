package spotify.models.episodes;

import spotify.models.generic.AbstractPlayableObject;
import spotify.models.generic.Image;
import spotify.models.shows.ShowSimplified;

import java.util.List;

public class EpisodeFull extends AbstractPlayableObject {
    private String audioPreviewUrl;
    private String description;
    private List<Image> images;
    private boolean isExternallyHosted;
    private List<String> languages;
    private String releaseDate;
    private String releaseDatePrecision;
    private ResumePointObject resumePoint;
    private ShowSimplified show;

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

    public List<String> getLanguages() {
        return languages;
    }

    public void setLanguages(List<String> languages) {
        this.languages = languages;
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

    public ShowSimplified getShow() {
        return show;
    }

    public void setShow(ShowSimplified show) {
        this.show = show;
    }
}
