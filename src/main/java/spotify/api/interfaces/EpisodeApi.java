package spotify.api.interfaces;

import spotify.models.episodes.EpisodeFull;

public interface EpisodeApi {
    EpisodeFull getEpisode(String episodeId);
}
