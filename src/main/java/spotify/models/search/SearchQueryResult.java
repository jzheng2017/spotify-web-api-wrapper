package spotify.models.search;

import spotify.models.albums.AlbumSimplified;
import spotify.models.artists.ArtistFull;
import spotify.models.episodes.EpisodeSimplified;
import spotify.models.paging.Paging;
import spotify.models.playlists.PlaylistSimplified;
import spotify.models.shows.ShowSimplified;
import spotify.models.tracks.TrackFull;

public class SearchQueryResult {
    private Paging<ArtistFull> artists;
    private Paging<AlbumSimplified> albums;
    private Paging<TrackFull> tracks;
    private Paging<ShowSimplified> shows;
    private Paging<EpisodeSimplified> episodes;
    private Paging<PlaylistSimplified> playlists;

    public Paging<ArtistFull> getArtists() {
        return artists;
    }

    public void setArtists(Paging<ArtistFull> artists) {
        this.artists = artists;
    }

    public Paging<AlbumSimplified> getAlbums() {
        return albums;
    }

    public void setAlbums(Paging<AlbumSimplified> albums) {
        this.albums = albums;
    }

    public Paging<TrackFull> getTracks() {
        return tracks;
    }

    public void setTracks(Paging<TrackFull> tracks) {
        this.tracks = tracks;
    }

    public Paging<ShowSimplified> getShows() {
        return shows;
    }

    public void setShows(Paging<ShowSimplified> shows) {
        this.shows = shows;
    }

    public Paging<EpisodeSimplified> getEpisodes() {
        return episodes;
    }

    public void setEpisodes(Paging<EpisodeSimplified> episodes) {
        this.episodes = episodes;
    }

    public Paging<PlaylistSimplified> getPlaylists() {
        return playlists;
    }

    public void setPlaylists(Paging<PlaylistSimplified> playlists) {
        this.playlists = playlists;
    }
}
