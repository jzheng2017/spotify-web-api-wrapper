package spotify.api.interfaces;

import spotify.models.shows.ShowFull;

public interface ShowApi {
    ShowFull getShow(String showId, String market);
}
