package spotify.models.playlists.requests;

import com.google.gson.annotations.SerializedName;

public class CreateUpdatePlaylistRequestBody {
    private String name;
    private String description;
    @SerializedName("public")
    private boolean isPublic;
    private boolean collaborative;

    public CreateUpdatePlaylistRequestBody(String name, String description, boolean isPublic, boolean collaborative) {
        this.name = name;
        this.description = description;
        this.isPublic = isPublic;
        this.collaborative = collaborative;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public boolean isCollaborative() {
        return collaborative;
    }

    public void setCollaborative(boolean collaborative) {
        this.collaborative = collaborative;
    }
}
