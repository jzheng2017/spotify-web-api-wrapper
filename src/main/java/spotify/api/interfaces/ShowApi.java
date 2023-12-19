package spotify.api.interfaces;

import spotify.models.episodes.EpisodeSimplified;
import spotify.models.paging.Paging;
import spotify.models.shows.ShowFull;
import spotify.models.shows.ShowSimplifiedCollection;

import java.util.List;
import java.util.Map;

public interface ShowApi {
    ShowFull getShow(String showId, Map<String, String> options);

    Paging<EpisodeSimplified> getShowEpisodes(String showId, Map<String, String> options);

    ShowSimplifiedCollection getShows(List<String> listOfShowIds, Map<String, String> options);
}
