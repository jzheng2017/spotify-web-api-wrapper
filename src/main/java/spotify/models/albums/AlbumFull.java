package spotify.models.albums;

import spotify.models.artists.ArtistSimplified;
import spotify.models.generic.Copyright;
import spotify.models.generic.ExternalId;
import spotify.models.generic.ExternalUrl;
import spotify.models.generic.Image;
import spotify.models.paging.Paging;
import spotify.models.tracks.TrackSimplified;

import java.util.List;

/**
 * Class AlbumFull
 * @package spotify.models.albums
 * @author jzheng2017
 * @version 1.0.0
 * Provides the AlbumFull class with the following information:
 *          list of album types
 *          list of artists
 *          list of available markets
 *          list of copyrights
 *          list of external IDs
 *          list of genres
 *          href link
 *          id
 *          list of images
 *          album
 *          popularity ranking
 *          date of release
 *          precision for date of release
 *          tracks
 *          type
 *          uri
 */

public class AlbumFull {
    private String albumType;
    private List<ArtistSimplified> artists;
    private List<String> availableMarkets;
    private List<Copyright> copyrights;
    private ExternalId externalIds;
    private ExternalUrl externalUrls;
    private List<String> genres;
    private String href;
    private String id;
    private List<Image> images;
    private String name;
    private int popularity;
    private String releaseDate;
    private String releaseDatePrecision;
    private Paging<TrackSimplified> tracks;
    private String type;
    private String uri;

    public String getAlbumType() {
        return albumType;
    }

    public void setAlbumType(String albumType) {
        this.albumType = albumType;
    }

    public List<ArtistSimplified> getArtists() {
        return artists;
    }

    public void setArtists(List<ArtistSimplified> artists) {
        this.artists = artists;
    }

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

    public ExternalId getExternalIds() {
        return externalIds;
    }

    public void setExternalIds(ExternalId externalIds) {
        this.externalIds = externalIds;
    }

    public ExternalUrl getExternalUrls() {
        return externalUrls;
    }

    public void setExternalUrls(ExternalUrl externalUrls) {
        this.externalUrls = externalUrls;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
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

    public Paging<TrackSimplified> getTracks() {
        return tracks;
    }

    public void setTracks(Paging<TrackSimplified> tracks) {
        this.tracks = tracks;
    }
}
