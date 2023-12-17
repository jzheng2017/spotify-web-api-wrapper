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
     * This method is deprecated because it returns an unnecessary wrapper object. Use {@link #getCategoryPlaylistsUnwrapped} instead;
     */
    @Deprecated(since = "1.5.8")
    PlaylistSimplifiedPaging getCategoryPlaylists(String categoryId, Map<String, String> options);

    Paging<PlaylistSimplified> getCategoryPlaylistsUnwrapped(String categoryId, Map<String, String> options);

    /**
     * This method is deprecated because it returns an unnecessary wrapper object. Use {@link #getCategoriesUnwrapped} instead;
     */
    @Deprecated(since = "1.5.8")
    CategoryFullPaging getCategories(Map<String, String> options);

    Paging<CategoryFull> getCategoriesUnwrapped(Map<String, String> options);

    FeaturedPlaylistCollection getFeaturedPlaylists(Map<String, String> options);

    /**
     * This method is deprecated because it returns an unnecessary wrapper object. Use {@link #getNewReleasesUnwrapped} instead;
     */
    @Deprecated(since = "1.5.8")
    AlbumSimplifiedPaging getNewReleases(Map<String, String> options);

    Paging<AlbumSimplified> getNewReleasesUnwrapped(Map<String, String> options);

    RecommendationCollection getRecommendations(List<String> listOfSeedArtists, List<String> listOfSeedGenres, List<String> listOfSeedTracks, Map<String, String> options);
}
