package spotify.api.interfaces;

import spotify.models.episodes.EpisodeFull;
import spotify.models.episodes.EpisodeFullCollection;

import java.util.List;

public interface EpisodeApi {
    EpisodeFull getEpisode(String episodeId, String market);

    EpisodeFullCollection getEpisodes(List<String> listOfEpisodeIds, String market);
}
