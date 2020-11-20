package spotify.retrofit.services;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;
import spotify.models.artists.ArtistFull;
import spotify.models.artists.ArtistFullCollection;
import spotify.models.artists.ArtistSimplified;
import spotify.models.paging.Paging;
import spotify.models.tracks.TrackFullCollection;

public interface ArtistService {
    @GET("artists/{id}")
    Call<ArtistFull> getArtist(@Header("Authorization") String accessToken, @Path("id") String artistId);

    @GET("artists/{id}/albums")
    Call<Paging<ArtistSimplified>> getArtistAlbums(@Header("Authorization") String accessToken,
                                                   @Path("id") String artistId,
                                                   @Query("include_groups") String albumTypes,
                                                   @Query("country") String country,
                                                   @Query("limit") int limit,
                                                   @Query("offset") int offset);
    @GET("artists/{id}/top-tracks")
    Call<TrackFullCollection> getArtistTopTracks(@Header("Authorization") String accessToken, @Path("id") String artistId, @Query("country") String country);

    @GET("artists/{id}/related-artists")
    Call<ArtistFullCollection> getRelatedArtists(@Header("Authorization") String accessToken, @Path("id") String artistId);

    @GET("artists")
    Call<ArtistFullCollection> getArtists(@Header("Authorization") String accessToken, @Query("ids") String artistIds);

}
