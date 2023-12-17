package spotify.api.interfaces;

import spotify.models.episodes.EpisodeSimplified;
import spotify.models.paging.Paging;
import spotify.models.shows.ShowFull;
import spotify.models.shows.ShowSimplified;
import spotify.models.shows.ShowSimplifiedCollection;

import java.util.List;
import java.util.Map;

public interface ShowApi {
    ShowFull getShow(String showId, Map<String, String> options);

    Paging<EpisodeSimplified> getShowEpisodes(String showId, Map<String, String> options);

    /**
     * This method is deprecated because it returns an unnecessary wrapper object. Use {@link #getShowsUnwrapped} instead;
     */
    @Deprecated(since = "1.5.8")
    ShowSimplifiedCollection getShows(List<String> listOfShowIds, Map<String, String> options);

    List<ShowSimplified> getShowsUnwrapped(List<String> listOfShowIds, Map<String, String> options);
}
