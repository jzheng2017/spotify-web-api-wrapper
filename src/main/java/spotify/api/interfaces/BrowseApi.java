package spotify.api.interfaces;

import spotify.models.albums.AlbumSimplifiedPaging;
import spotify.models.categories.CategoryFull;
import spotify.models.categories.CategoryFullPaging;
import spotify.models.playlists.FeaturedPlaylistCollection;
import spotify.models.playlists.PlaylistSimplifiedPaging;
import spotify.models.recommendations.RecommendationCollection;

import java.util.List;
import java.util.Map;

public interface BrowseApi {
    CategoryFull getCategory(String categoryId, Map<String, String> options);

    PlaylistSimplifiedPaging getCategoryPlaylists(String categoryId, Map<String, String> options);

    CategoryFullPaging getCategories(Map<String, String> options);

    FeaturedPlaylistCollection getFeaturedPlaylists(Map<String, String> options);

    AlbumSimplifiedPaging getNewReleases(Map<String, String> options);

    RecommendationCollection getRecommendations(List<String> listOfSeedArtists, List<String> listOfSeedGenres, List<String> listOfSeedTracks, Map<String, String> options);
}
