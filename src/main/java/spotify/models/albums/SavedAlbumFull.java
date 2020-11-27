package spotify.models.albums;

public class SavedAlbumFull {
    private String addedAt;
    private AlbumFull album;

    public String getAddedAt() {
        return addedAt;
    }

    public void setAddedAt(String addedAt) {
        this.addedAt = addedAt;
    }

    public AlbumFull getAlbum() {
        return album;
    }

    public void setAlbum(AlbumFull album) {
        this.album = album;
    }
}
