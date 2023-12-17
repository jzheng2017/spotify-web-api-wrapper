package spotify.api.interfaces;

import spotify.models.episodes.EpisodeFull;
import spotify.models.episodes.EpisodeFullCollection;

import java.util.List;
import java.util.Map;

public interface EpisodeApi {
    EpisodeFull getEpisode(String episodeId, Map<String, String> options);

    /**
     * This method is deprecated because it returns an unnecessary wrapper object. Use {@link #getEpisodesUnwrapped} instead;
     */
    @Deprecated(since = "1.5.8")
    EpisodeFullCollection getEpisodes(List<String> listOfEpisodeIds, Map<String, String> options);

    List<EpisodeFull> getEpisodesUnwrapped(List<String> listOfEpisodeIds, Map<String, String> options);
}
