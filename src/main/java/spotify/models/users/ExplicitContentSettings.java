package spotify.models.users;

public class ExplicitContentSettings {
    private boolean filterEnabled;
    private boolean filterLocked;

    public boolean isFilterEnabled() {
        return filterEnabled;
    }

    public void setFilterEnabled(boolean filterEnabled) {
        this.filterEnabled = filterEnabled;
    }

    public boolean isFilterLocked() {
        return filterLocked;
    }

    public void setFilterLocked(boolean filterLocked) {
        this.filterLocked = filterLocked;
    }
}
