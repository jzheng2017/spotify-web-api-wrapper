package spotify.models.tracks;

import spotify.models.albums.AlbumSimplified;
import spotify.models.artists.ArtistSimplified;
import spotify.models.generic.AbstractPlayableObject;

import java.util.List;

public class TrackFull extends AbstractPlayableObject {
    private AlbumSimplified album;
    private List<ArtistSimplified> artists;
    private List<String> availableMarkets;
    private int discNumber;
    private boolean isLocal;
    private int popularity;
    private String previewUrl;
    private int trackNumber;

    public AlbumSimplified getAlbum() {
        return album;
    }

    public void setAlbum(AlbumSimplified album) {
        this.album = album;
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

    public int getDiscNumber() {
        return discNumber;
    }

    public void setDiscNumber(int discNumber) {
        this.discNumber = discNumber;
    }

    public boolean isLocal() {
        return isLocal;
    }

    public void setLocal(boolean local) {
        isLocal = local;
    }

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    public String getPreviewUrl() {
        return previewUrl;
    }

    public void setPreviewUrl(String previewUrl) {
        this.previewUrl = previewUrl;
    }

    public int getTrackNumber() {
        return trackNumber;
    }

    public void setTrackNumber(int trackNumber) {
        this.trackNumber = trackNumber;
    }
}
