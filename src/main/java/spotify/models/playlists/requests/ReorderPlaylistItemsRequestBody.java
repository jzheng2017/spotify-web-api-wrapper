package spotify.models.playlists.requests;

public class ReorderPlaylistItemsRequestBody {
    private int rangeStart;
    private int rangeLength;
    private int insertBefore;
    private String snapshotId;

    public ReorderPlaylistItemsRequestBody(int rangeStart, int rangeLength, int insertBefore, String snapshotId) {
        this.rangeStart = rangeStart;
        this.rangeLength = rangeLength;
        this.insertBefore = insertBefore;
        this.snapshotId = snapshotId;
    }

    public int getRangeStart() {
        return rangeStart;
    }

    public void setRangeStart(int rangeStart) {
        this.rangeStart = rangeStart;
    }

    public int getRangeLength() {
        return rangeLength;
    }

    public void setRangeLength(int rangeLength) {
        this.rangeLength = rangeLength;
    }

    public int getInsertBefore() {
        return insertBefore;
    }

    public void setInsertBefore(int insertBefore) {
        this.insertBefore = insertBefore;
    }

    public String getSnapshotId() {
        return snapshotId;
    }

    public void setSnapshotId(String snapshotId) {
        this.snapshotId = snapshotId;
    }
}
