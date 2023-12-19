package spotify.api.interfaces;

import spotify.models.episodes.EpisodeFull;
import spotify.models.episodes.EpisodeFullCollection;

import java.util.List;
import java.util.Map;

public interface EpisodeApi {
    EpisodeFull getEpisode(String episodeId, Map<String, String> options);

    EpisodeFullCollection getEpisodes(List<String> listOfEpisodeIds, Map<String, String> options););
}
