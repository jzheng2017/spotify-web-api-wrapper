package spotify.retrofit.services;

import retrofit2.Call;
import retrofit2.http.*;
import spotify.models.albums.AlbumFull;
import spotify.models.albums.AlbumFullCollection;
import spotify.models.paging.Paging;
import spotify.models.tracks.TrackSimplified;

import java.util.Map;



/**
 * Retrofit service interface for Spotify Album API endpoints.
 * Provides methods to retrieve album information and tracks.
 */
public interface AlbumService {


    /**
     * Retrieves detailed information about a specific album.
     *
     * @param accessToken the OAuth access token for authorization
     * @param albumId the Spotify ID of the album
     * @param options optional query parameters such as market
     * @return a Call object containing the full album details
     */
    @GET("albums/{id}")
    Call<AlbumFull> getAlbum(@Header("Authorization") String accessToken, @Path("id") String albumId, @QueryMap Map<String, String> options);


    /**
     * Retrieves detailed information about multiple albums.
     *
     * @param accessToken the OAuth access token for authorization
     * @param albumIds a comma-separated list of Spotify album IDs
     * @param options optional query parameters such as market
     * @return a Call object containing a collection of full album details
     */
    @GET("albums")
    Call<AlbumFullCollection> getAlbums(@Header("Authorization") String accessToken, @Query("ids") String albumIds, @QueryMap Map<String, String> options);


    /**
     * Retrieves the tracks of a specific album.
     *
     * @param accessToken the OAuth access token for authorization
     * @param albumId the Spotify ID of the album
     * @param options optional query parameters such as market, limit, and offset
     * @return a Call object containing a paged list of simplified track objects
     */
    @GET("albums/{id}/tracks")
    Call<Paging<TrackSimplified>> getAlbumTracks(@Header("Authorization") String accessToken,
                                                 @Path("id") String albumId,
                                                 @QueryMap Map<String, String> options);
}




