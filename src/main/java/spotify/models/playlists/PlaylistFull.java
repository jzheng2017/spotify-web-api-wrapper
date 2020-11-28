package spotify.models.playlists;

import com.google.gson.annotations.SerializedName;
import spotify.models.artists.Follower;
import spotify.models.generic.ExternalUrl;
import spotify.models.generic.Image;
import spotify.models.paging.Paging;
import spotify.models.tracks.TrackFull;
import spotify.models.users.User;

import java.util.List;

public class PlaylistFull {
    private boolean collaborative;
    private String description;
    private ExternalUrl externalUrls;
    private Follower followers;
    private String href;
    private String id;
    private List<Image> images;
    private String name;
    private User owner;
    @SerializedName("public")
    private boolean isPublic;
    private String snapshotId;
    private Paging<PlaylistTrack<TrackFull>> tracks;
    private String type;
    private String uri;

    public boolean isCollaborative() {
        return collaborative;
    }

    public void setCollaborative(boolean collaborative) {
        this.collaborative = collaborative;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ExternalUrl getExternalUrls() {
        return externalUrls;
    }

    public void setExternalUrls(ExternalUrl externalUrls) {
        this.externalUrls = externalUrls;
    }

    public Follower getFollowers() {
        return followers;
    }

    public void setFollowers(Follower followers) {
        this.followers = followers;
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

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public String getSnapshotId() {
        return snapshotId;
    }

    public void setSnapshotId(String snapshotId) {
        this.snapshotId = snapshotId;
    }

    public Paging<PlaylistTrack<TrackFull>> getTracks() {
        return tracks;
    }

    public void setTracks(Paging<PlaylistTrack<TrackFull>> tracks) {
        this.tracks = tracks;
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
