package spotify.models.shows;

import spotify.models.episodes.EpisodeSimplified;
import spotify.models.generic.Copyright;
import spotify.models.generic.ExternalUrl;
import spotify.models.generic.Image;
import spotify.models.paging.Paging;

import java.util.List;

public class ShowFull {
    private List<String> availableMarkets;
    private List<Copyright> copyrights;
    private String description;
    private boolean explicit;
    private Paging<EpisodeSimplified> episodes;
    private ExternalUrl externalUrl;
    private String href;
    private String id;
    private List<Image> images;
    private boolean isExternallyHosted;
    private List<String> languages;
    private String mediaType;
    private String name;
    private String publisher;
    private String type;
    private String uri;
    private int totalEpisodes;

    public List<String> getAvailableMarkets() {
        return availableMarkets;
    }

    public void setAvailableMarkets(List<String> availableMarkets) {
        this.availableMarkets = availableMarkets;
    }

    public List<Copyright> getCopyrights() {
        return copyrights;
    }

    public void setCopyrights(List<Copyright> copyrights) {
        this.copyrights = copyrights;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isExplicit() {
        return explicit;
    }

    public void setExplicit(boolean explicit) {
        this.explicit = explicit;
    }

    public Paging<EpisodeSimplified> getEpisodes() {
        return episodes;
    }

    public void setEpisodes(Paging<EpisodeSimplified> episodes) {
        this.episodes = episodes;
    }

    public ExternalUrl getExternalUrl() {
        return externalUrl;
    }

    public void setExternalUrl(ExternalUrl externalUrl) {
        this.externalUrl = externalUrl;
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

    public List<String> getLanguages() {
        return languages;
    }

    public void setLanguages(List<String> languages) {
        this.languages = languages;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
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

    public int getTotalEpisodes() {
        return totalEpisodes;
    }

    public void setTotalEpisodes(int totalEpisodes) {
        this.totalEpisodes = totalEpisodes;
    }
}
