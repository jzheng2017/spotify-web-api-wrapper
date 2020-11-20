package spotify.api.interfaces;

import spotify.models.episodes.EpisodeSimplified;
import spotify.models.paging.Paging;
import spotify.models.shows.ShowFull;

public interface ShowApi {
    ShowFull getShow(String showId, String market);
    Paging<EpisodeSimplified> getShowEpisodes(String showId, int limit, int offset, String market);
}
