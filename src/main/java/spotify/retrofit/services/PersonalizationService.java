package spotify.retrofit.services;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.QueryMap;
import spotify.models.artists.ArtistFull;
import spotify.models.paging.Paging;
import spotify.models.tracks.TrackFull;

import java.util.Map;

/**
 * Retrofit service interface for Spotify Personalization API endpoints.
 * Provides methods to retrieve the current user's top artists and tracks
 * based on their listening history.
 */
public interface PersonalizationService {

    /**
     * Retrieves the current user's top artists based on listening history.
     *
     * @param accessToken the OAuth access token for authorization
     * @param options optional query parameters such as time_range, limit, and offset
     * @return a Call object containing a paged list of the user's top artists
     */
    @GET("me/top/artists")
    Call<Paging<ArtistFull>> getTopArtists(@Header("Authorization") String accessToken, @QueryMap Map<String, String> options);

    /**
     * Retrieves the current user's top tracks based on listening history.
     *
     * @param accessToken the OAuth access token for authorization
     * @param options optional query parameters such as time_range, limit, and offset
     * @return a Call object containing a paged list of the user's top tracks
     */
    @GET("me/top/tracks")
    Call<Paging<TrackFull>> getTopTracks(@Header("Authorization") String accessToken, @QueryMap Map<String, String> options);
}