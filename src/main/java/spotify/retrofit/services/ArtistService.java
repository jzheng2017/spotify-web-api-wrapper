package spotify.retrofit.services;

import retrofit2.Call;
import retrofit2.http.*;
import spotify.models.albums.AlbumSimplified;
import spotify.models.artists.ArtistFull;
import spotify.models.artists.ArtistFullCollection;
import spotify.models.paging.Paging;
import spotify.models.tracks.TrackFullCollection;

import java.util.Map;


/**
 * Retrofit service interface for Spotify Artist API endpoints.
 * Provides methods to retrieve artist information, albums, top tracks, and related artists.
 */
public interface ArtistService {



    /**
     * Retrieves detailed information about a specific artist.
     *
     * @param accessToken the OAuth access token for authorization
     * @param artistId the Spotify ID of the artist
     * @return a Call object containing the full artist details
     */
    @GET("artists/{id}")
    Call<ArtistFull> getArtist(@Header("Authorization") String accessToken, @Path("id") String artistId);


    /**
     * Retrieves the albums of a specific artist.
     *
     * @param accessToken the OAuth access token for authorization
     * @param artistId the Spotify ID of the artist
     * @param options optional query parameters such as include_groups, market, limit, and offset
     * @return a Call object containing a paged list of simplified album objects
     */
    @GET("artists/{id}/albums")
    Call<Paging<AlbumSimplified>> getArtistAlbums(@Header("Authorization") String accessToken,
                                                  @Path("id") String artistId,
                                                  @QueryMap Map<String, String> options);


    /**
     * Retrieves the top tracks of a specific artist.
     *
     * @param accessToken the OAuth access token for authorization
     * @param artistId the Spotify ID of the artist
     * @param options query parameters including the required market parameter
     * @return a Call object containing a collection of the artist's top tracks
     */
    @GET("artists/{id}/top-tracks")
    Call<TrackFullCollection> getArtistTopTracks(@Header("Authorization") String accessToken, @Path("id") String artistId, @QueryMap Map<String, String> options);


    /**
     * Retrieves artists related to a specific artist.
     *
     * @param accessToken the OAuth access token for authorization
     * @param artistId the Spotify ID of the artist
     * @return a Call object containing a collection of related artists
     */
    @GET("artists/{id}/related-artists")
    Call<ArtistFullCollection> getRelatedArtists(@Header("Authorization") String accessToken, @Path("id") String artistId);



    /**
     * Retrieves detailed information about multiple artists.
     *
     * @param accessToken the OAuth access token for authorization
     * @param artistIds a comma-separated list of Spotify artist IDs
     * @return a Call object containing a collection of full artist details
     */
    @GET("artists")
    Call<ArtistFullCollection> getArtists(@Header("Authorization") String accessToken, @Query("ids") String artistIds);

}