package spotify.api.interfaces;

import spotify.models.artists.ArtistFull;
import spotify.models.paging.Paging;
import spotify.models.tracks.TrackFull;

import java.util.Map;

public interface PersonalizationApi {
    Paging<ArtistFull> getTopArtists(Map<String, String> options);

    Paging<TrackFull> getTopTracks(Map<String, String> options);
}
