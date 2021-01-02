package spotify.api.interfaces;

import spotify.models.episodes.EpisodeFull;
import spotify.models.episodes.EpisodeFullCollection;

import java.util.List;
import java.util.Map;

/**
 * API interface for retrieving information about one or more episodes from the Spotify catalog.
 * <p>
 * More information see: <a href="https://developer.spotify.com/documentation/web-api/reference/episodes/">reference documentation</a>
 *
 * @author Jiankai Zheng (jk.zheng@hotmail.com)
 * @since 1.0.0
 */
public interface EpisodeApi {
    /**
     * Get information about a single episode.
     * <p>
     * More information about how to use this see: <a href="https://developer.spotify.com/documentation/web-api/reference/episodes/get-an-episode/">reference documentation</a>
     *
     * @param episodeId the spotify ID of the episode (ex: 6rqhFgbbKwnb9MLmUQDhG6)
     * @param options   the optional parameters
     * @return An object containing the complete information about the episode
     * @see EpisodeFull
     */
    EpisodeFull getEpisode(String episodeId, Map<String, String> options);

    /**
     * Get a list of episodes.
     * <p>
     * More information about how to use this see: <a href="https://developer.spotify.com/documentation/web-api/reference/episodes/get-several-episodes/">reference documentation</a>
     *
     * @param listOfEpisodeIds a list of spotify IDs of episodes (ex: 6rqhFgbbKwnb9MLmUQDhG6)
     * @param options          the optional parameters
     * @return A collection object containing a list of episodes in complete form
     * @see EpisodeFull
     */
    EpisodeFullCollection getEpisodes(List<String> listOfEpisodeIds, Map<String, String> options);
}
