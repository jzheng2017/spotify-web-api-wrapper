package spotify.models.shows;

public class SavedShowSimplified {
    private String addedAt;
    private ShowSimplified show;

    public String getAddedAt() {
        return addedAt;
    }

    public void setAddedAt(String addedAt) {
        this.addedAt = addedAt;
    }

    public ShowSimplified getShow() {
        return show;
    }

    public void setShow(ShowSimplified show) {
        this.show = show;
    }
}
