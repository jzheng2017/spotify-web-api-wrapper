package spotify.api.interfaces;

import spotify.models.albums.AlbumSimplified;
import spotify.models.categories.CategoryFull;
import spotify.models.paging.Paging;
import spotify.models.playlists.FeaturedPlaylistCollection;
import spotify.models.playlists.PlaylistSimplified;
import spotify.models.recommendations.RecommendationCollection;

import java.util.List;
import java.util.Map;

public interface BrowseApi {

    CategoryFull getCategory(
            String categoryId,
            Map<String, String> options
    );

    Paging<PlaylistSimplified> getCategoryPlaylists(
            String categoryId,
            Map<String, String> options
    );

    Paging<CategoryFull> getCategories(
            Map<String, String> options
    );

    FeaturedPlaylistCollection getFeaturedPlaylists(
            Map<String, String> options
    );

    Paging<AlbumSimplified> getNewReleases(
            Map<String, String> options
    );

    RecommendationCollection getRecommendations(
            List<String> listOfSeedArtists,
            List<String> listOfSeedGenres,
            List<String> listOfSeedTracks,
            Map<String, String> options
    );
}