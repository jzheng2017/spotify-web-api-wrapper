package spotify.api.interfaces;

import spotify.models.categories.CategoryFull;
import spotify.models.categories.CategoryFullPaging;
import spotify.models.playlists.FeaturedPlaylistCollection;
import spotify.models.playlists.PlaylistSimplifiedPaging;

import java.util.Map;

public interface BrowseApi {
    CategoryFull getCategory(String categoryId, Map<String, String> options);

    PlaylistSimplifiedPaging getCategoryPlaylists(String categoryId, Map<String, String> options);

    CategoryFullPaging getCategories(Map<String, String> options);

    FeaturedPlaylistCollection getFeaturedPlaylists(Map<String, String> options);
}
