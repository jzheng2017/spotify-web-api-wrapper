package spotify.api.interfaces;

import spotify.models.albums.AlbumSimplifiedPaging;
import spotify.models.categories.CategoryFull;
import spotify.models.categories.CategoryFullPaging;
import spotify.models.playlists.FeaturedPlaylistCollection;
import spotify.models.playlists.PlaylistSimplifiedPaging;
import spotify.models.recommendations.RecommendationCollection;

import java.util.List;
import java.util.Map;

/**
 * API interface for getting playlists and new album releases featured on Spotify’s Browse tab.
 * <p>
 * More information see: <a href="https://developer.spotify.com/documentation/web-api/reference/browse/">reference documentation</a>
 *
 * @author Jiankai Zheng (jk.zheng@hotmail.com)
 * @since 1.0.0
 */
public interface BrowseApi {
    /**
     * Get information about a single category.
     * <p>
     * More information about how to use this see: <a href="https://developer.spotify.com/documentation/web-api/reference/browse/get-category/">reference documentation</a>
     *
     * @param categoryId the Spotify ID of the category (ex: kpop)
     * @param options    the optional parameters
     * @return An object containing the complete information of a category in complete form
     * @see CategoryFull
     */
    CategoryFull getCategory(String categoryId, Map<String, String> options);

    /**
     * Get playlists tagged with a specific category.
     * <p>
     * More information about how to use this see: <a href="https://developer.spotify.com/documentation/web-api/reference/browse/get-categorys-playlists/">reference documentation</a>
     *
     * @param categoryId the Spotify ID of the category (ex: kpop)
     * @param options    the optional parameters
     * @return An object containing a paged object with a list of simplified playlists
     * @see spotify.models.playlists.PlaylistSimplified
     */
    PlaylistSimplifiedPaging getCategoryPlaylists(String categoryId, Map<String, String> options);

    /**
     * Get a list of categories.
     * <p>
     * More information about how to use this see: <a href="https://developer.spotify.com/documentation/web-api/reference/browse/get-list-categories/">reference documentation</a>
     *
     * @param options the optional parameters
     * @return A paging object containing a list of categories in complete form
     * @see CategoryFull
     */
    CategoryFullPaging getCategories(Map<String, String> options);

    /**
     * Get a list of Spotify featured playlists.
     * <p>
     * More information about how to use this see: <a href="https://developer.spotify.com/documentation/web-api/reference/browse/get-list-featured-playlists/">reference documentation</a>
     *
     * @param options the optional parameters
     * @return A collection object containing a paging object of simplified playlists
     * @see spotify.models.playlists.PlaylistSimplified
     */
    FeaturedPlaylistCollection getFeaturedPlaylists(Map<String, String> options);

    /**
     * Get a list of new album releases featured in Spotify.
     * <p>
     * More information about how to use this see: <a href="https://developer.spotify.com/documentation/web-api/reference/browse/get-list-new-releases/">reference documentation</a>
     *
     * @param options the optional parameters
     * @return A paging object containing a list of simplified albums
     * @see spotify.models.albums.AlbumSimplified
     */
    AlbumSimplifiedPaging getNewReleases(Map<String, String> options);

    /**
     * Get a list of recommendation based on various seeds.
     * <p>
     * More information about how to use this see: <a href="https://developer.spotify.com/documentation/web-api/reference/browse/get-recommendations/">reference documentation</a>
     *
     * @param listOfSeedArtists a list of artist seeds
     * @param listOfSeedGenres  a list of genre seeds
     * @param listOfSeedTracks  a list of track seeds
     * @param options           the optional parameters
     * @return A collection object containing a list of recommendations and the used seeds
     * @see spotify.models.tracks.TrackSimplified
     * @see spotify.models.recommendations.RecommendationSeed
     */
    RecommendationCollection getRecommendations(List<String> listOfSeedArtists, List<String> listOfSeedGenres, List<String> listOfSeedTracks, Map<String, String> options);
}
