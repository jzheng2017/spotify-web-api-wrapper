package spotify.api.interfaces;

import spotify.models.categories.CategoryFull;
import spotify.models.playlists.PlaylistSimplifiedPaging;

public interface BrowseApi {
    CategoryFull getCategory(String categoryId, String country, String locale);

    PlaylistSimplifiedPaging getCategoryPlaylists(String categoryId, String country, int limit, int offset);
}
