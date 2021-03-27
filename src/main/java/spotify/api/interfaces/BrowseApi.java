package spotify.api.interfaces;

import spotify.models.albums.AlbumSimplified;
import spotify.models.albums.AlbumSimplifiedPaging;
import spotify.models.categories.CategoryFull;
import spotify.models.categories.CategoryFullPaging;
import spotify.models.paging.Paging;
import spotify.models.playlists.FeaturedPlaylistCollection;
import spotify.models.playlists.PlaylistSimplified;
import spotify.models.playlists.PlaylistSimplifiedPaging;
import spotify.models.recommendations.RecommendationCollection;

import java.util.List;
import java.util.Map;

public interface BrowseApi {
    CategoryFull getCategory(String categoryId, Map<String, String> options);

    /**
     * @deprecated Replaced by {@link #getCategoryPlaylistsPaging(String, Map)} in version 1.5.8.
     */
    @Deprecated(since = "1.5.8")
    PlaylistSimplifiedPaging getCategoryPlaylists(String categoryId, Map<String, String> options);

    Paging<PlaylistSimplified> getCategoryPlaylistsPaging(String categoryId, Map<String, String> options);

    /**
     * @deprecated Replaced by {@link #getCategoriesPaging(Map)} in version 1.5.8.
     */
    @Deprecated(since = "1.5.8")
    CategoryFullPaging getCategories(Map<String, String> options);
    
    Paging<CategoryFull> getCategoriesPaging(Map<String, String> options);

    FeaturedPlaylistCollection getFeaturedPlaylists(Map<String, String> options);

    /**
     * @deprecated Replaced by {@link #getNewReleasesPaging(Map)} in version 1.5.8.
     */
    @Deprecated(since = "1.5.8")
    AlbumSimplifiedPaging getNewReleases(Map<String, String> options);

    Paging<AlbumSimplified> getNewReleasesPaging(Map<String, String> options);
    
    RecommendationCollection getRecommendations(List<String> listOfSeedArtists, List<String> listOfSeedGenres, List<String> listOfSeedTracks, Map<String, String> options);
}
